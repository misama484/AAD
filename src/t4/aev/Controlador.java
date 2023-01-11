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

	private ActionListener ALConectar, ALAnyadirLibro, ALGuardarLibro;

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
					ALGuardarLibro = new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							anyadir.getEditorPane().setText(agregarLibro());
						}

					};
					anyadir.getBtnGuardar().addActionListener(ALGuardarLibro);

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

	// agrega un libro a la coleccion, recabando los datos de los textField de la
	// ventana anyadir y llamando al metodo
	// modelo.AnyadeLibro, que lo manda a mongo
	private String agregarLibro() {
		String titulo = anyadir.getTextFieldTitulo().getText();
		System.out.println(titulo.toString());
		String autor = anyadir.getTextFieldAutor().getText();
		System.out.println(autor);
		String anyoNacimientoString = anyadir.getTextFieldAnyoNac().getText();
		System.out.println(anyoNacimientoString);
		
		int anyoNacimiento = Integer.valueOf(anyoNacimientoString);
		Integer anyoPublicacion = Integer.parseInt(anyadir.getTextFieldAnyoPub().getText());
		String editorial = anyadir.getTextFieldEditorial().getText();
		Integer numPaginas = Integer.parseInt(anyadir.getTextFieldNumPaginas().getText());
		String imagen = anyadir.getTextFieldImagen().getText();

		Libro libro = new Libro(titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas, imagen);

		//modelo.AnyadeLibro(libro);
		return "Agregado libro " + titulo + " a la coleccion.";
	}

}
