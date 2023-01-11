package t4.aev;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.bson.Document;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class Modelo {
	File fichero = new File("DatosConexion.json");

	private String usuario;
	private String password;
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

	// CARGAR FICHERO JSON
	public String cargaJson(File ficheroJSON) {
		return null;

	}

	// CONECTAR CON DB
	// metodo que conecta con mongo y muestra listado de bases de datos en
	// "servidor"
	// devuelve una lista con las bd
	// en un futuro, pasar ip y puerto por parametros, a partir de doc.json para
	// conexion a server AWS
	public ArrayList<String> conectaMongo() {
		ArrayList<String> results = new ArrayList<String>();
		// conectamos con mongo(en futuro, pasar como parametros ip y puerto)
		mongoClient = new MongoClient("localhost", 27017);
		database = mongoClient.getDatabase("BibliotecaAEV3");
		collectionUsers = database.getCollection("users");
		if (validaUsuario()) {
			collectionBooks = database.getCollection("books");
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
	
	//anyade un libro a la coleccion, le pasamos un objeto libro y lo anyade.
	public String AnyadeLibro(Libro libro) {
		String response = null;
		
		Document doc = new Document();
		doc.append("Id", libro.getId());
		doc.append("Iitutlo", libro.getTitulo());
		doc.append("Autor", libro.getAutor());
		doc.append("Anyo_nacimiento", libro.getAnyNacimiento());
		doc.append("Anyo_publicacion", libro.getAnyoPublicacion());
		doc.append("Editorial", libro.getEditorial());
		doc.append("Paginas", libro.getNumPaginas());
		doc.append("Imagen", libro.getImagen());

		collectionBooks.insertOne(doc);
		response = "Anyadido libro: " + libro.getTitulo() + " a la base de datos";
		return response;
	}
}

