/*Se pide desarrollar con Java una aplicaci�n con interfaz gr�fica (ventana principal y emergentes)
y patr�n Modelo-Vista-Controlador para gestionar una base de datos MongoDB (base de datos
biblioteca, con dos colecciones (libros y usuarios) proporcionada para la actividad).
El dise�o de la interfaz, clases y m�todos es libre.

Aunque puedes realizar pruebas en localhost, en la versi�n final de la actividad la base de datos
MongoDB debe estar alojada en una m�quina virtual (AWS o Azure) con una IP p�blica.
Se pide que la aplicaci�n tenga las siguientes funcionalidades:

� Cargar un fichero JSON con la informaci�n necesaria para realizar la conexi�n a la base
de datos: IP, puerto, base de datos y colecci�n (tendr�s que crear previamente el fichero
JSON).
� Para poder utilizar la aplicaci�n, se han de controlar las credenciales. Pide al usuario su
login y contrase�a. En la base de datos deber�s tener una colecci�n con los usuarios
autorizados. No se puede guardar la contrase�a como texto plano, tendr�s que utilizar
un hash SHA-256. No se pueden ejecutar funcionalidades si el usuario no est� activo.
� Mostrar un resumen de la colecci�n: n�mero total de documentos y, para cada
documento, un campo (el que consideres m�s relevante).
� Implementa las funcionalidades CRUD a nivel de documento: crear un documento, leer
y mostrar un documento, actualizar un documento (C) y borrar un documento (C).
� Las im�genes est�n almacenadas como string en base 64. Cuando se crea un nuevo
documento se debe poder adjuntar la imagen de la misma forma (string base 64), como
DAM | Acceso a datos
un campo m�s del JSON de la colecci�n. Por tanto, la aplicaci�n debe solicitar la ruta a
la imagen del nuevo documento creado.
� Permitir crear y aplicar cualquier consulta, como m�nimo las de tipo igual (eq), mayor o
igual (qte) y menor o igual (lte).

Las operaciones indicadas con (C) deben solicitar una confirmaci�n al usuario (por ejemplo,
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
