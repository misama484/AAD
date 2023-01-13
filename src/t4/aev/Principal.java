/*Se pide desarrollar con Java una aplicación con interfaz gráfica (ventana principal y emergentes)
y patrón Modelo-Vista-Controlador para gestionar una base de datos MongoDB (base de datos
biblioteca, con dos colecciones (libros y usuarios) proporcionada para la actividad).
El diseño de la interfaz, clases y métodos es libre.

Aunque puedes realizar pruebas en localhost, en la versión final de la actividad la base de datos
MongoDB debe estar alojada en una máquina virtual (AWS o Azure) con una IP pública.
Se pide que la aplicación tenga las siguientes funcionalidades:

• Cargar un fichero JSON con la información necesaria para realizar la conexión a la base
de datos: IP, puerto, base de datos y colección (tendrás que crear previamente el fichero
JSON).
• Para poder utilizar la aplicación, se han de controlar las credenciales. Pide al usuario su
login y contraseña. En la base de datos deberás tener una colección con los usuarios
autorizados. No se puede guardar la contraseña como texto plano, tendrás que utilizar
un hash SHA-256. No se pueden ejecutar funcionalidades si el usuario no está activo.
• Mostrar un resumen de la colección: número total de documentos y, para cada
documento, un campo (el que consideres más relevante).
• Implementa las funcionalidades CRUD a nivel de documento: crear un documento, leer
y mostrar un documento, actualizar un documento (C) y borrar un documento (C).
• Las imágenes están almacenadas como string en base 64. Cuando se crea un nuevo
documento se debe poder adjuntar la imagen de la misma forma (string base 64), como
DAM | Acceso a datos
un campo más del JSON de la colección. Por tanto, la aplicación debe solicitar la ruta a
la imagen del nuevo documento creado.
• Permitir crear y aplicar cualquier consulta, como mínimo las de tipo igual (eq), mayor o
igual (qte) y menor o igual (lte).

Las operaciones indicadas con (C) deben solicitar una confirmación al usuario (por ejemplo,
mediante una ventana emergente de tipo popup).*/


package t4.aev;



public class Principal {

	public static void main(String[] args) {
		Modelo modelo = new Modelo();
		Vista vista = new Vista();
		Anyadir anyadir = new Anyadir();
		Editar editar = new Editar();
		Controlador controlador = new Controlador(modelo, vista, anyadir, editar);


	}

}
