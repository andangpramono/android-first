package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final Button btnregister = (Button) findViewById(R.id.btnregister);

        final RequestQueue queue = Volley.newRequestQueue(this);

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText et_firstname = (EditText) findViewById(R.id.first_name_register);
                final EditText et_lastname = (EditText) findViewById(R.id.last_name_register);
                final EditText et_email = (EditText) findViewById(R.id.email_register);
                final EditText et_phone = (EditText) findViewById(R.id.phone_register);
                final EditText et_alamat = (EditText) findViewById(R.id.alamat_register);
                final EditText et_password = (EditText) findViewById(R.id.password_register);
                final EditText et_password_confirm = (EditText) findViewById(R.id.password_confirm_register);

                String url = "https://bukawaralaba.com/api_android/register";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    boolean error = obj.getBoolean("error");
                                    if(!error){


                                    }
                                    if(error){

                                        boolean first_name_error_b = obj.getBoolean("first_name_error_b");
                                        boolean email_eror_b = obj.getBoolean("email_eror_b");
                                        boolean phone_eror_b = obj.getBoolean("phone_eror_b");
                                        boolean password_eror_b = obj.getBoolean("password_eror_b");
                                        boolean password_confirm_eror_b = obj.getBoolean("password_confirm_eror_b");

                                        if(first_name_error_b){
                                            String first_name = obj.getString("first_name");
                                            et_firstname.setHint(first_name);
                                        }

                                        if(obj.getString("email_eror")!=null){
                                            String email_eror = obj.getString("email_eror");
                                            et_email.setHint(email_eror);
                                        }
                                        if(email_eror_b){
                                            String phone_eror = obj.getString("phone_eror");
                                            et_phone.setHint(phone_eror);
                                        }
                                        if(password_eror_b){
                                            String password_eror = obj.getString("password_eror");
                                            et_password.setHint(password_eror);
                                        }
                                        if(password_confirm_eror_b){
                                            String password_confirm_eror = obj.getString("password_confirm_eror");
                                            et_password_confirm.setHint(password_confirm_eror);
                                        }

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
                        params.put("first_name", et_firstname.getEditableText().toString());
                        params.put("last_name",et_lastname.getEditableText().toString());
                        params.put("phone", et_phone.getEditableText().toString());
                        params.put("email",et_email.getEditableText().toString());
                        params.put("alamat",et_alamat.getEditableText().toString());
                        params.put("password", et_password.getEditableText().toString());
                        params.put("password_confirm",et_password_confirm.getEditableText().toString());

                        return params;
                    }
                };
                queue.add(postRequest);

            }
        });
    }
}
