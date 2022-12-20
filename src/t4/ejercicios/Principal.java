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
		ArrayList<String> libro = new ArrayList<String>();
		while(linea != null) {		//cortar el string linea en subString para crear el objeto libro
			System.out.println(linea);
			libros.add(linea);
			//String[] arrayDatos = linea.split(";");			
			linea = br.readLine();
		}
		
		//POSIBILIDAD DE PASAR DIRECTAMENTE LA LINEA CON LOS DATOS DEL LIBRO COMO JSON DIRECTAMENTE A MONGO??
		
		for(String lib : libros) {
			String[] arrayLibro = lib.split(";");
			for(int i = 0; i < arrayLibro.length ; i++) {	//extraer datos del array(datos de cada libro) y crear objeto Libro
				String titulo = arrayLibro[i];
			}
			
			System.out.println("Libro => " + lib);
		}
		
		
		
		/*
		//Iniciamos la conexion
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		List<String> DBName = mongoClient.getDatabaseNames();
		//JOptionPane.showMessageDialog(null, DBName.toString());
		MongoDatabase database = mongoClient.getDatabase("Biblioteca");	//CAMBIAR A NOMBRE DE NUEVA BASE DE DATOS PARA PODER CREARLA
		MongoCollection<Document> collection = database.getCollection("Libros");
		
		//OPERACIONES CRUD	
		
		//recuperar lista de elementos en coleccion(Tabla)
		//MongoCursor<Document> cursor = collection.find(Bson query = eq("Titulo", "Lazarillo de Tormes")).iterator(); //CON EL FILTRO NO FUNCIONA, NO RECONOCE Bson
		MongoCursor<Document> cursor = collection.find().iterator();
		while(cursor.hasNext()) {
			//System.out.println(cursor.next().toJson());
			//JOptionPane.showMessageDialog(null, cursor.next().toJson());
			
			JSONObject obj = new JSONObject(cursor.next().toJson());	
			System.out.println(obj.getString("Titulo"));
			//System.out.println(cursor.next().toJson());
		}
	
		
		//con esto cerrmos la conexion a la bd
		mongoClient.close();
*/
	}

}
