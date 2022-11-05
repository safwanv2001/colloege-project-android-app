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
import android.widget.EditText;
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

public class Add_work_student extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s2;
    EditText e1, e2;
    Button b1;
    String st1, st2,sub;
    ArrayList<String>subject,sid;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_work_student);

        s2 = findViewById(R.id.spinner2);
        e1 = findViewById(R.id.editTextTextPersonName5);
        e2 = findViewById(R.id.editTextTextPersonName6);
        b1 = findViewById(R.id.button3);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        s2.setOnItemSelectedListener(this);
        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
       String url ="http://"+sh.getString("ip","")+":5000/view_allocated_subject";
        RequestQueue queue = Volley.newRequestQueue(Add_work_student.this);

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

                    ArrayAdapter<String> ad=new ArrayAdapter<>(Add_work_student.this,android.R.layout.simple_spinner_item,subject);
                    s2.setAdapter(ad);


                    // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Add_work_student.this, "err"+error, Toast.LENGTH_SHORT).show();
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
                st1 = e1.getText().toString();
                st2 = e2.getText().toString();
                if (st1.equalsIgnoreCase(""))
                {
                    e1.setError("Enter work");
                }
                else if(st2.equalsIgnoreCase(""))
                {
                    e2.setError("Enter last date");
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(Add_work_student.this);
                    String url = "http://" + sh.getString("ip", "") + ":5000/add_work";


                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("task success")) {
                                    Toast.makeText(getApplicationContext(), "Work added", Toast.LENGTH_LONG).show();
                                    Intent in = new Intent(getApplicationContext(), Staff_home.class);

                                    startActivity(in);

                                } else {
                                    Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("work", st1);
                            params.put("subdate", st2);
                            params.put("stid", sh.getString("lid", ""));
                            params.put("subid", sub);


                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }

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





}
