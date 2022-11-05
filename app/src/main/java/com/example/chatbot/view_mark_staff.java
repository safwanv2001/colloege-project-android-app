package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class view_mark_staff extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s2;
    ListView l1;
    SharedPreferences sh;
    ArrayList<String>student,mark,subject,sid;
    String subjectid;
    String type="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_mark_staff);
        s2=findViewById(R.id.spinner12);
        l1=findViewById(R.id.list10);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        try{
            type=getIntent().getStringExtra("s");

        }
        catch (Exception e)
        {

        }

        s2.setOnItemSelectedListener(this);
        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String url ="http://"+sh.getString("ip","")+":5000/view_allocated_subject";
        RequestQueue queue = Volley.newRequestQueue(view_mark_staff.this);

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

                    ArrayAdapter<String> ad=new ArrayAdapter<>(view_mark_staff.this,android.R.layout.simple_spinner_item,subject);
                    s2.setAdapter(ad);


                    // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(view_mark_staff.this, "err"+error, Toast.LENGTH_SHORT).show();
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















    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        subjectid=sid.get(position);



        String  url1 ="http://"+sh.getString("ip", "") + ":5000/view_mark_staff";
        RequestQueue queue = Volley.newRequestQueue(view_mark_staff.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    student=new ArrayList<>();
                    mark=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);

                        student.add(jo.getString("f_name")+" "+ jo.getString("l_name"));
                        mark.add(jo.getString("mark"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom2(view_mark_staff.this,student,mark));
//                    l1.setOnItemClickListener(view_allocated_subject.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(view_mark_staff.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sid",subjectid);
                return params;
            }
        };
        queue.add(stringRequest);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onBackPressed() {
        if (type.equals("v"))
        {
            Intent ik=new Intent(getApplicationContext(),voice_search.class);
            startActivity(ik);

        }
        else {
            super.onBackPressed();
        }
    }
}