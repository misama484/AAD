package t4.aev;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.codec.binary.Base64;

public class Controlador {

	private Modelo modelo;
	private Vista vista;
	private Anyadir anyadir;
	private Editar editar;
	private Buscar buscarPop;
	private PanelLogin panelLogin;

	
	private ActionListener ALConectar, ALValidaUsuario, ALCerrarLogin, ALDesconectar, ALMostrarDatos, ALAnyadirLibro,
			ALGuardarLibro, ALCerrar, ALEditar, ALCerrarEditar, ALBorrarCampos, ALGuardarCambios, ALCargarLibro,
			ALBuscarLibro, ALBuscar, ALBorrarBusqueda, ALEliminar, ALBuscarCriterio, ALCerrarBusqueda, ALBorrarLibro,
			ALSelccionarImagen, AlMostrarDetalle, ALCerrarDetalle;

	// CONSTRUCTOR
	public Controlador(Modelo modelo, Vista vista, Anyadir anyadir, Editar editar, Buscar buscar,
			PanelLogin panelLogin) {
		this.modelo = modelo;
		this.vista = vista;
		this.anyadir = anyadir;
		this.editar = editar;
		this.buscarPop = buscar;
		this.panelLogin = panelLogin;
		control();
	}

	private void control() {

		// conecta bd y muestra lista de bd's
		ALConectar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelLogin.setVisible(true);
				vista.getTextAreaPrincipal().setText("");

				ALValidaUsuario = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String usuario = panelLogin.getTextUser().getText();
						char pass[] = panelLogin.getPasswordField().getPassword();
						String password = new String(pass);
						if (conectaMongo(password, password)) {
							panelLogin.setVisible(false);
							vista.getTextAreaTablas().setText(modelo.ElementosBD());
							vista.getLblEstado()
									.setText("LOGUEADO COMO: " + modelo.getUsuario() + " EN IP: " + modelo.getIp());
						}
					}

				};
				panelLogin.getBtnAceptar().addActionListener(ALValidaUsuario);

				ALCerrarLogin = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						panelLogin.setVisible(false);

					}

				};
				panelLogin.getBtnCancelar().addActionListener(ALCerrarLogin);

			}

		};
		vista.getBtnConectar().addActionListener(ALConectar);

		ALDesconectar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String mensajeDesconexion = desconectaMongo();
				JOptionPane.showMessageDialog(new JFrame(), mensajeDesconexion, "INFO",
						JOptionPane.INFORMATION_MESSAGE);

			}

		};
		vista.getBtnDesconectar().addActionListener(ALDesconectar);

		ALMostrarDatos = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vista.getTextAreaPrincipal().setText("");
				ArrayList<Libro> biblioteca = MostrarBD();
				for (Libro libro : biblioteca) {
					vista.getTextAreaPrincipal().setText(vista.getTextAreaPrincipal().getText() + "\n" + "ID: " + libro.getId() + " Titulo: " + libro.getTitulo());
				}

			}

		};
		vista.getBtnMostrarBD().addActionListener(ALMostrarDatos);

		ALAnyadirLibro = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (modelo.getUserLogged()) {
					Anyadir anyadir = new Anyadir();
					anyadir.setVisible(true);

					// funcion del boton guardar libro, que lo subira a la coleccion
					// recoge datos de textFields y llama a agregaLibro()
					ALGuardarLibro = new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							anyadir.getEditorPane().setContentType("text");
							String titulo = anyadir.getTextFieldTitulo().getText();
							String autor = anyadir.getTextFieldAutor().getText();
							String anyoNacimiento = anyadir.getTextFieldAnyoNac().getText();
							String anyoPublicacion = anyadir.getTextFieldAnyoPub().getText();
							String editorial = anyadir.getTextFieldEditorial().getText();
							String numPaginas = anyadir.getTextFieldNumPaginas().getText();
							String imagen = anyadir.getTextFieldImagen().getText();

							anyadir.getEditorPane().setText(titulo + "\n" + autor + "\n" + anyoNacimiento + "\n"
									+ anyoPublicacion + "\n" + editorial + "\n" + numPaginas + " paginas\n" + imagen);

							anyadir.getEditorPane().setText(agregarLibro(titulo, autor, anyoNacimiento, anyoPublicacion,
									editorial, numPaginas, imagen));
						}

					};
					anyadir.getBtnGuardar().addActionListener(ALGuardarLibro);

					// Funcion del boton cerrar - cierra ventana
					ALCerrar = new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							anyadir.setVisible(false);

						}

					};
					anyadir.getBtnCerrar().addActionListener(ALCerrar);

					ALSelccionarImagen = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser fc = new JFileChooser("D:\\DAMFlorida\\AAD\\AEVT4\\images");
							fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
							FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("JPG & PNG Images", "jpg",
									"png");
							fc.setFileFilter(imgFilter);
							fc.showOpenDialog(fc);
							String image = fc.getSelectedFile().getAbsolutePath();
							File imagen = new File(image);
							try {
								String imagenCodificada = codificaImagen(imagen);
								anyadir.getTextFieldImagen().setText(imagenCodificada);
								anyadir.getLblImagen().setIcon(decodificaImagen(imagenCodificada));

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}

					};
					anyadir.getBtnImagen().addActionListener(ALSelccionarImagen);

				} else {
					JOptionPane.showMessageDialog(new JFrame(), "USUARIO NO LOGUEADO", "ERROR",
							JOptionPane.WARNING_MESSAGE);
				}
			}

		};
		vista.getBtnAnyadirLibro().addActionListener(ALAnyadirLibro);

		// funcion del boton EditarLibro
		ALEditar = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (modelo.getUserLogged()) {
					Editar editar = new Editar();
					editar.setVisible(true);

					// FUNCION CARGAR LIBRO
					ALCargarLibro = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String id = editar.getTextFieldId().getText();

							if (editar.getTextFieldId().getText() == null || editar.getTextFieldId().getText() == "") {
								JOptionPane.showMessageDialog(new JFrame(), "EL ID NO EXISTE", "ERROR",
										JOptionPane.WARNING_MESSAGE);
							}
							cargarLibro(id);
							Libro libro = modelo.CargarLibro(Integer.parseInt(id));
							editar.getTextFieldTitulo().setText(libro.getTitulo());
							editar.getTextFieldAutor().setText(libro.getAutor());
							Integer anyoNacimiento = libro.getAnyNacimiento();
							editar.getTextFieldAnyoNac().setText(anyoNacimiento.toString());
							Integer anyoPublicacion = libro.getAnyoPublicacion();
							editar.getTextFieldAnyoPub().setText(anyoPublicacion.toString());
							editar.getTextFieldEditorial().setText(libro.getEditorial());
							Integer numPaginas = libro.getNumPaginas();
							editar.getTextFieldNumPaginas().setText(numPaginas.toString());
							editar.getTextFieldImagen().setText(libro.getImagen());
							editar.getLblImagen().setText("");
							try {
								editar.getLblImagen().setIcon(decodificaImagen(libro.getImagen().toString()));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							editar.getEditorPane()
									.setText("EDITE LOS CAMPOS NECESARIOS Y PULSE GUARADAR PARA ACTUALIZAR EL LIBRO");
						}

					};
					editar.getBtnCargarLibro().addActionListener(ALCargarLibro);

					// FUNCION BOTON EDITAR IMAGEN
					ALSelccionarImagen = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							JFileChooser fc = new JFileChooser("D:\\DAMFlorida\\AAD\\AEVT4\\images");
							fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
							FileNameExtensionFilter imgFilter = new FileNameExtensionFilter("JPG & PNG Images", "jpg",
									"png");
							fc.setFileFilter(imgFilter);
							fc.showOpenDialog(fc);
							String image = fc.getSelectedFile().getAbsolutePath();
							File imagen = new File(image);
							try {
								String imagenCodificada = codificaImagen(imagen);
								editar.getTextFieldImagen().setText(imagenCodificada);
								editar.getLblImagen().setIcon(decodificaImagen(imagenCodificada));

							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}

					};
					editar.getBtnImagen().addActionListener(ALSelccionarImagen);

					// FUNCION BOTON GUARDARCAMBIOS
					ALGuardarCambios = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							int id = Integer.parseInt(editar.getTextFieldId().getText());
							String titulo = editar.getTextFieldTitulo().getText();
							String autor = editar.getTextFieldAutor().getText();
							int anyoNacimiento = Integer.parseInt(editar.getTextFieldAnyoNac().getText());
							int anyoPublicacion = Integer.parseInt(editar.getTextFieldAnyoPub().getText());
							String editorial = editar.getTextFieldEditorial().getText();
							int numPaginas = Integer.parseInt(editar.getTextFieldNumPaginas().getText());
							String imagen = editar.getTextFieldImagen().getText();

							Libro libroEditado = new Libro(id, titulo, autor, anyoNacimiento, anyoPublicacion,
									editorial, numPaginas, imagen);
							editar.getEditorPane().setText("Editando libro: " + libroEditado.toString());
							String response = modelo.EditarLibro(libroEditado);
							editar.getEditorPane().setText(response);

						}

					};
					editar.getBtnGuardar().addActionListener(ALGuardarCambios);
					// Funcion de BORRAR CAMPOS
					ALBorrarCampos = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							editar.getTextFieldId().setText("");
							editar.getTextFieldTitulo().setText("");
							editar.getTextFieldAutor().setText("");
							editar.getTextFieldAnyoNac().setText("");
							editar.getTextFieldAnyoPub().setText("");
							editar.getTextFieldEditorial().setText("");
							editar.getTextFieldNumPaginas().setText("");
							editar.getTextFieldImagen().setText("");
							editar.getEditorPane().setText("INTRODUZCA ID DEL LIBRO A EDITAR");
							editar.getLblImagen().setIcon(null);
						}

					};
					editar.getBtnBorrarCampos().addActionListener(ALBorrarCampos);

					ALCerrarEditar = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							editar.setVisible(false);

						}

					};
					editar.getBtnCerrar().addActionListener(ALCerrarEditar);

				} else {
					JOptionPane.showMessageDialog(new JFrame(), "USUARIO NO LOGUEADO", "ERROR",
							JOptionPane.WARNING_MESSAGE);
				}

			}

		};
		vista.getBtnEditarLibro().addActionListener(ALEditar);

		// funcion del boton BUSCARLIBRO
		ALBuscarLibro = new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				// comprobamos usuario logueado
				if (modelo.getUserLogged()) {
					String campo = "";
					String valor = "";

					Buscar buscar = new Buscar();
					buscar.getBtnBorrarLibro().setVisible(false);
					buscar.getComboBox().addItem("Id");
					buscar.getComboBox().addItem("Titulo");
					buscar.getComboBox().addItem("Autor");
					buscar.getComboBox().addItem("Anyo_nacimiento");
					buscar.getComboBox().addItem("Anyo_publicacion");
					buscar.getComboBox().addItem("Editorial");
					buscar.getComboBox().addItem("Numero_paginas");
					buscar.getComboBoxCriterio().addItem("Mayor_que");
					buscar.getComboBoxCriterio().addItem("Menor_que");
					buscar.setVisible(true);

					ALBuscar = new ActionListener() {// --------------------------------------------------------------------
						@Override
						public void actionPerformed(ActionEvent e) {
							// preparamos datos a enviar
							String valor = buscar.getTextFieldCampoBusqueda().getText();
							String seleccion = (String) buscar.getComboBox().getSelectedItem();
							String parametroBusqueda = "eq";
							Libro libro = modelo.BuscarLibro(seleccion, valor);
							buscar.getEditorPane().setText(libro.toTextArea());

						}

					};
					buscar.getBtnBuscar().addActionListener(ALBuscar);

					ALBorrarBusqueda = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							buscar.getEditorPane().setText("");
							buscar.getTextFieldCampoBusqueda().setText("");
						}

					};
					buscar.getBtnBorrarCampos().addActionListener(ALBorrarBusqueda);

					ALCerrarBusqueda = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							buscar.setVisible(false);

						}

					};
					buscar.getBtnCerrar().addActionListener(ALCerrarBusqueda);

					ALBuscarCriterio = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							buscar.getEditorPane().setText("");
							String parametroBusqueda = null;
							String opcion = (String) buscar.getComboBoxCriterio().getSelectedItem();
							if (opcion.equals("Mayor_que")) {
								parametroBusqueda = "gte";
							} else {
								parametroBusqueda = "lte";
							}
							String valor = buscar.getTextFieldCampoBusqueda().getText();
							String seleccion = (String) buscar.getComboBox().getSelectedItem();
							ArrayList<Libro> response = BuscarLibroCriterio(seleccion, valor, parametroBusqueda);

							for (Libro libro : response) {
								String book = libro.toString();
								buscar.getEditorPane().setText(buscar.getEditorPane().getText() + "\n" + book);
							}

						}

					};
					buscar.getBtnBuscarCriterio().addActionListener(ALBuscarCriterio);

					AlMostrarDetalle = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {

							Editar detalle = new Editar();
							detalle.setVisible(true);
							detalle.getFrmEditarLibro().setTitle("Detalles del Libro");
							detalle.getEditorPane().setText("SE MUESTRAN DETALLES DEL LIBRO");
							detalle.getBtnImagen().setVisible(false);
							detalle.getBtnBorrarCampos().setVisible(false);
							detalle.getBtnCargarLibro().setVisible(false);
							detalle.getBtnGuardar().setVisible(false);
							detalle.getBtnImagen().setVisible(false);

							String valor = buscar.getTextFieldCampoBusqueda().getText();
							String seleccion = (String) buscar.getComboBox().getSelectedItem();
							Libro libro = modelo.BuscarLibro(seleccion, valor);
							Integer id = libro.getId();
							detalle.getTextFieldId().setText(id.toString());
							detalle.getTextFieldTitulo().setText(libro.getTitulo());
							detalle.getTextFieldAutor().setText(libro.getAutor());
							detalle.getTextFieldAnyoNac().setText(libro.getAnyNacimiento().toString());
							detalle.getTextFieldAnyoPub().setText(libro.getAnyoPublicacion().toString());
							detalle.getTextFieldEditorial().setText(libro.getEditorial());
							detalle.getTextFieldNumPaginas().setText(libro.getNumPaginas().toString());
							try {
								detalle.getLblImagen().setIcon(decodificaImagen(libro.getImagen().toString()));
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							ALCerrarDetalle = new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									detalle.setVisible(false);
								}

							};
							detalle.getBtnCerrar().addActionListener(ALCerrarDetalle);

						}

					};
					buscar.getBtnDetalle().addActionListener(AlMostrarDetalle);

				} else {
					JOptionPane.showMessageDialog(new JFrame(), "USUARIO NO LOGUEADO", "ERROR",
							JOptionPane.WARNING_MESSAGE);
				}
			}

		};
		vista.getBtnBuscarLibro().addActionListener(ALBuscarLibro);

		ALBorrarLibro = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (modelo.getUserLogged()) {
					String campo = "";
					String valor = "";

					Buscar borrar = new Buscar();
					borrar.getFrmEditarLibro().setTitle("Borrar Libro");
					borrar.getBtnBuscarCriterio().setVisible(false);
					borrar.getBtnDetalle().setVisible(false);
					borrar.getComboBoxCriterio().setVisible(false);
					borrar.getLblMayorMenor().setVisible(false);

					borrar.getComboBox().addItem("Id");
					borrar.getComboBox().addItem("Titulo");
					borrar.getComboBox().addItem("Autor");
					borrar.getComboBox().addItem("Anyo_nacimiento");
					borrar.getComboBox().addItem("Anyo_publicacion");
					borrar.getComboBox().addItem("Editorial");
					borrar.getComboBox().addItem("Numero_paginas");
					borrar.setVisible(true);
					ALBuscar = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// preparamos datos a enviar
							String valor = borrar.getTextFieldCampoBusqueda().getText();
							String seleccion = (String) borrar.getComboBox().getSelectedItem();
							Libro libro = modelo.BuscarLibro(seleccion, valor);
							borrar.getEditorPane().setText(libro.toTextArea());

						}

					};
					borrar.getBtnBuscar().addActionListener(ALBuscar);

					ALBorrarBusqueda = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							borrar.getEditorPane().setText("");
							borrar.getTextFieldCampoBusqueda().setText("");
						}

					};
					borrar.getBtnBorrarCampos().addActionListener(ALBorrarBusqueda);

					ALCerrarBusqueda = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							borrar.setVisible(false);

						}

					};
					borrar.getBtnCerrar().addActionListener(ALCerrarBusqueda);

					ALEliminar = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							String valor = borrar.getTextFieldCampoBusqueda().getText();
							String seleccion = (String) borrar.getComboBox().getSelectedItem();

							int response = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Alerta!",
									JOptionPane.YES_NO_OPTION);
							if (response == 0) {
								if (BorrarLibro(seleccion, valor)) {
									JOptionPane.showMessageDialog(new JFrame(), "Libro eliminado", "Correcto",
											JOptionPane.INFORMATION_MESSAGE);
								}
							}
						}
					};
					borrar.getBtnBorrarLibro().addActionListener(ALEliminar);

				} else {
					JOptionPane.showMessageDialog(new JFrame(), "USUARIO NO LOGUEADO", "ERROR",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		};
		vista.getBtnBorrarLibro().addActionListener(ALBorrarLibro);

	}

	/**CONECTA MONGODB
	 * Recibe String con usuario y password y llama al metodo de la clase modelo y le pasa los parametros
	 * @param usuario
	 * @param password
	 * @return booleano con la respuesta
	 */
	private boolean conectaMongo(String usuario, String password) {
		return modelo.conectaMongo(usuario, password);
	}

	/**DESCONECTA MONGODB
	 * Llama al metodo de la clase modelo y retorna String con la respuesta.
	 * @return String
	 */
	private String desconectaMongo() {
		String response = modelo.desconectaMongo();
		return response;
	}

	/**MOSTRARDB
	 * Retorna un Arraylist de Libro con los elementos de la coleccion, 
	 * mediante el metodo de la clase modelo.
	 * @return
	 */
	private ArrayList<Libro> MostrarBD() {
		ArrayList<Libro> biblioteca = modelo.mostrarBD();
		return biblioteca;
	}

	// agrega un libro a la coleccion, recibe strings de la ventana y convierte los
	// necesarios a int y llamando al metodo
	// modelo.AnyadeLibro, que lo manda a mongo
	// devuelve un string con el resultado de la operacion.
	/**AGREGAR LIBRO
	 * Recibe los datos del libro a agregar, convierte en el tipo de dato adecuado y crea un objeto Libro
	 * el cual envia al metodo de la clase Modelo para anyadirlo a la base de datos.
	 * Recibe un String con la confirmacion, el cual retorna.
	 * @param titulo
	 * @param autor
	 * @param anyoNacimiento
	 * @param anyoPublicacion
	 * @param editorial
	 * @param numPaginas
	 * @param imagen
	 * @return String con el resultado de la operacion
	 */
	private String agregarLibro(String titulo, String autor, String anyoNacimiento, String anyoPublicacion,
			String editorial, String numPaginas, String imagen) {
		int anyoNacimientoInt = Integer.parseInt(anyoNacimiento);
		int anyoPublicacionInt = Integer.parseInt(anyoPublicacion);
		int numPaginasInt = Integer.parseInt(numPaginas);

		Libro libro = new Libro(titulo, autor, anyoNacimientoInt, anyoPublicacionInt, editorial, numPaginasInt, imagen);

		String response = modelo.AnyadeLibro(libro);
		return response;
	}

	/**CARGAR LIBRO
	 * Recibe un String con el is del libro a mostrar.
	 * Comprueba que el id no esta vacio o el null, convierte a int el id y solicita al metodo de la clase
	 * Modelo, que retornara un objeto Libro con el libro coincidiente con el id.
	 * El metodo no retorna nada, asignara los valores en la pantalla.
	 * El caso de error, muestra un mensaje. 
	 * @param Id
	 */
	private void cargarLibro(String Id) {
		if (Id != "" || Id != null) {
			int id = Integer.parseInt(Id);
			Libro libro = modelo.CargarLibro(id);
			String titulo = libro.getTitulo();
			editar.getTextFieldTitulo().setText(titulo);
			
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "EL ID NO EXISTE", "ERROR", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**BORRAR LIBRO
	 * Recibe como parametros Strings con el campo de busqueda y el valor del campo
	 * Los envia al metodo de la clase Modelo, que borrara el libro de la BD, el cual retorna
	 * un boolean con el resultado
	 * @param campo
	 * @param valor
	 * @return boolean
	 */
	private boolean BorrarLibro(String campo, String valor) {

		return modelo.BorrarLibro(campo, valor);
	}

	/**BUSCAR LIBRO CRITERIO
	 * Recibe Strings conn campo, valor y filtro de busqueda y llama al metodo de la clase modelo,
	 * el cual retorna un ArrayList de Libro con los resultados de la busqueda
	 * @param seleccion
	 * @param valor
	 * @param parametroBusqueda
	 * @return ArrayList de Libro
	 */
	private ArrayList<Libro> BuscarLibroCriterio(String seleccion, String valor, String parametroBusqueda) {

		ArrayList<Libro> response = modelo.BuscarLibroCriterio(seleccion, valor, parametroBusqueda);
		return response;
	}

	/**DECODIFICA IMAGEN
	 * Recibe un String con base64, lo decodifica y convierte en un ImageIcon para poder mostrarlo
	 * en la UI.
	 * @param String base64 con la imagen
	 * @return ImageIcon con la imagen
	 * @throws IOException
	 */
	private ImageIcon decodificaImagen(String imagen) throws IOException {

		byte[] btDataFile = Base64.decodeBase64(imagen);

		BufferedImage image = ImageIO.read(new ByteArrayInputStream(btDataFile));
		ImageIcon imagenIcono = new ImageIcon(image);

		return imagenIcono;
	}

	/**CODIFICA IMAGEN
	 * Recibe un File con la imagen, la convierte a un array de bytes y la 
	 * codifica en String con base 64, el cual retorna
	 * @param File imagen
	 * @return String base64 con la imagen
	 * @throws IOException
	 */
	private String codificaImagen(File imagen) throws IOException {
		String imagenString;

		byte[] fileContent = Files.readAllBytes(imagen.toPath());
		imagenString = Base64.encodeBase64String(fileContent);

		return imagenString;
	}

}
