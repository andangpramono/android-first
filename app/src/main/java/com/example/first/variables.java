package com.example.first;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class variables extends Application {
    public int rowId = 0;
    public String email  = "";
    public Boolean error  = true;
    public String message  = "";
    public String id_brand;

    public ArrayList<Paket_list> paketList = new ArrayList<Paket_list>();
    public String url_penawaran;

}