package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

public class view_timetable extends AppCompatActivity {
    Spinner s1;
    Button b1;
    ImageView m1;
    SharedPreferences sh;
    String semester,img;
    String sem[]={"1","2","3","4","5","6"};
    String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_timetable);
        s1=findViewById(R.id.spinner13);
        b1=findViewById(R.id.button17);
        m1=findViewById(R.id.imageView2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ArrayAdapter<String> ad=new ArrayAdapter<>(view_timetable.this,android.R.layout.simple_list_item_1,sem);
        s1.setAdapter(ad);


        try{
            type=getIntent().getStringExtra("t");

        }
        catch (Exception e)
        {

        }


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              semester=s1.getSelectedItem().toString();
                String url = "http://" + sh.getString("ip", "") + ":5000/view_timetable";
                RequestQueue queue = Volley.newRequestQueue(view_timetable.this);

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

                                //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");

                                thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/timetable/"+img);
                                Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
                                m1.setImageDrawable(thumb_d);

                            }
                            catch (Exception e)
                            {
                                Log.d("errsssssssssssss",""+e);
                            }





                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);
//
//                            l1.setAdapter(new Custom(viewuser.this,name,place));
//                            l1.setOnItemClickListener(viewuser.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(view_timetable.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("sem",semester);

                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (type.equals("q"))
        {
            Intent ik=new Intent(getApplicationContext(),voice_search.class);

        }
        else {
            super.onBackPressed();
        }
    }
}