package t3.hibernate;

public class Libro {
	String titulo, autor, anyoNacimiento, anyoPublicacion, editorial;
	int id, numeroPaginas;
	
	
	//contructores
	//vacio
	
	public Libro() {
		
	}


	public Libro(int id,String titulo, String autor, String anyoNacimiento, String anyoPublicacion, String editorial, int numeroPaginas) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.anyoNacimiento = anyoNacimiento;
		this.anyoPublicacion = anyoPublicacion;
		this.editorial = editorial;
		this.id = id;
		this.numeroPaginas = numeroPaginas;
	}


	public Libro(String titulo, String autor, String anyoNacimiento, String anyoPublicacion, String editorial,
			int numeroPaginas) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.anyoNacimiento = anyoNacimiento;
		this.anyoPublicacion = anyoPublicacion;
		this.editorial = editorial;
		this.numeroPaginas = numeroPaginas;
	}

	//Getters&Setters
	
	public String getTitulo() {
		return titulo;
	}


	public String getAutor() {
		return autor;
	}


	public String getAnyoNacimiento() {
		return anyoNacimiento;
	}


	public String getAnyoPublicacion() {
		return anyoPublicacion;
	}


	public String getEditorial() {
		return editorial;
	}


	public int getId() {
		return id;
	}


	public int getNumeroPaginas() {
		return numeroPaginas;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}


	public void setAnyoNacimiento(String anyoNacimiento) {
		this.anyoNacimiento = anyoNacimiento;
	}


	public void setAnyoPublicacion(String anyoPublicacion) {
		this.anyoPublicacion = anyoPublicacion;
	}


	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}


	public void setId(int id) {
		this.id = id;
	}


	public void setNumeroPaginas(int numeroPaginas) {
		this.numeroPaginas = numeroPaginas;
	}


	@Override
	public String toString() {
		return "Libro [titulo=" + titulo + ", autor=" + autor + ", anyoNacimiento=" + anyoNacimiento
				+ ", anyoPublicacion=" + anyoPublicacion + ", editorial=" + editorial + ", id=" + id
				+ ", numeroPaginas=" + numeroPaginas + "]";
	}
		
	
}
