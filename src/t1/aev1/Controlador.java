package t1.aev1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.Highlighter;

public class Controlador {

	private Modelo modelo;
	private Vista vista;
	private Editar editar;
	private ActionListener ALAbrir, ALGuardar, ALEditar, ALSuprimir, ALCopiar, ALRenombrar, ALBuscar, ALReemplazar;
	private ActionListener ALEBuscar;
	private Highlighter.HighlightPainter YelowPainter;

	public Controlador(Modelo modelo, Vista vista, Editar editar) {
		this.modelo = modelo;
		this.vista = vista;
		this.editar = editar;
		control();
	}

	private void control() {

		ALAbrir = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser("D:\\DAMFlorida\\AAD\\");
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				fc.showOpenDialog(fc);
				vista.getTextAreaModificado().setText("");
				vista.getTextAreaOriginal().setText("");
				vista.getTextFieldRuta().setText("");
				modelo.setFicheroLectura(fc.getSelectedFile().getAbsolutePath());
				vista.getTextFieldRuta().setText(fc.getSelectedFile().getAbsolutePath());

				try {
					MostrarTexto(modelo.getFicheroLectura());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		};
		vista.getBtnAbrir().addActionListener(ALAbrir);


		ALEditar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Editar editar = new Editar();
				editar.setVisible(true);
				editar.getEditorPane().setText(vista.getTextAreaOriginal().getText());
				
				ALGuardar = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						JFileChooser fc = new JFileChooser("D:\\DAMFlorida\\AAD\\Aev1T1");
						fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
						fc.showSaveDialog(fc);
						String ruta = fc.getSelectedFile().getAbsolutePath();
						String nombre = fc.getSelectedFile().getName() + ".txt";						
						String texto = editar.getEditorPane().getText();
						GuardarFichero(ruta, nombre, texto);
						editar.getEditorPane().setText("");
						editar.setVisible(false);
					}

				};
				editar.getBtnEditGuardar().addActionListener(ALGuardar);

				ALEBuscar = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {

						String palabraBuscar = editar.getTextFieldBuscar().getText();
						String textoOriginal = editar.getEditorPane().getText();
						String palabrasEncontradas = BuscarPalabra(palabraBuscar, textoOriginal);
						JOptionPane.showMessageDialog(null, "Se han encontrado " + palabrasEncontradas
								+ " coincidencias con la palabra " + palabraBuscar + ".");
						editar.getEditorPane().setContentType("text/html");
						editar.getEditorPane().setText(ReemplazarPalabra(palabraBuscar, textoOriginal, "<strong>" + palabraBuscar.toUpperCase() + "</strong>"));
						
						
					}

				};
				editar.getBtnBuscar().addActionListener(ALEBuscar);
				//vista.getBtnBuscar().addActionListener(ALEBuscar);

				ALReemplazar = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String palabraBuscar = editar.getTextFieldBuscar().getText();
						String palabraReemplazar = editar.getTextFieldReemplazar().getText();
						String textoOriginal = vista.getTextAreaOriginal().getText();
						editar.getEditorPane().setContentType("text");
						editar.getEditorPane().setText(ReemplazarPalabra(palabraBuscar, textoOriginal, palabraReemplazar));

					}

				};
				editar.getBtnReemplazar().addActionListener(ALReemplazar);
			}
		};
		vista.getBtnEditar().addActionListener(ALEditar);

		// BORRAR FICHEROS
		ALSuprimir = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ruta = vista.getTextFieldRuta().getText();
				int resp = JOptionPane.showConfirmDialog(null, "Esta seguro?", "Alerta!", JOptionPane.YES_NO_OPTION);
				if (resp == 0) {
					if (EliminarFichero(ruta)) {
						JOptionPane.showMessageDialog(new JFrame(), "Fichero " + ruta + " eliminado", "Correcto",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(new JFrame(), "Seleccione un fichero", "ERROR",
								JOptionPane.WARNING_MESSAGE);
					}
					vista.getTextAreaModificado().setText("");
					vista.getTextAreaOriginal().setText("");
					vista.getTextFieldRuta().setText("");
				}
			}

		};
		vista.getBtnSuprimir().addActionListener(ALSuprimir);

		// COPIAR FICHEROS
		ALCopiar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String ficheroOrigen = vista.getTextFieldRuta().getText();
				System.out.println(ficheroOrigen);
				int result = JOptionPane.showConfirmDialog(null, "Desea copiar el fichero?", "Alerta!",
						JOptionPane.YES_NO_OPTION);
				if (result == 0) {
					CopiarFichero(ficheroOrigen);
				}
			}

		};
		vista.getBtnCopiar().addActionListener(ALCopiar);

		// RENOMBRAR FICHEROS
		ALRenombrar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String rutaOrigen = vista.getTextFieldRuta().getText();
				String nombreMod = vista.getTextFieldReemplazar().getText();
				RenombrarFichero(rutaOrigen, nombreMod);
			}

		};
		vista.getBtnRenombrar().addActionListener(ALRenombrar);

		ALBuscar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String palabraBuscar = vista.getTextFieldBuscar().getText();
				String textoOriginal = vista.getTextAreaOriginal().getText();
				String palabrasEncontradas = BuscarPalabra(palabraBuscar, textoOriginal);
				vista.getTextAreaModificado().setText("Se han encontrado " + palabrasEncontradas
						+ " coincidencias con la palabra " + palabraBuscar + ".");
			}

		};
		vista.getBtnBuscar().addActionListener(ALBuscar);
	}

	private void MostrarTexto(String ficheroLectura) throws IOException {
		ArrayList<String> contenido = modelo.MostrarTexto(ficheroLectura);
		ArrayList<String> datos = modelo.getDatosFichero(ficheroLectura);
		for (String linea : contenido) {
			vista.getTextAreaOriginal().append(linea + "\n");
		}
		for (String linea : datos) {
			vista.getTextAreaModificado().append(linea + "\n");
		}

	}

	private void GuardarFichero(String ruta, String nombre, String texto) {

		try {
			modelo.GuardarTexto(texto, nombre, ruta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean EliminarFichero(String ruta) {
		return modelo.Borrar(ruta);
	}

	private void CopiarFichero(String ficheroOrigen) {
		modelo.Copiar(ficheroOrigen);
	}

	private void RenombrarFichero(String nombreOriginal, String nombreMod) {
		modelo.Renombrar(nombreOriginal, nombreMod);
	}

	private String BuscarPalabra(String palabraBuscar, String textoOriginal) {
		return modelo.Buscar(palabraBuscar, textoOriginal);
	}

	private String ReemplazarPalabra(String palabraBuscar, String textoOriginal, String palabraReemplazar) {
		return modelo.Reemplazar(palabraBuscar, textoOriginal, palabraReemplazar);
	}

}
