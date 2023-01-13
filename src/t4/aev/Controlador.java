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

	private ActionListener ALConectar, ALAnyadirLibro, ALGuardarLibro, ALCerrar, ALEditar, ALCerrarEditar,
			ALBorrarCampos, ALGuardarCambios, ALCargarLibro, ALBuscarLibro, ALBuscar;

	// CONSTRUCTOR
	public Controlador(Modelo modelo, Vista vista, Anyadir anyadir, Editar editar) {
		this.modelo = modelo;
		this.vista = vista;
		this.anyadir = anyadir;
		this.editar = editar;
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
			@Override
			public void actionPerformed(ActionEvent e) {
				// comprobamos usuario logueado
				if (modelo.getUserLogged()) {
					// creamos una ventana editar y la modificamos adecuadamente
					Editar buscar = new Editar();
					buscar.setVisible(true);
					buscar.getFrmEditarLibro().setTitle("Buscar Libro");
					buscar.getEditorPane().setText("INTRODUZCA ALGUN CAMPO A BUSCAR");
					buscar.getBtnGuardar().setText("BUSCAR");
					buscar.getBtnCargarLibro().setVisible(false);
					buscar.getLblImagen().setVisible(false);
					buscar.getTextFieldImagen().setVisible(false);
					buscar.getLblImagenTitulo().setVisible(false);
					buscar.getBtnImagen().setVisible(false);
					// buscar.getTextFieldAnyoNac().setEditable(false);
					// buscar.getTextFieldAnyoPub().setEditable(false);
					// buscar.getTextFieldNumPaginas().setEditable(false);

					// FUNCION DEL BOTON BUSCAR -- NO ENTRA EN LOS IF!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					ALBuscar = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							// preparamos datos a enviar
							String campo = null, valor = null;
							System.out.println("-- " + buscar.getTextFieldAutor().getText() + "AUTOR");
							String titulo = buscar.getTextFieldTitulo().getText();
							String autor = buscar.getTextFieldAutor().getText();

							if (titulo != null) {
								campo = "Titulo";
								valor = buscar.getTextFieldTitulo().getText();
							}

							if (autor != null) {
								campo = "Autor";
								valor = buscar.getTextFieldAutor().getText();
							}

							// llamar a metodo buscar de modelo
							System.out.println(campo + "--" + valor);
							buscar.getEditorPane().setText(modelo.BuscarLibro(campo, valor));

						}

					};
					buscar.getBtnGuardar().addActionListener(ALBuscar);

				} else {
					JOptionPane.showMessageDialog(new JFrame(), "USUARIO NO LOGUEADO", "ERROR",
							JOptionPane.WARNING_MESSAGE);
				}
			}

		};
		vista.getBtnBuscarLibro().addActionListener(ALBuscarLibro);

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

	private void BuscarLibro(String campo, String valor) {
		// Libro libro = modelo.BuscarLibro(campo, valor);

	}

}
