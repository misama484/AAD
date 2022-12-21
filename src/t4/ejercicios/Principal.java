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
		
		//preparamos el archivo csv para extraer los datos y crear objetos libro
		//recibir fichero csv y pasar a objetos libro?
		//pasar a arrays objetos libro y subir a mongodb?
		File datosCsv = new File("Ejercicios_T4_MongoDB_Datos.csv");
		FileReader fr = new FileReader(datosCsv);
		BufferedReader br = new BufferedReader(fr);
		String linea = br.readLine();
		ArrayList<String> libros = new ArrayList<String>();
		ArrayList<Libro> biblioteca = new ArrayList<Libro>();
		
		while(linea != null) {		//cortar el string linea en subString para crear el objeto libro
			System.out.println(linea);
			libros.add(linea);		
			linea = br.readLine();
		}
		
		//POSIBILIDAD DE PASAR DIRECTAMENTE LA LINEA CON LOS DATOS DEL LIBRO COMO JSON DIRECTAMENTE A MONGO??
		libros.remove(0);//eliminamos primera linea que contiene el nombre de los campos
		
		for(String lib : libros) {	//Creamos objetos libro y Anyadimos los libros a la lista de libros (Pasamos los datos numericos a int, con una comrpbacion para si falta algun dato, que lo sustituya por "0".)
			String[] arrayLibro = lib.split(";");
			for(int i = 0; i < arrayLibro.length; i++) {
				if(arrayLibro[i] == "") {
					arrayLibro[i] = "0";
				}
			}
			Libro book = new Libro(Integer.parseInt(arrayLibro[0]), arrayLibro[1], arrayLibro[2], Integer.parseInt(arrayLibro[3]), Integer.parseInt(arrayLibro[4]), arrayLibro[5],Integer.parseInt(arrayLibro[6]));
			biblioteca.add(book);
			
		}
		
		for (Libro lib : biblioteca) {
			System.out.println("Libro => " + lib.toString());
		}
		
		System.out.println("\n===== ACCESO A MONGO =====\n");
		//INICIAMOS LA CONEXION
		//CREAMOS LA BASE DE DATOS
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		//List<String> DBName = mongoClient.getDatabaseNames();
		//JOptionPane.showMessageDialog(null, DBName.toString());
		MongoDatabase database = mongoClient.getDatabase("Biblioteca2");	//CAMBIAR A NOMBRE DE NUEVA BASE DE DATOS PARA PODER CREARLA
		MongoCollection<Document> collection = database.getCollection("Libros2");
		
		//OPERACIONES CRUD	
		/*
		//creamos una lista de documentos y le anyadimo cada documento con los datos de los libros del ArrayList biblioteca
		//insertamos la coleccion en la bd creada.
		ArrayList<Document> listaDocs = new ArrayList<Document>();
		for(Libro libro : biblioteca) {
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
		*/
		
		//RECUPERAR LISTA DE ELEMENTOS DE LA COLECION(Tabla)
		//MongoCursor<Document> cursor = collection.find(Bson query = eq("Titulo", "Lazarillo de Tormes")).iterator(); //CON EL FILTRO NO FUNCIONA, NO RECONOCE Bson
		MongoCursor<Document> cursor = collection.find().iterator();
		while(cursor.hasNext()) {
			//System.out.println(cursor.next().toJson());
			//JOptionPane.showMessageDialog(null, cursor.next().toJson());
			
			JSONObject obj = new JSONObject(cursor.next().toJson());	
			System.out.println(obj.getString("titulo"));
			//System.out.println(cursor.next().toJson());
		}
	
		
		//con esto cerrmos la conexion a la bd
		mongoClient.close();

	}

}
