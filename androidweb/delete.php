<?php

require_once 'koneksi.php';
 
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

if($_SERVER['REQUEST_METHOD']=='POST'){

    $kb = $_POST['kode_barang'];
    
    $sql = "DELETE FROM barang WHERE kode_barang = '$kb'";
    
    //Menghapus Nilai pada Database
    if(mysqli_query($conn,$sql)){
        echo 'Berhasil Menghapus Barang';
    }else{
        echo 'Gagal Menghapus Barang';
    }
    
    mysqli_close($conn);
}
else{
    echo "Gagal Menghapus";
}
?>
