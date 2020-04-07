package com.example.first;

public class Paket_list {
    private int id;
    private String nama_paket;

    public Paket_list(int id, String nama_paket) {
        this.id = id;
        this.nama_paket = nama_paket;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_paket() {
        return nama_paket;
    }

    public void setNama_paket(String nama_paket) {
        this.nama_paket = nama_paket;
    }
}
