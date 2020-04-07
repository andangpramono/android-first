package com.example.first;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Brands_list extends AppCompatActivity {

    ArrayList<Brands> brandsArrayList;
    List<String> indexBrandlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brands_list);
        final SharedPreferences sharedpreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String nama_user = sharedpreferences.getString("nama_user", "Nama User");
        String email = sharedpreferences.getString("email", "Email User");
        String url_pp = sharedpreferences.getString("file_foto", "http://bukawaralaba.com/gambar/default_photo.jpg");

        ImageView img_profil = (ImageView) findViewById(R.id.img_profil);
        TextView textnamauser = (TextView) findViewById(R.id.textnamauser);
        TextView textemailuser = (TextView) findViewById(R.id.textemailuser);

        Picasso.get()
                .load (url_pp)
                .into(img_profil);
        textnamauser.setText(nama_user);
        textemailuser.setText(email);


        Button btn_logout = (Button) findViewById(R.id.btnlogout);
        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();

                startActivity(new Intent(Brands_list.this, MainActivity.class));
                finish();
            }
        });

        getJSON("https://bukawaralaba.com/api_android/brands");

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position, final String test) {
                //Values are passing to activity & to fragment as well
               // Toast.makeText(Brands_list.this, "Single Click on position        :"+indexBrandlist.get(position),
                //        Toast.LENGTH_SHORT).show();
                ((variables) getApplicationContext()).id_brand=indexBrandlist.get(position);
                startActivity(new Intent(Brands_list.this, Brand.class));
            }

            @Override
            public void onLongClick(View view, int position) {
                Toast.makeText(Brands_list.this, "Long press on position :"+position,
                        Toast.LENGTH_LONG).show();
            }
        }));
    }

    public static interface ClickListener{
        public void onClick(View view, int position, String test);
        public void onLongClick(View view,int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener){

            this.clicklistener=clicklistener;
            gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child=recycleView.findChildViewUnder(e.getX(),e.getY());
                    if(child!=null && clicklistener!=null){
                        clicklistener.onLongClick(child,recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child=rv.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clicklistener!=null && gestureDetector.onTouchEvent(e)){
                clicklistener.onClick(child,rv.getChildAdapterPosition(child), "Test");
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }




    public void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    //JSONArray jsonArray = new JSONArray(s);
//                    JSONObject obj = new JSONObject(s);
                    //((variables) getApplicationContext()).message=obj.getString("message");

                    //String paketstr = obj.getString("paket");
                    JSONArray json_brands = new JSONArray(s);

                    brandsArrayList = new ArrayList<>();
                    indexBrandlist = new ArrayList<String>();
                    for (int i = 0; i <json_brands.length(); i++) {
                        JSONObject json_pro = json_brands.getJSONObject(i);


                        indexBrandlist.add(json_pro.getString("id"));
                        brandsArrayList.add(new Brands(json_pro.getString("nama_brand"), json_pro.getString("kategori")+", "+json_pro.getString("sub_kategori"), json_pro.getString("nama_file")));
                    }
                    //brandsArrayList.add(new Brands("ts nama", "tes ka", "gambar/users/files/1/brands/Bosco-Ice-Blend-27-23.jpg"));


                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);

                    BrandsAdapter adapter = new BrandsAdapter(brandsArrayList);

                    //RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Brands_list.this, LinearLayoutManager.VERTICAL, false);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(Brands_list.this, 2);

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
}
