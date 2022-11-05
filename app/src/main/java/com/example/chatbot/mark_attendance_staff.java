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

public class mark_attendance_staff extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1,s2;

    Button b1;
    ListView l1;
    SharedPreferences sh;
    ArrayList<String > subject,sid,id,name,status;
    String ssid="",h="";
    String hour[]={"1","2","3","4","5","6","7"};
    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_mark_attendance_staff);
        s1=findViewById(R.id.spinner4);
        s2=findViewById(R.id.spinner5);
        b1=findViewById(R.id.button27);
        l1=findViewById(R.id.viiewmarkedattendance);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        try {
            type=getIntent().getStringExtra("s");
        }
        catch (Exception e)
        {}

        ArrayAdapter<String> ad=new ArrayAdapter<>(mark_attendance_staff.this,android.R.layout.simple_list_item_1,hour);
        s2.setAdapter(ad);


        s1.setOnItemSelectedListener(this);
        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String url ="http://"+sh.getString("ip","")+":5000/view_allocated_subject";
        RequestQueue queue = Volley.newRequestQueue(mark_attendance_staff.this);

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

                    ArrayAdapter<String> ad=new ArrayAdapter<>(mark_attendance_staff.this,android.R.layout.simple_spinner_item,subject);
                    s1.setAdapter(ad);

                    // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mark_attendance_staff.this, "err"+error, Toast.LENGTH_SHORT).show();
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

                h=s2.getSelectedItem().toString();



                String url ="http://"+sh.getString("ip","")+":5000/marked_attendance_staff";
                RequestQueue queue = Volley.newRequestQueue(mark_attendance_staff.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);

                            id= new ArrayList<>(ar.length());
                            name= new ArrayList<>(ar.length());
                            status= new ArrayList<>(ar.length());
                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                id.add(jo.getString("a_id"));
                                name.add(jo.getString("f_name")+" "+jo.getString("l_name"));

                                if(jo.getString("attendance").equals("1")) {
                                    status.add("P");
                                }
                                else
                                {
                                    status.add("A");
                                }

                            }
                            if(ar.length()>0)
                            {
                                l1.setAdapter(new custom2(mark_attendance_staff.this,name,status));
                            }
                            else
                            {


                                String uri ="http://"+sh.getString("ip","")+":5000/mark_attendance?sid="+ssid+"&h="+h;


                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                startActivity(intent);
                            }


                            // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(mark_attendance_staff.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("ssid",ssid);
                        params.put("h",h);
                        return params;
                    }
                };
                queue.add(stringRequest);















            }
        });



    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ssid=sid.get(position);
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