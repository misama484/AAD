package t4.ejercicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.*;

public class Principal {

	public static void main(String[] args) throws IOException {

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

		for (Libro lib : biblioteca) {
			System.out.println("Libro => " + lib.toString());
		}
		
		//CONECTAMOS A MONGODB

		// Conectamos con la BD de mongo, como no existe, la creara, junto con la
		// coleccion

		System.out.println("\n===== ACCESO A MONGO =====\n");
		
		// INICIAMOS LA CONEXION
		// CREAMOS LA BASE DE DATOS
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase database = mongoClient.getDatabase("Biblioteca2"); 
		MongoCollection<Document> collection = database.getCollection("Libros2");

		//CARGAMOS DATOS EN MONGO
		// Cargamos los datos a la bd de mongo, obteniendolos de la lista biblioteca

		// creamos una lista de documentos y le anyadimos cada documento con los datos de
		// los libros del ArrayList biblioteca
		// insertamos la coleccion en la bd creada.
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

		// RECUPERAR LISTA DE ELEMENTOS DE LA COLECION(Tabla)
		// MongoCursor<Document> cursor = collection.find(Bson query = eq("Titulo",
		// "Lazarillo de Tormes")).iterator(); //CON EL FILTRO NO FUNCIONA, NO RECONOCE
		// Bson
		MongoCursor<Document> cursor = collection.find().iterator();
		while (cursor.hasNext()) {
			// System.out.println(cursor.next().toJson());
			// JOptionPane.showMessageDialog(null, cursor.next().toJson());
			JSONObject obj = new JSONObject(cursor.next().toJson());
			System.out.println(obj.getString("autor"));
			// System.out.println(cursor.next().toJson());
		}

		// OBTENER EL TAMANYO DE LA COLECCION
		System.out.println("Tamanyo de la coleccion ==> " + collection.count() + " elementos.");

		// OPERACIONES CRUD
		// insertamos un documento manualmente, creando un objeto libro y anyadiendolo a la bd

		// INSERTAR NUEVOS ELEMENTOS EN LA COLECCION
		System.out.println("==> Insertar nuevos elementos en la coleccion <==");
		// Creamos un objeto libro
		Libro libro = new Libro((int) (collection.count() + 1), "El boson de Higgs no te va a hacer la cama",
				"Javier Santaolalla", 1982, 2016, "La esfera de libros", 369);
		Document doc = new Document();
		doc.append("id", libro.getId());
		doc.append("titutlo", libro.getTitulo());
		doc.append("autor", libro.getAutor());
		doc.append("anyoNacimiento", libro.getAnyNacimiento());
		doc.append("anyoPublicacion", libro.getAnyoPublicacion());
		doc.append("editorial", libro.getEditorial());
		doc.append("paginas", libro.getNumPaginas());

		collection.insertOne(doc);


		// ACTUALIZAR ELEMENTOS DE LA COLECCION
		// mostramos los elementos de la coleccion
		System.out.println("============");
		cursor = collection.find().iterator();
		while (cursor.hasNext()) {
			System.out.println(cursor.next().toJson());
		}

		// actualizamos solo un documento
		System.out.println("==== Actualizamos elementos ======");
		collection.updateOne(eq("anyoNacimiento", 0), new Document("$set", new Document("anyoNacimiento", 10)));

		// volvemos a mostrar el contenido de la coleccion
		System.out.println("============");
		cursor = collection.find().iterator();
		while (cursor.hasNext()) {
			System.out.println(cursor.next().toJson());
		}

		//BORRADO DE DOCUMENTO

		collection.deleteOne(eq("autor", "Javier Santaolalla"));

		System.out.println("============");
		cursor = collection.find().iterator();
		while (cursor.hasNext()) {
			System.out.println(cursor.next().toJson());
		}

		// con esto cerrmos la conexion a la bd
		mongoClient.close();

	}

}
