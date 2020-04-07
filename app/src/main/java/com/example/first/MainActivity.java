package com.example.first;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.first.apihelper.UserService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;
    UserService UserService;
    OutputStream out = null;
    String text_email="";
    String text_password="";
    private final OkHttpClient client = new OkHttpClient();
    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button button = (Button) findViewById(R.id.btnLogin);
        final TextView txterror = (TextView) findViewById(R.id.error);
        EditText et_email = (EditText) findViewById(R.id.identity);
        EditText et_password = (EditText) findViewById(R.id.password);
        final SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Typeface reguler = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.otf");
        Typeface thin = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.otf");
        Typeface Regular = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.otf");

        button.setTypeface(reguler);
        txterror.setTypeface(thin);
        et_email.setTypeface(Regular);
        et_password.setTypeface(Regular);

        //UserService = UtilsApi.getUserService();
        final RequestQueue queue = Volley.newRequestQueue(this);

        String Login = sharedpreferences.getString("Login", "false");

        if (Login.equalsIgnoreCase("true")){
            startActivity(new Intent(MainActivity.this, Brands_list.class));
            finish();
        }
        button.setText("Login");

        TextView linkregister = (TextView) findViewById(R.id.lnkRegister);
        linkregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });


        Button btn_login = (Button) findViewById(R.id.btnLogin);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(View v) {

                EditText et_email = (EditText) findViewById(R.id.identity);
                EditText et_password = (EditText) findViewById(R.id.password);


                button.setText("Plesae Wait...");
                button.setEnabled(false);
                text_email= et_email.getEditableText().toString();
                text_password= et_password.getEditableText().toString();

                //getJSON("https://bukawaralaba.com/api_android/login2/?identity="+text_email+"&password="+text_password);

                String url = "https://bukawaralaba.com/api_android/login";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    boolean error = obj.getBoolean("error");
                                    String message = obj.getString("message");
                                    if(!error){
                                        button.setText("OK");
                                        button.setEnabled(true);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString("Login", "true");
                                        editor.putString("email", obj.getString("email"));
                                        editor.putString("file_foto", "http://bukawaralaba.com/"+obj.getString("file_foto"));
                                        editor.putString("nama_user", obj.getString("first_name")+" "+obj.getString("last_name"));
                                        editor.commit();

                                        startActivity(new Intent(MainActivity.this, Brands_list.class));
                                        finish();

                                    }
                                    if(error){
                                        txterror.setPadding(0,5,0,5);
                                        txterror.setText(message);
                                        button.setText("Login");
                                        button.setEnabled(true);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                //Log.d("Error.Response", response);
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                        {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("identity", text_email);
                        params.put("password", text_password);

                        return params;
                    }
                };
                queue.add(postRequest);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        final Button button = (Button) findViewById(R.id.btnLogin);
        final TextView txterror = (TextView) findViewById(R.id.error);
        EditText et_email = (EditText) findViewById(R.id.identity);
        EditText et_password = (EditText) findViewById(R.id.password);
        final SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Typeface reguler = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.otf");
        Typeface thin = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Medium.otf");
        Typeface Regular = Typeface.createFromAsset(getAssets(), "fonts/Poppins-Regular.otf");

        button.setTypeface(reguler);
        txterror.setTypeface(thin);
        et_email.setTypeface(Regular);
        et_password.setTypeface(Regular);


        //button.setText("Login");
        button.setEnabled(true);

        txterror.setPadding(0,0, 0,0);
        txterror.setText("");
        //final SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String Login = sharedpreferences.getString("Login", "false");

        if (Login.equalsIgnoreCase("true")){
            startActivity(new Intent(MainActivity.this, Brands_list.class));
            finish();

        }

        button.setText("Login");

        //UserService = UtilsApi.getUserService();
    }

    public void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    //JSONArray jsonArray = new JSONArray(s);
                    JSONObject obj = new JSONObject(s);
                    ((variables) getApplicationContext()).message=obj.getString("message");
                    String message =((variables) getApplicationContext()).message;

                    //loadIntoListView(s);
                    startActivity(new Intent(MainActivity.this, SuccessloginActivity.class));
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

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }


}
