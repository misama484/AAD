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
	@SuppressWarnings("unused")
	private String usuarioLog;
	private Boolean userLogged = false;

	MongoClient mongoClient;
	MongoDatabase database;
	MongoCollection<Document> collectionBooks;
	MongoCollection<Document> collectionUsers;
	MongoCursor<Document> cursor;

	// GETTERS & SETTERS
	/**
	 * @return Booleano con el estado de logueo del usuario
	 */
	public Boolean getUserLogged() {
		return userLogged;
	}

	/**
	 * @return String con el nombre de usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @return String con la contrasenya
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Recibe un String y modifica el nombre de usuario
	 * @param usuario
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Recibeun String y modifica la contrasenya
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return String con la IP de conecion a la BD
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @return String con el puerto de conexion.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @return String con el nombre de la base de datos.
	 */
	public String getBdName() {
		return bdName;
	}

	/**
	 * @return String con el nombre de la coleccion en la BD.
	 */
	public String getCollection() {
		return collection;
	}

	/**
	 * Recibe un String y modifica la IP de conexion
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * Recibe un String y modifica el puerto de conexion
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Recibe un String y modifica el nombre de la BD
	 * @param bdName
	 */
	public void setBdName(String bdName) {
		this.bdName = bdName;
	}

	/**
	 * Recibe un String y modifica el nombre de la coleccion
	 * @param collection
	 */
	public void setCollection(String collection) {
		this.collection = collection;
	}



	
	
	/**EXTRAER DATOS DE FICHERO JSON
	 * No recibe ni retorna parametros, carga un fichero JSON con los datos de conexion, los gestiona y
	 * asigna a los Setters de los parametros de la clase.
	 * Los parametros son IP, Puerto de conexion, nombre de BD y nombre de coleccion
	 */
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

	

	/**CONECTAR CON MONGODB
	 * Recibe String con usuario y password, llama al metodo DatosConexion() que carga los datos de conexion desde un fichero JSON.
	 * Inicia la conexion con MongoDB para acceder a la coleccion de usuarios y validar el usuario y password mediante el metodo ValidaUsuaio(),
	 * si ValidaUsuario() es true, mestra un popup con un mensaje de confirmacion y continua cargando colecciones y resto de conexion.
	 * En caso de false, muestra una alerta y cierra la conexion.
	 * @param String userName con el nombre de usuario
	 * @param String password con la contrsenya de usuario
	 * @return booleano con el resultado
	 */
	public boolean conectaMongo(String userName, String password) {
		ArrayList<String> results = new ArrayList<String>();
		DatosConexion();
		// conectamos con mongo(en futuro, pasar como parametros ip y puerto)
		mongoClient = new MongoClient(getIp(), getPort());
		database = mongoClient.getDatabase(getBdName());
		collectionUsers = database.getCollection("users");
		if (validaUsuario(userName, password)) {
			userLogged = true;
			JOptionPane.showMessageDialog(new JFrame(), "LOGIN CORRECTO", "INFO", JOptionPane.INFORMATION_MESSAGE);
			collectionBooks = database.getCollection(getCollection());
			MongoCursor<Document> cursor;
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Credenciales erroneas", "Alerta", JOptionPane.WARNING_MESSAGE);
			mongoClient.close();
			return false;
		}
	}

	// comprueba que los datos introducidos corresponden con los almacenados en BD
	/**VALIDA USUARIO
	 * Recibe Strings con usuario y password, setea el nombre de usuario en la clase, encripta el password recibido por parametro
	 * para comprobarlo con el de la bd.
	 * Accede a la coleccion de usuarios y crea un objeto Usuario con cada uno de ellos y lo anyade a la Lista de usuarios.
	 * Comprueba que el usuario recibido por parametro y el password encriptado coinciden con algun usuario de la  lista.
	 * retorna booleano con el resultado
	 * @param String userName
	 * @param String password
	 * @return boolenao con el resultado
	 */
	public boolean validaUsuario(String userName, String password) {

		setUsuario(userName);
		String passEncrypt = encriptarContrasenya(password);
		Usuario user = null;

		cursor = collectionUsers.find().iterator();
		while (cursor.hasNext()) {
			JSONObject obj = new JSONObject(cursor.next().toJson());
			String user2 = obj.getString("user");
			String passw = obj.getString("pass");
			user = new Usuario(user2, passw);
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
	/**ENCRIPTAR CONTRASENYA
	 * Metodo que encripta la contrsenya recibida por parametro con el Hash SHA-256
	 * @param String con la contrsenya en claro
	 * @return String con la contrsenya encriptada
	 */
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

	/**DESCONECTAR MONGODB
	 * Metodo que cierra la conecion con MongoDB.
	 * Retorna un String con el resultado de la operacion
	 * @return String con mensaje al usuario informando del resultado
	 */
	public String desconectaMongo() {
		if (mongoClient != null) {
			String response = "CONEXION CERRADA";
			mongoClient.close();
			return response;
		}
		return "DEBE CONECTARSE PRIMERO";

	}

	/**MOSTRA BD
	 * Retorna un ArrayList de Libro con los elementos de la BD.
	 * Accede a la BD y consult todos los elementos de la coleccion, crea un objeto libro por cada uno
	 * de ellos y lo anyade a una Lista, la cual retorna.
	 * @return ArrayList con los libros de la coleccion.
	 */
	public ArrayList<Libro> mostrarBD() {
		ArrayList<Libro> biblioteca = new ArrayList<Libro>();
		Integer id2 = null, anyoNacimiento = null, anyoPublicacion = null, numPaginas = null;
		String titulo = null, autor = null, editorial = null, imagen = null;
		cursor = collectionBooks.find().iterator();
		// controlamos que el id exista
		if (!cursor.hasNext()) {
			System.err.println("El id no existe");
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
			Libro libro = new Libro(id2, titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas, imagen);
			biblioteca.add(libro);
		}

		return biblioteca;

	}

	/**ELEMENTOS EN BD
	 * Muestra la cantidad de elementos en la coleccion	
	 * @return String con el resultado
	 */
	public String ElementosBD() {
		Integer elementosBD = (int) collectionBooks.count();
		String elementos = elementosBD.toString();
		return ("Cantidad de elementos en coleccion: " + elementos);
	}

	// 
	/**
	 *Anyade un libro a la coleccion, recibe un objeto Libro, lo gestiona y anyade a labase de datos
	 *Retorna un String con la confirmacion
	 * @param libro
	 * @return String
	 */
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

	/**
	 * Recibe como parametro un Int con el id del libro a mostrar, lo busca en la base de datos y crea un objeto Libro con el resultado, el cual retorna.
	 * @param int id
	 * @return Libro libro
	 */
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
		}

		Libro libro = new Libro(id2, titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas, imagen);
		return libro;
	}

	/**EDITAR LIBRO
	 * Recibe un objeto Libro como parametro y devuelve un String con el resultado de la operacion.
	 * Primero solicita confirmacion del usuario, en caso afirmativo, crea la consulta y busca el libro en la BD.
	 * Compara los campos del libro de la BD con los campos del objeto Libro recibido por parametro y en caso de no ser iguales,
	 * modifica el parametro en la base de datos.
	 * @param libro
	 * @return String con el resultado
	 */
	public String EditarLibro(Libro libro) {
		int input = JOptionPane.showConfirmDialog(null, "¿Desear editar el libro?", "Atención",
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

	
	/**
	 * Recibe dos Strings con el campo de busqueda y el valor a buscar, devuelve un objeto Libro con el libro buscado
	 * @param String campo con el campo de busqueda
	 * @param String valor con el valor a buscar
	 * @return Libro con el libro buscado
	 */
	public Libro BuscarLibro(String campo, String valor) {

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
			JOptionPane.showMessageDialog(new JFrame(), response, "ERROR", JOptionPane.WARNING_MESSAGE);
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
			imagen = obj.getString("Thumbnail");

		}
		Libro libro = new Libro(id, titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas, imagen);

		return libro;
	}

	
	/**
	 * Recibe tres Strings con el campo de busqueda, el valor a buscar y el filtro de busqueda,
	 * siendo "eq" para coincidencia exacta, "gte" para busquedas mayor que el valor y "lte" para busquedas menor que el valor.  
	 * Devuelve ArrayList de Libro con una lista de libros coincidintes.
	 * @param String campo con el campo de busqueda
	 * @param String valor con el valor a buscar 
	 * @param String parametroBusqueda con el filtro de busqueda
	 * @return ArrttayList de Libro con los libros
	 */
	public ArrayList<Libro> BuscarLibroCriterio(String campo, String valor, String parametroBusqueda) {

		ArrayList<Libro> response = new ArrayList<Libro>();
		Integer id = null, anyoNacimiento = null, anyoPublicacion = null, numPaginas = null;
		String titulo = null, autor = null, editorial = null, imagen = null;
		Bson query = null;
		if (parametroBusqueda.equals("eq")) {
			if (campo == "Id" || campo == "Anyo_nacimiento" || campo == "Anyo_publicacion" || campo == "Num_paginas") {
				int valor2 = Integer.parseInt(valor);
				query = eq(campo, valor2);
			} else {
				query = eq(campo, valor);
			}
		}

		if (parametroBusqueda.equals("gte")) {
			if (campo == "Id" || campo == "Anyo_nacimiento" || campo == "Anyo_publicacion" || campo == "Num_paginas") {
				int valor2 = Integer.parseInt(valor);
				query = gte(campo, valor2);
			} else {
				query = gte(campo, valor);
			}
		}
		if (parametroBusqueda.equals("lte")) {
			if (campo == "Id" || campo == "Anyo_nacimiento" || campo == "Anyo_publicacion" || campo == "Num_paginas") {
				int valor2 = Integer.parseInt(valor);
				query = lte(campo, valor2);
			} else {
				query = lte(campo, valor);
			}
		}

		cursor = collectionBooks.find(query).iterator();
		
		if (!cursor.hasNext()) {			
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

	/**
	 * Recibe String con el campo de busqueda y el valor del campo, busca el libro y lo convierte a JSON
	 * seguidamente, borra el libro de la base de datos.
	 * Retorna un booleano con el resultado
	 * @param campo, String con el campo a buscar
	 * @param valor, String con el valor del campo
	 * @return boolean con resultado
	 */
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
		
		if (!cursor.hasNext()) {
			System.err.println("El id no existe");
			JOptionPane.showMessageDialog(new JFrame(), "EL ID NO EXISTE", "ERROR",
					JOptionPane.WARNING_MESSAGE);
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
