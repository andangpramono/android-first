package com.example.first;

public class Brands {
    private String nama_brand;
    private String kategori;
    private String nama_file;

    public Brands (String nama_brand1, String kategori1, String nama_file1) {
        this.nama_brand = nama_brand1;
        this.kategori = kategori1;
        this.nama_file = nama_file1;

    }

    public String getNama_brand() {
        return nama_brand;
    }

    public void setNama_brand(String nama_brand) {
        this.nama_brand = nama_brand;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getNama_file() {
        return nama_file;
    }

    public void setNama_file(String nama_file) {
        this.nama_file = nama_file;
    }
}
