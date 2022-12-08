package t3.hibernate.ej8;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import t3.hibernate.Libro;

public class Principal {
	
	static Session session;

	public static void main(String[] args) {
		// creamos sesion
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		configuration.addClass(Libro.class);
		ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration.buildSessionFactory(registry);

		// Abrimos sesion
		session = sessionFactory.openSession();
		session.beginTransaction();		
		
		Scanner teclado = new Scanner(System.in);
		while(true) {
		//Mostramos menu de aplicacion
		consola("\nBienvenido a BiblioGest 2.0\n Elija una de las opciones: \n 1. Mostrar toda la biblioteca\n "
				+ "2. Mostrar un libro\n 3. Anyadir nuevo libro\n 4. Editar un libro\n 5. Borrar un libro\n 6. Salir");
		
		int opcion = teclado.nextInt();
		
			switch (opcion){
				case 1:
					listaLibros();
					break;
				
				case 2: 
					consola("Introduzca id del libro: ");
					int id = teclado.nextInt();
					mostrarDetalle(id);
					break;
					
				case 3:
					creaLibro();
					break;
				
				case 4:
					consola("Introduzca id del libro: ");
					int idEditar = teclado.nextInt();
					editaLibro(idEditar);
					break;
					
				case 5:
					consola("Introduzca id del libro: ");
					int idBorrar = teclado.nextInt();
					eliminaLibro(idBorrar);
					break;
				
				case 6:
					session.close();
					System.exit(0);
					
				default:
					consola("Opcion no valida");
					break;
				
			}
			
			//session.getTransaction().commit();
			//session.clear();			
		}
		
		
		
	}
	
	
	public static void listaLibros() {
		//  mostrar todos los libros de la biblioteca, solo id y titulo
			List listaLibros = new ArrayList();
			listaLibros = session.createQuery("FROM Libro").list();
			for (Object obj : listaLibros) {
				Libro book = (Libro) obj;
				System.out.println("Id: " + book.getId() + " Titulo: " + book.getTitulo());
			}
			
			
		}
	
	public static void mostrarDetalle(int id) {
		
		Libro libro = (Libro) session.load(Libro.class, id);
		consola(libro.toString());
		
		
	}
	
	public static void creaLibro() {
		Scanner teclado = new Scanner(System.in);
		consola("Introduzca el titulo del libro: ");
		String titulo = teclado.nextLine();
		consola("Introduzca autor: ");
		String autor = teclado.nextLine();
		consola("Introduzca anyo de nacimiento del autor: ");
		String anyoNacimiento = teclado.nextLine();
		consola("Intrduzca anyo de publicacion: ");
		String anyoPublicacion = teclado.nextLine();
		consola("Introduzca editoral: ");
		String editorial = teclado.nextLine();
		consola("Introduzca numero de paginas");
		Integer numPaginas = Integer.parseInt(teclado.nextLine());
		
		Libro libro = new Libro(titulo, autor, anyoNacimiento, anyoPublicacion, editorial, numPaginas);
		Serializable id = session.save(libro);
		consola("Creado libro: " + libro.toString() + " con id: " + id);
		
		session.getTransaction().commit();
		session.clear();
	}
	
	public static void editaLibro(int id) {
		Libro libro = (Libro) session.load(Libro.class, id);
		consola(libro.toString());
		Scanner teclado = new Scanner(System.in);
		consola("Que desea editar? ");
		String opcion = teclado.nextLine();
		switch(opcion.toLowerCase()) {
			case "titulo":
				consola("Introduzca nuevo titulo: ");
				String tituloNuevo = teclado.nextLine();
				libro.setTitulo(tituloNuevo);
				break;
				
			case "autor":
				consola("Introduzca nuevo autor: ");
				String autorNuevo = teclado.nextLine();
				libro.setAutor(autorNuevo);
				break;
			
			case "anyonacimiento":
				consola("Introduzca nuevo anyo de nacmiento: ");
				String anyoNacimientoNuevo = teclado.nextLine();
				libro.setAnyoNacimiento(anyoNacimientoNuevo);
				break;
				
			case "anyopublicacion":
				consola("Introduzca nuevo anyo de publicacion: ");
				String anyoPublicacionNuevo = teclado.nextLine();
				libro.setAnyoPublicacion(anyoPublicacionNuevo);
				break;
				
			case "editorial":
				consola("Introduzca nueva editorial: ");
				String editorialNuevo = teclado.nextLine();
				libro.setEditorial(editorialNuevo);
				break;
				
			case "numeropaginas":
				consola("Introduzca nuevo numero de paginas: ");
				String numeroPaginasNuevo = teclado.nextLine();
				libro.setNumeroPaginas(Integer.parseInt(numeroPaginasNuevo));
				break;
				
			default:
				consola("Introduzca una opcion valida");
		}
		
		session.update(libro);
		session.getTransaction().commit();
		session.clear();
		
	}
	
	public static void eliminaLibro(int id) {
		try {
			Libro libro = new Libro();
			libro.setId(id);
			session.delete(libro);
			System.out.println("Borrado Libro " + id);
		}catch(Exception e) {
			System.err.println("No existe el id");
		}
		
		session.getTransaction().commit();
		session.clear();
	}
	
	public static void consola(String mensaje) {
		System.out.println(mensaje);
	}


	
	

}
