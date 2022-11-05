package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
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

public class View_work_allocated extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    ArrayList<String>work,asgn_date,sub_date,work_id;
    Button b1;
    String wid;
    String type="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_work_allocated);
        l1=findViewById(R.id.list7);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1=findViewById(R.id.button23);

        try {
            type=getIntent().getStringExtra("s");
        }
        catch (Exception e)
        {}

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Add_work_student.class));
            }
        });
        String  url ="http://"+sh.getString("ip", "") + ":5000/view_work";
        RequestQueue queue = Volley.newRequestQueue(View_work_allocated.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    work_id=new ArrayList<>();
                    work=new ArrayList<>();
                    asgn_date=new ArrayList<>();
                    sub_date=new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);


                        work_id.add(jo.getString("work_id"));
                        work.add(jo.getString("work"));
                        asgn_date.add(jo.getString("Assigned_date"));
                        sub_date.add(jo.getString("Submission_date"));


                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom3(View_work_allocated.this,work,asgn_date,sub_date));
                    l1.setOnItemClickListener(View_work_allocated.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(View_work_allocated.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("sid",sh.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        wid=work_id.get(position);

        AlertDialog.Builder ald=new AlertDialog.Builder(View_work_allocated.this);
        ald.setTitle("File")
                .setPositiveButton("Delete ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        RequestQueue queue = Volley.newRequestQueue(View_work_allocated.this);
                        String  url = "http://" + sh.getString("ip","") + ":5000/deletework";

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Log.d("+++++++++++++++++", response);
                                try {
                                    JSONObject json = new JSONObject(response);

                                    String res = json.getString("task");

                                    if (res.equalsIgnoreCase("success")) {
                                        Toast.makeText(View_work_allocated.this, "deleted!!! ", Toast.LENGTH_SHORT).show();

                                        Intent i=new Intent(getApplicationContext(),View_work_allocated.class);
                                        startActivity(i);

                                    } else {

                                        Toast.makeText(View_work_allocated.this, "Invalid ", Toast.LENGTH_SHORT).show();

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
                                params.put("wid",wid);

                                return params;
                            }
                        };
                        queue.add(stringRequest);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent i=new Intent(getApplicationContext(),View_work_allocated.class);
                        startActivity(i);
                    }
                });

        AlertDialog al=ald.create();
        al.show();

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