package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ipset extends AppCompatActivity {
    EditText e1;
    Button b1;
    String ip;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_ipset);
        e1=findViewById(R.id.editTextTextPersonName3);
        b1=findViewById(R.id.button2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ip=e1.getText().toString();
                SharedPreferences.Editor edp = sh.edit();
                edp.putString("ip", ip);
                edp.commit();



                if (ip.equalsIgnoreCase(""))
                {
                    e1.setError("Enter ip address");
                }
                else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }




            }
        });
    }
}