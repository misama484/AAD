/*package t4.aev;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CodigoSuelto {
	
	// funcion del boton BUSCARLIBRO
			ALBuscarLibro = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					Buscar buscador = new Buscar();
					buscador.getComboBox().addItem("Titulo");
					buscador.setVisible(true);
					
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

if(seleccion == "Id" ||seleccion == "Anyo_nacimiento" || seleccion == "Anyo_publicacion" || seleccion == "Num_paginas") {
								int seleccionInt = Integer.parseInt(seleccion);
								buscar.getEditorPane().setText(modelo.BuscarLibro(seleccionInt, valor));
							}
*/