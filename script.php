<?php
    if(isset($_POST["titulo"])){
        //datos Post    
        $titulo = $_POST["titulo"];
        $fecha = $_POST["fecha"];
        $miniatura = $_POST["miniatura"];
        $imagen = $_POST["imagen"];
        
        //datos conexion
        $servidor = "localhost";
        $usuario = "root";
        $password = "";
        $basedatos = "datosnasa";

        //conexion
        $conexion = mysqli_connect($servidor, $usuario, $password, $basedatos);

        //comprobamos conexion 
        if(!$conexion){
            echo "Conexion fallida: " . mysqli_connect_error();
            exit();
        }

        //consulta
        $consulta = "INSERT INTO apod (titulo, fecha, URLminiatura, URLimagen) VALUES ('$titulo', '$fecha', '$miniatura', '$imagen')";

        //ejecutamos consulta
        if (mysqli_query($conexion, $consulta)){
            echo "Datos insertados correctamente";
        }else {
            echo "Error: " . $consulta . "<br>" . mysqli_error($conexion);
        }


    }
?>