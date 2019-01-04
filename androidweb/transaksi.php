<?php

require_once 'koneksi.php';
date_default_timezone_set('Asia/Jakarta');
// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

if($_SERVER['REQUEST_METHOD']=='POST'){
    
    $kode_barang = $_POST['kode_barang'];
    $nama_barang = $_POST['nama_barang'];
    $pesanan_transaksi = $_POST['jumlah_pesanan'];
    $harga_barang = $_POST['harga_barang'];
    $nama_pembeli = $_POST['nama_pembeli'];
    $alamat_pembeli = $_POST['alamat_pembeli'];
    $total_harga = $pesanan_transaksi*$harga_barang;
    
    $sqltambah = "INSERT INTO transaksi (kode_barang_transaksi, nama_barang_transaksi, jumlah_pesanan_transaksi, harga_barang_transaksi, nama_pembeli_transaksi, alamat_pembeli_transaksi, total_harga_transaksi) VALUES ('$kode_barang', '$nama_barang','$pesanan_transaksi', '$harga_barang', '$nama_pembeli', '$alamat_pembeli', '$total_harga')";
    
    if(mysqli_query($conn, $sqltambah)){
        echo "Berhasil Melakukan Transaksi";
        $sqlupdate = "UPDATE barang SET jumlah_barang = jumlah_barang - $pesanan_transaksi WHERE kode_barang = '$kode_barang'";
        if(mysqli_query($conn, $sqlupdate)){
            //return true;
            $deletesql = mysqli_query($conn, "DELETE FROM keranjang");
        }else{
            return false;
        }
    }else{
        echo "Gagal Melakukan Transaksi";
        try{
         // Your code
    } 
    catch(Throwable $e) {
        $trace = $e->getTrace();
        echo $e->getMessage().' in '.$e->getFile().' on line '.$e->getLine().' called from '.$trace[0]['file'].' on line '.$trace[0]['line'];
    }
    }
	mysqli_close($conn);
}else{
    echo "Tidak Ada Request";
}

?>