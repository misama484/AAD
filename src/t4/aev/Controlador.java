package t4.aev;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controlador {
	
	private Modelo modelo;
	private Vista vista;
	
	private ActionListener ALConectar;
	
	//CONSTRUCTOR
	public Controlador(Modelo modelo, Vista vista) {
		this.modelo = modelo;
		this.vista = vista;
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
				
			}
			
		};
		vista.getBtnConectar().addActionListener(ALConectar);
		
	}
	private ArrayList<String> conectaMongo() {
		return modelo.conectaMongo();
	}

}
