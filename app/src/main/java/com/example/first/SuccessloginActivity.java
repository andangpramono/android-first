package com.example.first;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SuccessloginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successlogin);


        TextView show_email = (TextView) findViewById(R.id.txt_data);
        String message= ((variables) getApplicationContext()).message;

        show_email.setText(message);

    }
}
