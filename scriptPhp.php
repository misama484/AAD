<?php

if(isset($_POST["valor"])){
    $idApi = $_POST["idApi"];
    $nombre = $_POST["nombre"];
    $estado = $_POST["estado"];
    $especie = $_POST["especie"];
    $genero = $_POST["genero"];
    $localizacion = $_POST["localizacion"];
    $origen = $_POST["origen"];
    $urlImagen = $_POST["URLImagen"];
    $servidor = "127.0.0.1";
    $usuario = "root";
    $password = "";
    $dbname = "rickmorty";

    $conexion = mysqli_connect($servidor, $usuario, $password, $dbname);

    if (!$conexion) {
        echo "Error en la conexion a MySQL: ".mysqli_connect_error();
    exit();
}

$sql = "INSERT INTO personajes (idApi,nombre,estado,especie,genero,localizacion,origen,URLImagen) VALUES ('".$idApi."','".$nombre."','".$estado."','".$especie."','".$genero."','".$localizacion."','".$origen."','".$urlImagen."')";

if (mysqli_query($conexion, $sql)) {
    echo "Registro insertado correctamente.";
} else {
    echo "Error: " . $sql . "<br>" . mysqli_error($conexion);
}

?>