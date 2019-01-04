<?php
require_once 'koneksi.php';
 
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
 
if($_SERVER['REQUEST_METHOD'] == 'POST')
{
    $kode = $_POST['kode_barang'];
    $nama = $_POST['nama_barang'];
    $harga = $_POST['harga_barang'];
    $namapembeli = $_POST['nama_pembeli'];
    $jumlahpesanan = $_POST['jumlah_pesanan'];
    $alamat = $_POST['alamat_pembeli'];
     
    $InsertSQL = "INSERT INTO keranjang (kode_barang_keranjang, nama_barang_keranjang, harga_barang_keranjang, nama_pembeli_keranjang, jumlah_pesanan_keranjang, alamat_pembeli_keranjang) values('$kode', '$nama', '$harga', '$namapembeli', '$jumlahpesanan', '$alamat')";
     
    if(mysqli_query($conn, $InsertSQL)){
        echo "Berhasil Menambahkan ".$nama. " ke Dalam Keranjang";
    }
    else{
        echo "Gagal Menambahkan ke Dalam Keranjang";
    }
    mysqli_close($conn);
}
else
{
    echo "Gagal";
}
 
?>