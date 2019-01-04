<?php
require_once "koneksi.php";

$conn = new mysqli($servername, $username, $password, $dbname);

$query = "SELECT * FROM barang ORDER BY barang_id DESC";
$data = mysqli_query($conn, $query);

$result = array();

while($row = mysqli_fetch_array($data))
{
    array_push($result, array(
        'kode_barang'   => $row['kode_barang'],
        'nama_barang'   => $row['nama_barang'],
        'jumlah_barang' => $row['jumlah_barang'],
        'harga_barang'  => $row['harga_barang'],
        'nama_file'     => $row['nama_file'],
        'url_file'      => $row['url_file']
    ));
}

if($result == NULL)
{
    echo json_encode(array('result' => 'Error', 'Message' => 'Data Kosong'));
}
else
{
    echo json_encode(array('result' => $result), TRUE);
}

?>