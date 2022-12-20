package t4.ejercicios;

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

	public static void main(String[] args) {
		
		//Iniciamos la conexion
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		List<String> DBName = mongoClient.getDatabaseNames();
		//JOptionPane.showMessageDialog(null, DBName.toString());
		MongoDatabase database = mongoClient.getDatabase("Biblioteca");
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

	}

}
