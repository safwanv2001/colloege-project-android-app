package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View_attendance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1,s2;
    Button b1,b2;
    ListView l1;
    ArrayList<String>subject,sid,name,twh,tph,avg;
    SharedPreferences sh;
    String sub,mnth;
    String type="";
    String month[]={"january","february","march","april","may","june","july","august","september","october","november","december"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_attendance);
        s1=findViewById(R.id.spinner6);
        s2=findViewById(R.id.spinner7);
        b1=findViewById(R.id.button4);
        b2=findViewById(R.id.button9);
        l1=findViewById(R.id.list2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        try {
            type=getIntent().getStringExtra("s");
        }
        catch (Exception e)
        {}

        ArrayAdapter<String> ad=new ArrayAdapter<>(View_attendance.this,android.R.layout.simple_list_item_1,month);
        s2.setAdapter(ad);


        s1.setOnItemSelectedListener(this);
        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String url ="http://"+sh.getString("ip","")+":5000/view_allocated_subject";
        RequestQueue queue = Volley.newRequestQueue(View_attendance.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    subject= new ArrayList<>(ar.length());
                    sid= new ArrayList<>(ar.length());
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        subject.add(jo.getString("subject"));
                        sid.add(jo.getString("subject_id"));


                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<>(View_attendance.this,android.R.layout.simple_spinner_item,subject);
                    s1.setAdapter(ad);


                    // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(View_attendance.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("stid",sh.getString("lid",""));
                return params;
            }
        };
        queue.add(stringRequest);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                mnth=s2.getSelectedItem().toString();

                String  url ="http://"+sh.getString("ip", "") + ":5000/view_attendance_staff";
                RequestQueue queue = Volley.newRequestQueue(View_attendance.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);

                            name=new ArrayList<>();
                            twh=new ArrayList<>();
                            tph=new ArrayList<>();
                            avg=new ArrayList<>();

                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);

                                name.add(jo.getString("f_name")+" "+jo.getString("l_name"));
                                twh.add(jo.getString("twh"));
                                tph.add(jo.getString("tph"));
                                avg.add(jo.getString("pe"));


                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

                            l1.setAdapter(new custom44(View_attendance.this,name,twh,tph,avg));
//                    l1.setOnItemClickListener(view_allocated_subject.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                            Toast.makeText(View_attendance.this, "err==="+e, Toast.LENGTH_SHORT).show();

                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(View_attendance.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("mnth",mnth);
                        params.put("sid",sub);
                        return params;
                    }
                };
                queue.add(stringRequest);



            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),mark_attendance_staff.class));

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        sub=sid.get(position);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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

