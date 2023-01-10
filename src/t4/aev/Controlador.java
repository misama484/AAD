package t4.aev;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controlador {

	private Modelo modelo;
	private Vista vista;
	private Anyadir anyadir;
	

	private ActionListener ALConectar, ALAnyadirLibro;

	// CONSTRUCTOR
	public Controlador(Modelo modelo, Vista vista, Anyadir anyadir) {
		this.modelo = modelo;
		this.vista = vista;
		this.anyadir = anyadir;
		control();
	}

	private void control() {
		
		//conecta bd y muestra lista de bd's
		ALConectar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				vista.getTextAreaPrincipal().setText("");
				ArrayList<String> listaBd = conectaMongo();
				for(String bdName : listaBd) {
					vista.getTextAreaPrincipal().append(bdName + "\n");
				}	
				vista.getTextAreaTablas().setText(modelo.ElementosBD());
				
			}
			
		};
		vista.getBtnConectar().addActionListener(ALConectar);
		
		ALAnyadirLibro = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Anyadir anyadir = new Anyadir();
				anyadir.setVisible(true);
				
				
			}
			
		};
		vista.getBtnMostrarTablas().addActionListener(ALAnyadirLibro);
	}

	private ArrayList<String> conectaMongo() {
		//String usuario = panelLogin.getTextUser().getText();
		//String password = panelLogin.getTextPassword().getText();
		return modelo.conectaMongo();
	}

}
