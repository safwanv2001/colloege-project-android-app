package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class student_home extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6;
    SharedPreferences sh;
    String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        b1=findViewById(R.id.button18);
        b2=findViewById(R.id.button19);
        b3=findViewById(R.id.button20);
        b4=findViewById(R.id.button21);
        b5=findViewById(R.id.button22);
        b6=findViewById(R.id.button16);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        try {
            type=getIntent().getStringExtra("s");
        }
        catch (Exception e)
        {}

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),view_notification_student.class));


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),view_marks_student.class));

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),view_timetable_student.class));


            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),view_attendance_student.class));


            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),view_work_student.class));


            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));


            }
        });
    }
    @Override
    public void onBackPressed() {
        if(type.equals("v"))
        {
            Intent ik = new Intent(getApplicationContext(), voice_search.class);
            startActivity(ik);
        }
        else
        {
            super.onBackPressed();
        }
    }
}