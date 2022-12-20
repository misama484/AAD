package t4.ejercicios;

public class Libro {
	String titulo, autor, editorial;
	int id, anyNacimiento, anyoPublicacion, numPaginas;
	
	//CONSTRUCTORES
	public Libro(int id, String titulo, String autor, int anyNacimiento, int anyoPublicacion, String editorial,
			int numPaginas) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.autor = autor;		
		this.anyNacimiento = anyNacimiento;
		this.anyoPublicacion = anyoPublicacion;
		this.editorial = editorial;
		this.numPaginas = numPaginas;
	}
	
	
	//GEETERS & SETTERS
	
	public int getId() {
		return id;
	}
	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public String getEditorial() {
		return editorial;
	}

	public int getAnyNacimiento() {
		return anyNacimiento;
	}

	public int getAnyoPublicacion() {
		return anyoPublicacion;
	}

	public int getNumPaginas() {
		return numPaginas;
	}
	
	public void getId(int id) {
		this.id = id;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public void setAnyNacimiento(int anyNacimiento) {
		this.anyNacimiento = anyNacimiento;
	}

	public void setAnyoPublicacion(int anyoPublicacion) {
		this.anyoPublicacion = anyoPublicacion;
	}

	public void setNumPaginas(int numPaginas) {
		this.numPaginas = numPaginas;
	}

	//ToString

	@Override
	public String toString() {
		return "Libro " + id + "[titulo=" + titulo + ", autor=" + autor + ", editorial=" + editorial + ", anyNacimiento="
				+ anyNacimiento + ", anyoPublicacion=" + anyoPublicacion + ", numPaginas=" + numPaginas + "]";
	}
	
	

	
	
	
	
}
