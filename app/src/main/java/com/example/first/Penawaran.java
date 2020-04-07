package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Penawaran extends AppCompatActivity {
    List<String> list_paket_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penawaran);

        TextView url_penawaran = findViewById(R.id.url_penawaran);
        url_penawaran.setText(((variables) getApplicationContext()).url_penawaran);

        ArrayList<Paket_list> paketlist = ((variables) getApplicationContext()).paketList;
        String pilihan_paket;

        TextView listpaket = findViewById(R.id.listpaket);
        //listpaket.setText(paketlist.ge);

        RadioGroup group = (RadioGroup) findViewById(R.id.radioButtonpaket);
        RadioButton button;

        list_paket_id=new ArrayList<String>();
        list_paket_id.clear();
        for(Paket_list object: paketlist) {
            button = new RadioButton(this);
            button.setText(object.getNama_paket()+"");
            group.addView(button);
            list_paket_id.add(String.valueOf(object.getId()));

        }

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonID = group.getCheckedRadioButtonId();
                View radioButton = group.findViewById(radioButtonID);
                int position = group.indexOfChild(radioButton);

                TextView idpaket = findViewById(R.id.idpaket);
                idpaket.setText(list_paket_id.get(position));
            }
        });

//        for (Paket_list object: paketlist) {
//            listpaket.setText((CharSequence) object.getNama_paket());
//            RadioButton rb = new RadioButton(this);
//            rb.setText((CharSequence) object.getNama_paket()+"");
//            rg.addView(rb);
//        }
    }
}
