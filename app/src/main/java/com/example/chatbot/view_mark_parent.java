package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class view_mark_parent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ListView l1;
    Spinner s1;
    SharedPreferences sh;
    ArrayList<String>subject,average;
    String sem[]={"1","2","3","4","5","6"};
    String semester;
    Button b1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_mark_parent);
        l1=findViewById(R.id.list4);
        s1=findViewById(R.id.spinner);
        b1=findViewById(R.id.button24);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        ArrayAdapter<String> ad=new ArrayAdapter<>(view_mark_parent.this,android.R.layout.simple_list_item_1,sem);
        s1.setAdapter(ad);

        if(android.os.Build.VERSION.SDK_INT>9)
        {
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    semester=s1.getSelectedItem().toString();


                    String  url ="http://"+sh.getString("ip", "") + ":5000/view_mark_parent";
                    s1.setOnItemSelectedListener(view_mark_parent.this);
                    RequestQueue queue = Volley.newRequestQueue(view_mark_parent.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++",response);
                            try {

                                JSONArray ar=new JSONArray(response);

                                subject=new ArrayList<>();
                                average=new ArrayList<>();

                                for(int i=0;i<ar.length();i++)
                                {
                                    JSONObject jo=ar.getJSONObject(i);

                                    subject.add(jo.getString("subject"));
                                    average.add(jo.getString("mark"));


                                }

                                // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                                //lv.setAdapter(ad);

                                l1.setAdapter(new custom2(view_mark_parent.this,subject,average));
//                    l1.setOnItemClickListener(view_allocated_subject.this);

                            } catch (Exception e) {
                                Log.d("=========", e.toString());
                            }


                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(view_mark_parent.this, "err"+error, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("sem",semester);
                            params.put("pid",sh.getString("lid",""));
                            return params;
                        }
                    };
                    queue.add(stringRequest);



                }
            });




    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {







    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}