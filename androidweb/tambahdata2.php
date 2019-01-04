<?php
require_once 'koneksi.php';
 
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
 
if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $DefaultId = 0;
    
    $kode  = $_POST['kode_barang'];
    $nama = $_POST['nama_barang'];
    $jumlah = $_POST['jumlah_barang'];
    $harga = $_POST['harga_barang'];
    $ImageData = $_POST['image_data'];
    //  $ImageName = $_POST['image_tag'];
    $date = date('Y-m-d h:i:s');
    $hash = md5($date);
    $ImageName = $hash;
    $ImagePath = "image/$ImageName.jpg";
     
    $ServerURL = "http://iniwahyu.com/android/$ImagePath";
     
    $InsertSQL = "INSERT INTO barang (kode_barang, nama_barang, jumlah_barang, harga_barang, url_file, nama_file) values('$kode', '$nama', '$jumlah', '$harga', '$ServerURL', '$ImageName')";
     
    if(mysqli_query($conn, $InsertSQL)){
        file_put_contents($ImagePath,base64_decode($ImageData));
        echo "Berhasil Menambahkan Data ".$nama;
    }
    else{
        echo "Gagal Menambahkan Data";
    }
    mysqli_close($conn);
}
else
{
    echo "Gagal";
}
 
?>