package com.example.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Brand extends AppCompatActivity {
    TextView judul;
    ImageView thumb;

    TextView textnama;
    String json_nama;

    TextView kategori;
    String json_kategori;
    String json_sub_katgegori;
    String nama_paket;

    WebView webView;

    RecyclerView recyclerView;
    MahasiswaAdapter adapter;
    ArrayList<Pakets> mahasiswaArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);


        //CardView card =(CardView)  findViewById(R.id.card_view1);
        //card.setCardBackgroundColor(Color.parseColor("#ffffff"));
        //card.setMaxCardElevation((float) 0.0);
        //card.setRadius((float) 5.0);


        //cek_login = (TextView) findViewById(R.id.ceklogin);
        //text_cek=((variables) getApplicationContext()).message;
        //cek_login.setText(text_cek);

        String id_brandnow = ((variables) getApplicationContext()).id_brand;
        getJSON("https://bukawaralaba.com/api_android/brand/"+id_brandnow);
        //addData();
        Button gotopenawaran = findViewById(R.id.gotopenawaran);
        gotopenawaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Brand.this, Penawaran.class));
            }
        });


    }

    public void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    //JSONArray jsonArray = new JSONArray(s);
                    JSONObject obj = new JSONObject(s);
                    //((variables) getApplicationContext()).message=obj.getString("message");
                    judul = (TextView) findViewById(R.id.txt_title);
                    judul.setText(obj.getString("nama_brand"));

                    thumb=(ImageView) findViewById(R.id.imageView);
                    String img_url="http://bukawaralaba.com/"+obj.getString("nama_file");
                    Picasso.get()
                            .load(img_url)
                            .into(thumb);

                    //URL url = new URL("http://image10.bizrate-images.com/resize?sq=60&uid=2216744464");
                    //Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    //thumb.setImageBitmap(bmp);


                    textnama = (TextView) findViewById(R.id.textnama);
                    json_nama=obj.getString("nama_brand");
                    textnama.setText(json_nama);

                    kategori = (TextView) findViewById(R.id.textkategori);
                    json_kategori=obj.getString("kategori");
                    json_sub_katgegori=obj.getString("sub_kategori");
                    kategori.setText(json_kategori+", "+json_sub_katgegori);

                    TextView textinvestasi = (TextView) findViewById(R.id.textinvestasi);
                    String json_investasi = obj.getString("franchise_fee");
                    textinvestasi.setText(json_investasi);

                    TextView textcp = (TextView) findViewById(R.id.textcp);
                    String json_cp = obj.getString("contact_person");
                    textcp.setText(json_cp);

                    TextView textalamat = (TextView) findViewById(R.id.textalamat);
                    String json_alamat = obj.getString("alamat");
                    textalamat.setText(json_alamat);

                    webView = (WebView) findViewById(R.id.proposal);
                    // displaying content in WebView from html file that stored in assets folder
                    webView.getSettings().setJavaScriptEnabled(true);

                    String proposal=obj.getString("deskripsi");
                    webView.loadDataWithBaseURL(null, proposal, "text/html", "utf-8", null);

                    String paketstr = obj.getString("paket");
                    JSONArray paket = new JSONArray(paketstr);


                    mahasiswaArrayList = new ArrayList<>();
                    ArrayList<Paket_list> paketList = new ArrayList<Paket_list>();

                    for (int i = 0; i < paket.length(); i++) {
                        JSONObject mJsonObjectProperty = paket.getJSONObject(i);

                        mahasiswaArrayList.add(new Pakets(mJsonObjectProperty.getString("nama_paket"), mJsonObjectProperty.getString("nama_gambar"), "987648765"));
                        paketList.add(new Paket_list(mJsonObjectProperty.getInt("id"), mJsonObjectProperty.getString("nama_paket")));
                    }

                    ((variables) getApplicationContext()).paketList=paketList;
                    ((variables) getApplicationContext()).url_penawaran="https://bukawaralaba.com/api_android/penawaran/"+obj.getString("id");

                    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

                    adapter = new MahasiswaAdapter(mahasiswaArrayList);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Brand.this, LinearLayoutManager.HORIZONTAL, false);

                    recyclerView.setLayoutManager(layoutManager);

                    recyclerView.setAdapter(adapter);



                    

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }



            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
