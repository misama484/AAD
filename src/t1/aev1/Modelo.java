package t1.aev1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Modelo {

	private String ficheroLectura, ficheroEscritura, ruta;
	
	/**	
	 * CONSTRUCTOR
	 */
	public Modelo() {
		ficheroLectura = "";
		ficheroEscritura = "";
		ruta = "";
	}
	/**
	 * Getters & Setters
	 */

	public String getFicheroLectura() {
		return ficheroLectura;
	}

	public String getFicheroEscritura() {
		return ficheroEscritura;
	}

	public void setFicheroLectura(String fichero) {
		ficheroLectura = fichero;
	}
	
	/**
	 * metodo que recibe un nombre de fichero de texto, crea un objeto File y lo devuelveel contenido en un array
	 * @param String con el nombre del fichero
	 * @return ArrayList<String> con el contenido del fichero
	 */

	public ArrayList<String> MostrarTexto(String rutaFicheroLectura) {
		ArrayList<String> contenidoFichero = new ArrayList<String>();
		File fichero = new File(rutaFicheroLectura);
		ruta = rutaFicheroLectura;
		
		if (!fichero.exists()) {
			try {
				fichero.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(fichero);
			br = new BufferedReader(fr);
			String linea = br.readLine();
			while (linea != null) {
				contenidoFichero.add(linea);
				linea = br.readLine();
			}
			fr.close();
			br.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}

		return contenidoFichero;
	}

	/**
	 * Metodo que devuelve un Array con las caracteristicas del fichero
	 * @param String con la rita absoluta del fichero
	 * @returnArray con las propiedades del fichero formateadas para mostrar por tantalla/consola.
	 * @throws IOException
	 */
	public ArrayList<String> getDatosFichero(String ruta) throws IOException {

		File fichero = new File(ruta);
		String nombre = fichero.getName();
		int extension = nombre.indexOf(".");
		String tipo = nombre.substring(extension);
		Integer peso = (int) (fichero.length() / 100);
		String tamanyo = peso.toString();
		BasicFileAttributes atributos = Files.readAttributes(fichero.toPath(), BasicFileAttributes.class);
		FileTime tiempo = atributos.creationTime();
		String modelo = "dd-mm-yyyy HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(modelo);
		String fechaCreacion = sdf.format(new Date(tiempo.toMillis()));

		ArrayList<String> datos = new ArrayList<String>();
		datos.add("DATOS DE FICHERO" + "\n");
		datos.add("Nombre del fichero: " + nombre);
		datos.add("Tipo de fichero: " + tipo);
		datos.add("Tamanyo del fichero: " + tamanyo + " MB");
		datos.add("Fecha creacion: " + fechaCreacion);

		return datos;
	}

	/**
	 * Metodo que dado un texto en String, nos crea un fichero y nos lo guarda en la ubicaion elegida
	 * @param String con el texto a guardar.
	 * @param String con el nombre del fichero
	 * @param String con la ruta donde se guardara el fichero.
	 * @throws IOException
	 */
	public void GuardarTexto(String texto, String nombre, String ruta) throws IOException {

		File FicheroNuevo = new File(nombre);
		if(FicheroNuevo.exists()) {
			JOptionPane.showConfirmDialog(null, "Desea sobreescribir el archivo?", "Alerta!", JOptionPane.YES_NO_OPTION);
		}

		FileWriter fw;
		BufferedWriter bw;
		try {
			fw = new FileWriter(ruta);
			bw = new BufferedWriter(fw);
			bw.write(texto);
			bw.close();
			fw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Metodo que recibe una ruta por parametro , comprieba si el fichero existe y de ser correcto, lo borra.
	 * @param String con la ruta
	 * @return booleano con el resultado de la operacion
	 */

	public boolean Borrar(String ruta) {
		File fichero = new File(ruta);
		if (!fichero.exists()) {
			return false;
		}
		fichero.delete();
		if (fichero.exists()) {
			return false;
		}
		return true;
	}

	/**
	 * metodo que recibe un String con el nombre de un fichero, lo lee y crea una copia del mismo con un nombre distinto
	 * @param String con el fichero de origen
	 * no devuelve nada
	 */
	public void Copiar(String ficheroOrigen) {
		File origen = new File(ficheroLectura);
		String[] nombre = origen.getName().split(".txt");
		String rutaDestino = nombre[0] + "_copia.txt";

		// System.out.println(rutaDestino);

		File destino = new File(rutaDestino);
		try {
			FileReader fr = new FileReader(origen);
			BufferedReader br = new BufferedReader(fr);
			String linea = br.readLine();

			FileWriter fw = new FileWriter(destino);
			BufferedWriter bw = new BufferedWriter(fw);

			while (linea != null) {
				bw.write(linea);
				bw.newLine();
				linea = br.readLine();
			}
			bw.close();
			fw.close();
			br.close();
			fr.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	/**
	 * Metodo que recibe un String con el nobre de un fichero y otro String con el nombre a modoficar, 
	 * @param String con el nombre original
	 * @param String con el nombre a modificar
	 */
	public void Renombrar(String nombre, String nombreMod) {
		File fichero = new File(nombre);
		System.out.println(nombreMod + ".txt");
		int option = JOptionPane.showConfirmDialog(null, "Confirma renombrar fichero?", "Alerta!",
				JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if (option == 0) {
			fichero.renameTo(new File(nombreMod + ".txt"));
		} else if (option == 1) {
			JOptionPane.showMessageDialog(null, "Operacion cancelada");
		} else {
			JOptionPane.showMessageDialog(null, "Operacion Cancelada", "Alerta!", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * Metodo que reemplaza palabras de un texto
	 * @param String con la palabra a buscar
	 * @param String con el texto
	 * @param String con la palabra a reemplazar
	 * @return String con el texto una vez reemplazadas las palabras
	 */
	public String Reemplazar(String palabraBuscar, String textoOriginal, String palabraReemplazar) {

		String nuevoTexto = "";
		nuevoTexto = textoOriginal.replaceAll(palabraBuscar, palabraReemplazar);

		return nuevoTexto;
	}

	/**
	 * metodo que busca palabras en un texto pasado por parametro
	 * @param String con la palabra a buscar
	 * @param String con el texto
	 * @param textoResaltado
	 * @return String con el numero de veces que aparece la palabra
	 */
	public String Buscar(String palabraBuscar, String textoOriginal) {

		String arrayTexto[] = textoOriginal.split("\n");
		Integer count = 0;
		if (palabraBuscar != "") {
			for (int i = 0; i < arrayTexto.length; i++) {
				if (arrayTexto[i].indexOf(palabraBuscar) != -1) {
					System.out.println(count);
					count++;
				}
			}
		}else {
			count = -1;
		}

		return count.toString();
	}


}
