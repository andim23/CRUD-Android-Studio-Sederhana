package com.example.wrep.penjualanbarang;

public class AdminListItem {

    private String kode;
    private String judul;
    private String jumlah;
    private String harga;
    private String gambar;

    public AdminListItem(String gambar, String judul, String harga, String jumlah, String kode) {
        this.kode = kode;
        this.judul = judul;
        this.jumlah = jumlah;
        this.harga = harga;
        this.gambar = gambar;
    }

    public String getKode() {
        return kode;
    }

    public String getGambar() {
        return gambar;
    }

    public String getJudul() {
        return judul;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getHarga() {
        return harga;
    }
}