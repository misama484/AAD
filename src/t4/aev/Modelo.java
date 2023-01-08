package t4.aev;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Modelo {
	
	// INICIAMOS LA CONEXION
	//en mongoCient colocaremos la direccion y puerto del servidor de AWS
	MongoClient mongoClient = new MongoClient("localhost", 27017);
	MongoDatabase database = mongoClient.getDatabase("BibliotecaAEV3");
	MongoCollection<Document> collectionBooks = database.getCollection("books");
	MongoCollection<Document> collectionUsers = database.getCollection("users");
	
	//comprobar que puede accder a la bd, consultando la cantidad de elementos
	Integer elementosBd = (int) collectionBooks.count();
	

}
