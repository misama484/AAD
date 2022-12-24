/*
Crea en MongoDB Compass una base de datos que se llame “Biblioteca” (con mayúscula).
Importa el csv proporcionado para la actividad como una colección nueva de Biblioteca que se
llamará “Libros”.
Se pide desarrollar un proyecto Java en Eclipse que gestione objetos libro como documentos y
asegure su persistencia como una colección en la base de datos MongoDB.
Concretamente, se pide adaptar las funcionalidades que has desarrollado en el tema anterior al
entorno No-SQL de MongoDB:
• Mostrar todos los ids y los títulos de la biblioteca.
• Mostrar la información detallada de un libro a partir de su id.
• Añadir un nuevo libro a la biblioteca.
• Modificar atributos de un libro a partir de su id.
• Borrar un libro a partir de su id.
• Como id puedes utilizar el del csv o el que autogenera MongoDB. El primero es user-friendly,
  pero hay que controlarlo y gestionarlo en la aplicación Java. El segundo no es user-friendly,
  pero la gestión corre a cargo de MongoDB.
• Se accederá a cada una de las funcionalidades a través de un menú de texto que mostrará
  la aplicación cuando arranque.
• La implementación de los métodos necesarios para conseguir las funcionalidades es libre.
*/
package t4.miniproyecto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import t4.ejercicios.Libro;
import static com.mongodb.client.model.Filters.*;

public class Principal {

	// Declaramos los elementos necesarios
	// entrada de teclado
	static Scanner teclado;
	//coleccion
	static MongoCollection<Document> collection;
	static MongoCursor<Document> cursor;

	public static void main(String[] args) throws IOException {

		// delaramos elementos necesarios
		teclado = new Scanner(System.in);
		// Creamos interfaz de usuario
		Consola("Sistema de gestion de bilioteca");
		Consola("Conectado con la base de datos...");
		// INICIAMOS LA CONEXION
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("Biblioteca");
		collection = database.getCollection("Libros");
		
		//importamos los libros del csv
		//ArrayList<Libro> biblioteca = importaDatos();
		//los anyadimos a la bd
		//agregaVarios(biblioteca);
		
		Integer elementosBd = (int) collection.count();
		Consola("Elementos en bd: " + elementosBd.toString());
		// Mostramos menu
		Consola("Introduzca la opcion deseada:");
		Consola("1. Crear libro");
		Consola("2. Mostrar libro");
		Consola("3. Borrar libro");
		Consola("4. Actualizar libro");
		Consola("5. Recuperar todos");
		Consola("6. Salir");
		
		//creamos interfaz de usuario
		int opcion = teclado.nextInt();
		while(true) {
			switch(opcion) {
			case 1:
				creaLibro();
				break;
			case 2:
				Consola("Introduzca id del libro: ");
				int id = teclado.nextInt();
				mostrarLibro(id);
				break;
			}
		}

	}

	private static void mostrarLibro(int id) {
		Bson query = eq("id", id);
		cursor = collection.find(query).iterator();
		while (cursor.hasNext()) {
			System.out.println(cursor.next().toJson());
		}
		
	}


	private static void creaLibro() {
		//AL EJECUTAR, MUESTRA DOS CAMPOS A LA VEZ
		Consola("Introduzca los datos del libro:");
		Consola("Titulo: ");
		String titulo = teclado.nextLine();
		Consola("Autor: ");
		String autor = teclado.nextLine();
		Consola("Anyo de Nacimiento:");
		int anyoNacimiento = teclado.nextInt();
		Consola("Anyo de Publicacion:");
		int anyoPublicacion = teclado.nextInt();
		Consola("Editorial:");
		String editorial = teclado.nextLine();
		Consola("Numero de Paginas:");
		int numPaginas = teclado.nextInt();
		int id = (int) (collection.count() + 1);
		//creamos objeto Libro
		Libro libro = new Libro(id, titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas);
		Consola(libro.toString());
		
		//ANYADIMOS A LA BD
		Document doc = new Document();
		doc.append("id", libro.getId());
		doc.append("titutlo", libro.getTitulo());
		doc.append("autor", libro.getAutor());
		doc.append("anyoNacimiento", libro.getAnyNacimiento());
		doc.append("anyoPublicacion", libro.getAnyoPublicacion());
		doc.append("editorial", libro.getEditorial());
		doc.append("paginas", libro.getNumPaginas());		
		collection.insertOne(doc);		
		
	}

	public static ArrayList<Libro> importaDatos() throws IOException {
		// Cargamos los datos desde un fichero .csv, creando objetos Libro y
		// anyadiendolos a la lista biblioteca
		// preparamos el archivo csv para extraer los datos y crear objetos libro
		File datosCsv = new File("Ejercicios_T4_MongoDB_Datos.csv");
		FileReader fr = new FileReader(datosCsv);
		BufferedReader br = new BufferedReader(fr);
		String linea = br.readLine();
		ArrayList<String> libros = new ArrayList<String>();
		ArrayList<Libro> biblioteca = new ArrayList<Libro>();
		// cortar el string linea en subString para crear el objeto libro
		while (linea != null) {
			System.out.println(linea);
			libros.add(linea);
			linea = br.readLine();
		}
		// eliminamos primera linea que contiene el nombre de los campos
		libros.remove(0);
		// Creamos objetos libro y Anyadimos los libros a la lista de libros (Pasamos
		// los datos numericos a int, con una comprobacion para si falta algun dato, que
		// lo sustituya por "0".)
		for (String lib : libros) {
			String[] arrayLibro = lib.split(";");
			for (int i = 0; i < arrayLibro.length; i++) {
				if (arrayLibro[i] == "") {
					arrayLibro[i] = "0";
				}
			}
			Libro book = new Libro(Integer.parseInt(arrayLibro[0]), arrayLibro[1], arrayLibro[2],
					Integer.parseInt(arrayLibro[3]), Integer.parseInt(arrayLibro[4]), arrayLibro[5],
					Integer.parseInt(arrayLibro[6]));
			biblioteca.add(book);
		}

		return biblioteca;
	}

	public static void agregaVarios(ArrayList<Libro> biblioteca) {
		ArrayList<Document> listaDocs = new ArrayList<Document>();
		for (Libro libro : biblioteca) {
			Document doc = new Document();
			doc.append("id", libro.getId());
			doc.append("titulo", libro.getTitulo());
			doc.append("autor", libro.getAutor());
			doc.append("anyoNacimiento", libro.getAnyNacimiento());
			doc.append("anyoPublicacion", libro.getAnyoPublicacion());
			doc.append("editorial", libro.getEditorial());
			doc.append("numPaginas", libro.getNumPaginas());
			listaDocs.add(doc);
		}
		collection.insertMany(listaDocs);
	}
	
	public static void Consola(String mensaje) {
		System.out.println(mensaje);
	}

}
