package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class view_timetable_student extends AppCompatActivity {
    ImageView m1;
    SharedPreferences sh;
    String img;
    String type="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_timetable_student);
        m1=findViewById(R.id.imageView);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        try {
            type=getIntent().getStringExtra("s");
        }
        catch (Exception e)
        {}

        String  url ="http://"+sh.getString("ip", "") + ":5000/view_timetable_student";
        RequestQueue queue = Volley.newRequestQueue(view_timetable_student.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);



                    JSONObject jo=ar.getJSONObject(0);
                    img=jo.getString("timetable");
                    java.net.URL thumb_u;
                    try {



                        thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/timetable/"+img);
                        Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
                        m1.setImageDrawable(thumb_d);

                    }
                    catch (Exception e)
                    {
                        Log.d("errsssssssssssss",""+e);
                    }







                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(view_timetable_student.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid",sh.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);
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
