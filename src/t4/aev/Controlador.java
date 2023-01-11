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

	private ActionListener ALConectar, ALAnyadirLibro, ALGuardarLibro, ALCerrar;

	// CONSTRUCTOR
	public Controlador(Modelo modelo, Vista vista, Anyadir anyadir) {
		this.modelo = modelo;
		this.vista = vista;
		this.anyadir = anyadir;
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
					
					//Funcion del boton cerrar - cierra ventana
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

			}

		};
		vista.getBtnAnyadirLibro().addActionListener(ALAnyadirLibro);
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

}
