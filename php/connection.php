<?php

$server = "localhost";
$databasename="rezaeia3_news";
$username="rezaeia3_news";
$pass="2623286Gray";

$qc="mysql:host=$server;dbname=$databasename";
try{
    $connect = new PDO($qc,$username,$pass);
    $connect->exec("SET character_set_connection='utf8'");
    $connect->exec("SET NAMES 'UTF8'");
    //echo "success";

}
catch(PDOException $error){
    echo "unable to connect ".$error->getMessage();

}

?>