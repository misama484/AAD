package t4.aev;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Controlador {

	private Modelo modelo;
	private Vista vista;
	private Anyadir anyadir;
	private Editar editar;
	private Buscar buscarPop;

	private ActionListener ALConectar, ALAnyadirLibro, ALGuardarLibro, ALCerrar, ALEditar, ALCerrarEditar,
			ALBorrarCampos, ALGuardarCambios, ALCargarLibro, ALBuscarLibro, ALBuscar, ALBorrarBusqueda, ALEliminar, ALBuscarCriterio, 
			ALCerrarBusqueda, ALBorrarLibro;

	// CONSTRUCTOR
	public Controlador(Modelo modelo, Vista vista, Anyadir anyadir, Editar editar, Buscar buscar) {
		this.modelo = modelo;
		this.vista = vista;
		this.anyadir = anyadir;
		this.editar = editar;
		this.buscarPop = buscar;
		control();
	}

	private void control() {

		// conecta bd y muestra lista de bd's
		ALConectar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vista.getTextAreaPrincipal().setText("");
				ArrayList<String> listaBd = conectaMongo();
				for (String bdName : listaBd) {
					vista.getTextAreaPrincipal().append(bdName + "\n");
				}
				vista.getTextAreaTablas().setText(modelo.ElementosBD());
				vista.getLblEstado().setText("LOGUEADO COMO: " + modelo.getUsuario() + " EN IP: " + modelo.getIp());

			}

		};
		vista.getBtnConectar().addActionListener(ALConectar);

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

				} else {
					JOptionPane.showMessageDialog(new JFrame(), "USUARIO NO LOGUEADO", "ERROR",
							JOptionPane.WARNING_MESSAGE);
				}

				ALCerrar = new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						anyadir.setVisible(false);

					}

				};
				anyadir.getBtnCerrar().addActionListener(ALCerrar);

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
							editar.getEditorPane()
									.setText("EDITE LOS CAMPOS NECESARIOS Y PULSE GUARADAR PARA ACTUALIZAR EL LIBRO");
						}

					};
					editar.getBtnCargarLibro().addActionListener(ALCargarLibro);

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

					ALBuscar = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// preparamos datos a enviar
							String valor = buscar.getTextFieldCampoBusqueda().getText();
							String seleccion = (String) buscar.getComboBox().getSelectedItem();
							String parametroBusqueda = "eq";

							buscar.getEditorPane().setText(modelo.BuscarLibro(seleccion, valor));

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
							String parametroBusqueda = null;
							String opcion = (String) buscar.getComboBoxCriterio().getSelectedItem();
							if(opcion.equals("Mayor_que")) {
								parametroBusqueda = "gte";
							}
							else {
								parametroBusqueda = "lte";
							}
							String valor = buscar.getTextFieldCampoBusqueda().getText();
							String seleccion = (String) buscar.getComboBox().getSelectedItem();
							ArrayList<Libro> response = BuscarLibroCriterio(seleccion, valor, parametroBusqueda);
			
							
							

							//buscar.getEditorPane().setText(modelo.BuscarLibroCriterio(seleccion, valor, parametroBusqueda));
							
							
						}
						
					};
					buscar.getBtnBuscarCriterio().addActionListener(ALBuscarCriterio);

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

							borrar.getEditorPane().setText(modelo.BuscarLibro(seleccion, valor));

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
							int response = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Alerta!", JOptionPane.YES_NO_OPTION);
							if (response == 0) {
								if (BorrarLibro(seleccion, valor)) {
									JOptionPane.showMessageDialog(new JFrame(), "Libro eliminado", "Correcto", JOptionPane.INFORMATION_MESSAGE);
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

	private ArrayList<String> conectaMongo() {
		// String usuario = panelLogin.getTextUser().getText();
		// String password = panelLogin.getTextPassword().getText();
		return modelo.conectaMongo();
	}

	// agrega un libro a la coleccion, recibe strings de la ventana y convierte los
	// necesarios a int y llamando al metodo
	// modelo.AnyadeLibro, que lo manda a mongo
	// devuelve un string con el resultado de la operacion.
	private String agregarLibro(String titulo, String autor, String anyoNacimiento, String anyoPublicacion,
			String editorial, String numPaginas, String imagen) {
		int anyoNacimientoInt = Integer.parseInt(anyoNacimiento);
		int anyoPublicacionInt = Integer.parseInt(anyoPublicacion);
		int numPaginasInt = Integer.parseInt(numPaginas);

		Libro libro = new Libro(titulo, autor, anyoNacimientoInt, anyoPublicacionInt, editorial, numPaginasInt, imagen);

		String response = modelo.AnyadeLibro(libro);
		return response;
	}

	private void cargarLibro(String Id) {
		if (Id != "" || Id != null) {
			int id = Integer.parseInt(Id);
			Libro libro = modelo.CargarLibro(id);
			String titulo = libro.getTitulo();
			editar.getTextFieldTitulo().setText(titulo);
			System.out.println(titulo);
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "EL ID NO EXISTE", "ERROR", JOptionPane.WARNING_MESSAGE);
		}
	}

	private boolean BorrarLibro(String campo, String valor) {
		return modelo.BorrarLibro(campo, valor);
	}
	 
	private ArrayList<Libro> BuscarLibroCriterio(String seleccion, String valor, String parametroBusqueda){
		
		ArrayList<Libro> response = modelo.BuscarLibroCriterio(seleccion, valor, parametroBusqueda);;
		return response;
	}

}
