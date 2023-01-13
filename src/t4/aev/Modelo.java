package t4.aev;

import static com.mongodb.client.model.Filters.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Modelo {
	File fichero = new File("DatosConexion.json");

	private String usuario;
	private String password;
	private String ip;
	private int port;
	private String bdName;
	private String collection;
	private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	private String usuarioLog;
	private Boolean userLogged = false;

	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collectionBooks;
	MongoCollection<Document> collectionUsers;
	MongoCursor<Document> cursor;

	// GETTERS & SETTERS
	public Boolean getUserLogged() {
		return userLogged;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}

	public String getBdName() {
		return bdName;
	}

	public String getCollection() {
		return collection;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setBdName(String bdName) {
		this.bdName = bdName;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	// CARGAR FICHERO JSON
	public String cargaJson(File ficheroJSON) {
		return null;
	}

	// EXTRAER DATOS DE FICHERO JSON
	// recibe un fichero json y extrae los datos para asignarlos a las variables de
	// conexion
	// modificando el json deveriamos poder conectar con AWS
	public void DatosConexion() {
		String datosJson = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("DatosConexion.json"));
			String linea;
			while ((linea = br.readLine()) != null) {
				datosJson += linea;
			}
			br.close();
			System.out.println(datosJson);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// convertimos a objeto JSON
		JSONObject jsonCon = new JSONObject(datosJson);
		System.out.println("String json => " + jsonCon.toString());

		setIp(jsonCon.getString("ip"));
		setPort(jsonCon.getInt("port"));
		setBdName(jsonCon.getString("bdname"));
		setCollection(jsonCon.getString("collectionname"));
	}

	// CONECTAR CON DB
	// metodo que conecta con mongo y muestra listado de bases de datos en
	// "servidor"
	// devuelve una lista con las bd
	// en un futuro, pasar ip y puerto por parametros, a partir de doc.json para
	// conexion a server AWS
	public ArrayList<String> conectaMongo() {
		ArrayList<String> results = new ArrayList<String>();
		DatosConexion();
		// conectamos con mongo(en futuro, pasar como parametros ip y puerto)
		mongoClient = new MongoClient(getIp(), getPort());
		database = mongoClient.getDatabase(getBdName());
		collectionUsers = database.getCollection("users");
		if (validaUsuario()) {
			collectionBooks = database.getCollection(getCollection());
			MongoCursor<Document> cursor;
			// List<String> dbList = mongoClient.getDatabaseNames();
			// getDatabaseNames esta deprecado, usar este
			cursor = collectionBooks.find().iterator();
			// controlamos que el id exista
			if (!cursor.hasNext()) {
				System.err.println("El id no existe");
			}
			while (cursor.hasNext()) {
				JSONObject obj = new JSONObject(cursor.next().toJson());
				Integer id2 = obj.getInt("Id");
				Integer anyoNacimiento = obj.getInt("Anyo_nacimiento");
				Integer anyoPublicacion = obj.getInt("Anyo_publicacion");
				Integer numPaginas = obj.getInt("Numero_paginas");
				results.add("ID: " + id2.toString() + "\n - TITULO: " + obj.getString("Titulo") + " \n- AUTOR: "
						+ obj.getString("Autor"));
			}
		} else {
			JOptionPane.showMessageDialog(null, "Credenciales erroneas", "Alerta", JOptionPane.WARNING_MESSAGE);
			mongoClient.close();
		}
		userLogged = true;

		return results;
	}

	// comprueba que los datos introducidos corresponden con los almacenados en BD
	public boolean validaUsuario() {

		String usuario = JOptionPane.showInputDialog("Introduzca nombre de usuario:");
		setUsuario(usuario);

		String password = JOptionPane.showInputDialog("Introduzca contrasenya:");

		String passEncrypt = encriptarContrasenya(password);
		Usuario user = null;

		cursor = collectionUsers.find().iterator();
		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			String userName = obj.getString("user");
			String passw = obj.getString("pass");
			user = new Usuario(userName, passw);
		}

		usuarios.add(user);

		boolean userVal = false;
		boolean userPass = false;

		for (Usuario usuarioList : usuarios) {
			if (usuarioList.getNombre().equalsIgnoreCase(usuario)) {
				userVal = true;
				usuarioLog = usuario;
				if (usuarioList.getPassword().equals(passEncrypt)) {
					userPass = true;
				}
			}
		}

		if (userVal && userPass) {
			return true;
		} else {
			return false;
		}
	}

	// encripta la contrasenya en sha-256
	public String encriptarContrasenya(String contrasenya) {
		String passwordToHash = contrasenya;
		String generatedPassword = null;

		try {
			// Create MessageDigest instance for MD5
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// Add password bytes to digest
			md.update(passwordToHash.getBytes());

			// Get the hash's bytes
			byte[] bytes = md.digest();

			// This bytes[] has bytes in decimal format. Convert it to hexadecimal format
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}

			// Get complete hashed password in hex format
			generatedPassword = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return generatedPassword;
	}

	public String ElementosBD() {
		Integer elementosBD = (int) collectionBooks.count();
		String elementos = elementosBD.toString();
		return ("Cantidad de elementos en coleccion: " + elementos);
	}

	// anyade un libro a la coleccion, le pasamos un objeto libro y lo anyade.
	public String AnyadeLibro(Libro libro) {
		int id = (int) (collectionBooks.count() + 1);
		Document doc = new Document();
		doc.append("Id", id);
		doc.append("Titulo", libro.getTitulo());
		doc.append("Autor", libro.getAutor());
		doc.append("Anyo_nacimiento", libro.getAnyNacimiento());
		doc.append("Anyo_publicacion", libro.getAnyoPublicacion());
		doc.append("Editorial", libro.getEditorial());
		doc.append("Numero_paginas", libro.getNumPaginas());
		doc.append("Thumbnail", libro.getImagen());

		collectionBooks.insertOne(doc);
		String response = "Anyadido libro: " + libro.getTitulo() + " a la base de datos" + doc.toString();
		return response;
	}

	public Libro CargarLibro(int id) {
		Integer id2 = null, anyoNacimiento = null, anyoPublicacion = null, numPaginas = null;
		String titulo = null, autor = null, editorial = null, imagen = null;

		Bson query = eq("Id", id);
		cursor = collectionBooks.find(query).iterator();
		// controlamos que el id exista
		if (!cursor.hasNext()) {
			System.err.println("El id no existe");
			JOptionPane.showMessageDialog(new JFrame(), "EL ID NO EXISTE", "ERROR", JOptionPane.WARNING_MESSAGE);
		}
		while (cursor.hasNext()) {
			// System.out.println(cursor.next().toJson());
			JSONObject obj = new JSONObject(cursor.next().toJson());
			id2 = obj.getInt("Id");
			titulo = obj.getString("Titulo");
			autor = obj.getString("Autor");
			anyoNacimiento = obj.getInt("Anyo_nacimiento");
			anyoPublicacion = obj.getInt("Anyo_publicacion");
			editorial = obj.getString("Editorial");
			numPaginas = obj.getInt("Numero_paginas");
			imagen = obj.getString("Thumbnail");
			/*
			 * System.out.println("ID: " + id2.toString() + " - TITULO: " +
			 * obj.getString("Titulo") + " - AUTOR: " + obj.getString("Autor") +
			 * " - ANYO DE NACIMIENTO: " + anyoNacimiento.toString() +
			 * " - ANYO DE PUBLICACION: " + anyoPublicacion.toString() + " - EDITORAL: " +
			 * obj.getString("Editorial") + " - NUMERO DE PAGINAS: " +
			 * numPaginas.toString());
			 */
		}

		Libro libro = new Libro(id2, titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas, imagen);
		return libro;
	}

	public String EditarLibro(Libro libro) {
		int input = JOptionPane.showConfirmDialog(null, "�Desear editar el libro?", "Atenci�n",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (input == 0) {
			Integer id2 = null, anyoNacimiento = null, anyoPublicacion = null, numPaginas = null;
			String titulo = null, autor = null, editorial = null, imagen = null;
			String response = "";
			// localizamos libro
			int id = libro.getId();
			Bson query = eq("Id", id);
			cursor = collectionBooks.find(query).iterator();
			// controlamos que el id exista
			if (!cursor.hasNext()) {
				response = "ID NO ENCONTRADO";
			}

			// almacenamos el libro en JSON para acceder a los campos
			JSONObject obj = new JSONObject();
			while (cursor.hasNext()) {
				obj = new JSONObject(cursor.next().toJson());
				id2 = obj.getInt("Id");
				titulo = obj.getString("Titulo");
				autor = obj.getString("Autor");
				anyoNacimiento = obj.getInt("Anyo_nacimiento");
				anyoPublicacion = obj.getInt("Anyo_publicacion");
				editorial = obj.getString("Editorial");
				numPaginas = obj.getInt("Numero_paginas");
				imagen = obj.getString("Thumbnail");

			}
			// comprobamos si los campos son distintos y enviamos la modificacion
			if (titulo != libro.getTitulo()) {
				collectionBooks.updateOne(eq("Titulo", obj.getString("Titulo")),
						new Document("$set", new Document("Titulo", libro.getTitulo().toString())));
			}
			if (autor != libro.getAutor()) {
				collectionBooks.updateOne(eq("Autor", obj.getString("Autor")),
						new Document("$set", new Document("Autor", libro.getAutor().toString())));
			}
			if (anyoNacimiento != libro.getAnyNacimiento()) {
				collectionBooks.updateOne(eq("Anyo_nacimiento", obj.getInt("Anyo_nacimiento")),
						new Document("$set", new Document("Anyo_nacimiento", libro.getAnyNacimiento())));
			}
			if (anyoPublicacion != libro.getAnyoPublicacion()) {
				collectionBooks.updateOne(eq("Anyo_publicacion", obj.getInt("Anyo_publicacion")),
						new Document("$set", new Document("Anyo_publicacion", libro.getAnyoPublicacion())));
			}
			if (editorial != libro.getEditorial()) {
				collectionBooks.updateOne(eq("Editorial", obj.getString("Editorial")),
						new Document("$set", new Document("Editorial", libro.getEditorial())));
			}
			if (numPaginas != libro.getNumPaginas()) {
				collectionBooks.updateOne(eq("Numero_paginas", obj.getInt("Numero_paginas")),
						new Document("$set", new Document("Numero_paginas", libro.getNumPaginas())));
			}
			if (imagen != libro.getImagen()) {
				collectionBooks.updateOne(eq("Thumbnail", obj.getString("Thumbnail")),
						new Document("$set", new Document("Thumbnail", libro.getImagen())));
			}

			response = "Editado libro: " + libro.getTitulo() + " en la base de datos";
			return response;
		}
		return "";
	}

	// recibe el campo y el valor a buscar, devuelve el libro
	public String BuscarLibro(String campo, String valor) {

		String response = null;
		Integer id = null, anyoNacimiento = null, anyoPublicacion = null, numPaginas = null;
		String titulo = null, autor = null, editorial = null, imagen = null;
		

		Bson query = null;
		if (campo == "Id" || campo == "Anyo_nacimiento" || campo == "Anyo_publicacion" || campo == "Num_paginas") {
				int valor2 = Integer.parseInt(valor);
				query = eq(campo, valor2);
			} else {
				query = eq(campo, valor);
			}

		cursor = collectionBooks.find(query).iterator();
		// controlamos que el id exista
		if (!cursor.hasNext()) {
			response = "DATOS NO ENCONTRADOS";
			return response;
		}

		// almacenamos el libro en JSON para acceder a los campos
		JSONObject obj = new JSONObject();
		while (cursor.hasNext()) {
			obj = new JSONObject(cursor.next().toJson());
			id = obj.getInt("Id");
			titulo = obj.getString("Titulo");
			autor = obj.getString("Autor");
			anyoNacimiento = obj.getInt("Anyo_nacimiento");
			anyoPublicacion = obj.getInt("Anyo_publicacion");
			editorial = obj.getString("Editorial");
			numPaginas = obj.getInt("Numero_paginas");
			imagen = "";
			Libro libro = new Libro(id, titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas, imagen);
			response = libro.toString();
		}
		System.out.println(obj.toString());

		
		
		return response;
	}
	
	
	// recibe el campo y el valor a buscar, devuelve el libro
		public ArrayList<Libro> BuscarLibroCriterio(String campo, String valor, String parametroBusqueda) {

			ArrayList<Libro> response = new ArrayList<Libro>();
			Integer id = null, anyoNacimiento = null, anyoPublicacion = null, numPaginas = null;
			String titulo = null, autor = null, editorial = null, imagen = null;
			Bson query = null;
			if(parametroBusqueda.equals("eq")) {			
				if (campo == "Id" || campo == "Anyo_nacimiento" || campo == "Anyo_publicacion" || campo == "Num_paginas") {
					int valor2 = Integer.parseInt(valor);
					query = eq(campo, valor2);
				} else {
					query = eq(campo, valor);
				}
			}
			
			if(parametroBusqueda.equals("gte")) {			
				if (campo == "Id" || campo == "Anyo_nacimiento" || campo == "Anyo_publicacion" || campo == "Num_paginas") {
					int valor2 = Integer.parseInt(valor);
					query = gte(campo, valor2);
				} else {
					query = gte(campo, valor);
				}
			}
			if(parametroBusqueda.equals("lte")) {			
				if (campo == "Id" || campo == "Anyo_nacimiento" || campo == "Anyo_publicacion" || campo == "Num_paginas") {
					int valor2 = Integer.parseInt(valor);
					query = lte(campo, valor2);
				} else {
					query = lte(campo, valor);
				}
			}
			

			cursor = collectionBooks.find(query).iterator();
			// controlamos que el id exista
			if (!cursor.hasNext()) {
				//response.add("DATOS NO ENCONTRADOS");
				return null;
			}

			// almacenamos el libro en JSON para acceder a los campos
			JSONObject obj = new JSONObject();
			while (cursor.hasNext()) {
				obj = new JSONObject(cursor.next().toJson());
				id = obj.getInt("Id");
				titulo = obj.getString("Titulo");
				autor = obj.getString("Autor");
				anyoNacimiento = obj.getInt("Anyo_nacimiento");
				anyoPublicacion = obj.getInt("Anyo_publicacion");
				editorial = obj.getString("Editorial");
				numPaginas = obj.getInt("Numero_paginas");
				imagen = "";
				Libro libro = new Libro(id, titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas, imagen);
				response.add(libro);
			}
			System.out.println(obj.toString());

			
			
			return response;
		}
	

	public boolean BorrarLibro(String campo, String valor) {
		String response = "";
		Integer id = null, anyoNacimiento = null, anyoPublicacion = null, numPaginas = null;
		String titulo = null, autor = null, editorial = null, imagen = null;

		Bson query;
		if (campo == "Id" || campo == "Anyo_nacimiento" || campo == "Anyo_publicacion" || campo == "Num_paginas") {
			int valor2 = Integer.parseInt(valor);
			query = eq(campo, valor2);
		} else {
			query = eq(campo, valor);
		}
		cursor = collectionBooks.find(query).iterator();
		// controlamos que el id exista
		if (!cursor.hasNext()) {
			System.err.println("El id no existe");
		}
		JSONObject obj = new JSONObject();
		while (cursor.hasNext()) {
			obj = new JSONObject(cursor.next().toJson());
			id = obj.getInt("Id");
			titulo = obj.getString("Titulo");
			autor = obj.getString("Autor");
			anyoNacimiento = obj.getInt("Anyo_nacimiento");
			anyoPublicacion = obj.getInt("Anyo_publicacion");
			editorial = obj.getString("Editorial");
			numPaginas = obj.getInt("Numero_paginas");
			imagen = obj.getString("Thumbnail");
		}

		collectionBooks.deleteOne(query);

		return true;

	}

}
