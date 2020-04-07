package com.example.first;

public class Pakets {
    private String nama_brand;
    private String npm;
    private String nohp;
    private String img_paket;

    public Pakets(String nama, String img_paket, String nohp) {
        this.nama_brand = nama;
        this.img_paket = img_paket;
        this.nohp = nohp;

    }

    public String getNama_brand() {
        return nama_brand;
    }

    public void setNama_brand(String nama_brand) {
        this.nama_brand = nama_brand;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getImg_paket() {
        return img_paket;
    }

    public void setImg_paket(String img_paket) {
        this.img_paket = img_paket;
    }
}
