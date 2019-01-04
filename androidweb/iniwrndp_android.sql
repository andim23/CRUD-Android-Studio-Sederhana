-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Waktu pembuatan: 04 Jan 2019 pada 12.01
-- Versi server: 10.1.37-MariaDB-cll-lve
-- Versi PHP: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `iniwrndp_android`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE `barang` (
  `barang_id` int(11) NOT NULL,
  `kode_barang` int(30) NOT NULL,
  `nama_barang` varchar(100) DEFAULT NULL,
  `jumlah_barang` int(10) DEFAULT NULL,
  `harga_barang` int(20) DEFAULT NULL,
  `nama_file` varchar(100) DEFAULT NULL,
  `url_file` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`barang_id`, `kode_barang`, `nama_barang`, `jumlah_barang`, `harga_barang`, `nama_file`, `url_file`) VALUES
(29, 1, 'Hijab Bella', 100, 25000, 'b6596127b6db15244becdfae17edeabe', 'http://iniwahyu.com/android/image/b6596127b6db15244becdfae17edeabe.jpg'),
(30, 2, 'Hijab Sabyan', 10, 60000, '854cf9d114d7dd61c44278d5e18902c2', 'http://iniwahyu.com/android/image/854cf9d114d7dd61c44278d5e18902c2.jpg'),
(31, 3, 'Hijup Dian Pelangi', 310, 15000, 'aadf71152ee6105c326d7cb93b9ca97e', 'http://iniwahyu.com/android/image/aadf71152ee6105c326d7cb93b9ca97e.jpg'),
(32, 4, 'Hijab Mansur', 490, 45000, 'd91da6c885f522ef6fb82a3bf06e62f9', 'http://iniwahyu.com/android/image/d91da6c885f522ef6fb82a3bf06e62f9.jpg'),
(33, 5, 'Hijab Bella Almira', 100, 100000, '7ce34ac570ef5691db8242cbb5c88270', 'http://iniwahyu.com/android/image/7ce34ac570ef5691db8242cbb5c88270.jpg'),
(34, 6, 'Hijab Segiempat', 100, 75000, '4a959bf6dd7edf3125c8a7c64966472c', 'http://iniwahyu.com/android/image/4a959bf6dd7edf3125c8a7c64966472c.jpg'),
(35, 7, 'Hijab Dian', 500, 2000000, 'bb8aee2ab5ab235190ab977d5fea1be6', 'http://iniwahyu.com/android/image/bb8aee2ab5ab235190ab977d5fea1be6.jpg'),
(36, 8, 'Hijab Premium', 1000, 5000000, '98d99e69459270be6d54034983c33516', 'http://iniwahyu.com/android/image/98d99e69459270be6d54034983c33516.jpg');

-- --------------------------------------------------------

--
-- Struktur dari tabel `keranjang`
--

CREATE TABLE `keranjang` (
  `keranjang_id` int(11) NOT NULL,
  `kode_barang_keranjang` int(30) DEFAULT NULL,
  `nama_barang_keranjang` varchar(100) DEFAULT NULL,
  `harga_barang_keranjang` int(20) DEFAULT NULL,
  `nama_pembeli_keranjang` varchar(100) DEFAULT NULL,
  `jumlah_pesanan_keranjang` int(10) DEFAULT NULL,
  `alamat_pembeli_keranjang` text
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE `transaksi` (
  `transaksi_id` int(11) NOT NULL,
  `kode_barang_transaksi` int(30) DEFAULT NULL,
  `nama_barang_transaksi` varchar(100) DEFAULT NULL,
  `jumlah_pesanan_transaksi` int(10) DEFAULT NULL,
  `harga_barang_transaksi` int(20) DEFAULT NULL,
  `nama_pembeli_transaksi` varchar(100) DEFAULT NULL,
  `alamat_pembeli_transaksi` text,
  `total_harga_transaksi` int(30) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`transaksi_id`, `kode_barang_transaksi`, `nama_barang_transaksi`, `jumlah_pesanan_transaksi`, `harga_barang_transaksi`, `nama_pembeli_transaksi`, `alamat_pembeli_transaksi`, `total_harga_transaksi`) VALUES
(2, 1, 'Hijab Bella', 4, 25000, 'Bugi', 'Kendal', 100000);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`barang_id`),
  ADD UNIQUE KEY `kode_barang` (`kode_barang`);

--
-- Indeks untuk tabel `keranjang`
--
ALTER TABLE `keranjang`
  ADD PRIMARY KEY (`keranjang_id`);

--
-- Indeks untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`transaksi_id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `barang`
--
ALTER TABLE `barang`
  MODIFY `barang_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT untuk tabel `keranjang`
--
ALTER TABLE `keranjang`
  MODIFY `keranjang_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT untuk tabel `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `transaksi_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
