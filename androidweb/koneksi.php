<?php
// define('HOST','localhost');
// define('USER','iniwrndp');
// define('PASS','QL6yVxglnBFl');
// define('DB','iniwrndp_android');

//Define your host here.
$servername = "localhost";
//Define your database username here.
$username = "iniwrndp";
//Define your database password here.
$password = "QL6yVxglnBFl";
//Define your database name here.
$dbname = "iniwrndp_android";
 
// $conn = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
// $conn = new mysqli($servername, $username, $password, $dbname);

if(mysqli_connect_errno()){
        die("Database connnection failed " . "(" .
            mysqli_connect_error() . " - " . mysqli_connect_errno() . ")"
                );
    }
?>