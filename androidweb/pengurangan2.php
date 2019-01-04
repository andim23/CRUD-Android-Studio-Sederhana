<?php
require_once 'koneksi.php';
 
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

$ambil = mysqli_query($conn, "SELECT * FROM keranjang WHERE kode_barang_keranjang = 3");
$hasil = mysqli_fetch_array($ambil);
    $harga = $hasil['harga_barang_keranjang'];
    $pesanan = $hasil['jumlah_pesanan_keranjang'];
    $jumlah = $harga*$pesanan;

// $update = "UPDATE barang SET jumlah_barang = jumlah_barang - $aa WHERE kode_barang = 3";
// if(mysqli_query($conn, $update)){
//     echo "Berhasil";
// }else{
//     echo "Gagal";
// }
print_r($jumlah);

?>