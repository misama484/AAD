package t3.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Principal {

	public static void main(String[] args) {
		// EJERCICIOS 1 - 2 - 3 - 4
		// creamos sesion
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		configuration.addClass(Libro.class);
		ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
				.build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(registry);

		// Abrimos sesion
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		// recuperar lista de libros
		List listaLibros = new ArrayList();
		listaLibros = session.createQuery("FROM Libro").list();
		for (Object obj : listaLibros) {
			Libro book = (Libro) obj;
			System.out.println(book.toString());
		}

		// EJERCICIO 5
		// creamos un nuevo objeto
		/*
		 * Libro book1 = new Libro("Mortadelo y Filemon Agencia de informacion",
		 * "Ibanyez", "1936", "1958", "Bruguera", 20 ); 
		 * //session.persist(book); //no funciona 
		 * Serializable id = session.save(book1);
		 * System.out.println("Creado libro con id: " + id);
		 */
		 

		// EJERCCIO 6
		// Actualizamos un objeto
			//recuperamos libro a partir de la id
		/*
		 * Libro bookEditar = (Libro) session.load(Libro.class, 15);
		 * System.out.println(bookEditar.toString()); //editamos los campos
		 * bookEditar.setNumeroPaginas(50); 
		 * session.update(bookEditar); //mandamos la modificacion a la bd
		 * System.out.println("Editado libro\n" + bookEditar.toString());
		 */
		
		//EJERCICIO 7 
		//Borramos un objeto de la bd
		try {
			Libro bookBorrar = new Libro();
			bookBorrar.setId(15);
			session.delete(bookBorrar);
			System.out.println("Borrado Libro 15");
		}catch(Exception e) {
			System.err.println("No existe el id");
		}
		
		
		// realizamos los commits a la bd y cerramos sesion
		session.getTransaction().commit();
		session.clear();
		session.close();
		System.out.println("Cerrada");

	}

}
