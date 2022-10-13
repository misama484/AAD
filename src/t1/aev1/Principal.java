package t1.aev1;


public class Principal {

	public static void main(String[] args) {
		Modelo modelo = new Modelo();		
		Vista vista = new Vista();
		Editar editar = new Editar();
		Controlador controlador = new Controlador(modelo, vista, editar);

	}

}



