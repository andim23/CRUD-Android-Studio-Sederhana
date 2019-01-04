<?php

require_once 'koneksi.php';
 
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

if($_SERVER['REQUEST_METHOD']=='POST'){
	
	$kode  = $_POST['kode_barang'];
    $nama = $_POST['nama_barang'];
    $jumlah = $_POST['jumlah_barang'];
    $harga = $_POST['harga_barang'];

	//Membuat SQL Query
	$sql = "UPDATE barang SET nama_barang='$nama', jumlah_barang = '$jumlah', harga_barang = '$harga' WHERE kode_barang = '$kode';";

	//Meng-update Database
	if(mysqli_query($conn,$sql)){
		echo 'Berhasil Mengubah Data '.$nama;
	}else{
		echo 'Gagal Mengubah Data';
	}

	mysqli_close($conn);
}

?>
