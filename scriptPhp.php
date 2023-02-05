
<?php

if(isset($_POST["personaje"])){
    //Datos Post
    $idApi = $_POST["idApi"];
    $nombre = $_POST["nombre"];
    $estado = $_POST["estado"];
    $especie = $_POST["especie"];
    $genero = $_POST["genero"];
    $localizacion = $_POST["localizacion"];
    $origen = $_POST["origen"];
    $urlImagen = $_POST["URLImagen"];

    //datosBD
    $servidor = "localhost";
    $usuario = "root";
    $password = "";
    $dbname = "rickmorty";

    $conexion = mysqli_connect($servidor, $usuario, $password, $dbname);

    if (!$conexion) {
        echo "Error en la conexion a MySQL: ".mysqli_connect_error();
    exit();
    }


    $sql = "INSERT INTO personajes (idApi, nombre, estado, especie, genero, localizacion, origen, URLImagen) VALUES ('".addslashes($idApi)."','".addslashes($nombre)."','".addslashes($estado)."','".addslashes($especie)."','".addslashes($genero)."','".addslashes($localizacion)."','".addslashes($origen)."','".addslashes($urlImagen)."')";

    if (mysqli_query($conexion, $sql)) {
        echo "Registro insertado correctamente.";
    } else {
        echo "Error: " . mysqli_error($conexion);
    }
}
/*
$servername = "localhost";
$username = "root";
$password = "";
$dbname = "rickmorty";

// Crea la conexión a la base de datos
$conn = mysqli_connect($servername, $username, $password, $dbname);

// Verifica la conexión
if (!$conn) {
    die("Conexión fallida: " . mysqli_connect_error());
}

// Recoge los parámetros enviados como POST
$idApi = $_POST['idApi'];
$nombre = $_POST['nombre'];
$estado = $_POST['estado'];
$especie = $_POST['especie'];
$genero = $_POST['genero'];
$origen = $_POST['origen'];
$localizacion = $_POST['localizacion'];
$imagen = $_POST['imagen'];

// Prepara la consulta SQL
$sql = "INSERT INTO personajes (idApi, nombre, estado, especie, genero, origen, localizacion, imagen)
VALUES ('$idApi', '$nombre', '$estado', '$especie', '$genero', '$origen', '$localizacion', '$imagen')";

// Ejecuta la consulta
if (mysqli_query($conn, $sql)) {
    echo "Datos guardados exitosamente";
} else {
    echo "Error al guardar los datos: " . mysqli_error($conn);
}

// Cierra la conexión a la base de datos
mysqli_close($conn);

 $sql = "INSERT INTO personajes (idApi, nombre, estado, especie, genero, localizacion, origen, URLImagen) VALUES ('".addslashes($idApi)."','".addslashes($nombre)."','".addslashes($estado)."','".addslashes($especie)."','".addslashes($genero)."','".addslashes($localizacion)."','".addslashes($origen)."','".addslashes($urlImagen)."')";


*/
?>