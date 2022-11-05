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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class view_attendance_parent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView t1,t2,t3,t4;
    Spinner s1;
    Button b1;
    ArrayList<String>twh,tph,tah,avg;
    String mnth,stid;
    SharedPreferences sh;
    String month[]={"january","february","march","april","may","june","july","august","september","october","november","december"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_view_attendance_parent);
        t1=findViewById(R.id.textView7);
        t2=findViewById(R.id.textView10);
        t3=findViewById(R.id.textView11);
        t4=findViewById(R.id.textView37);
        s1=findViewById(R.id.spinner8);
        b1=findViewById(R.id.button5);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ArrayAdapter<String> ad=new ArrayAdapter<>(view_attendance_parent.this,android.R.layout.simple_list_item_1,month);
        s1.setAdapter(ad);
        s1.setOnItemSelectedListener(this);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mnth=s1.getSelectedItem().toString();


                RequestQueue queue = Volley.newRequestQueue(view_attendance_parent.this);
                String  url = "http://" + sh.getString("ip", "") + ":5000/view_attendance_parent";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);
                            if (ar.length()>0) {
                                {
                                    JSONObject jo = ar.getJSONObject(0);
                                    t1.setText(jo.getString("twh"));
                                    t2.setText(jo.getString("tph"));
                                    t3.setText(jo.getString("ah"));
                                    t4.setText(jo.getString("pe"));


                                    if (android.os.Build.VERSION.SDK_INT > 9) {
                                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                        StrictMode.setThreadPolicy(policy);
                                    }

//        i2.setImageDrawable(Drawable.createFromPath(getIntent().getStringExtra("photo"))));
//                                java.net.URL thumb_u;
//                                try {
//
//                                    //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");
//
//                                    thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/photos/"+jo.getString("photo"));
//                                    Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
//                                    iv5.setImageDrawable(thumb_d);
//
//                                }
//                                catch (Exception e)
//                                {
//                                    Log.d("errsssssssssssss",""+e);
//                                }
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"No Value",Toast.LENGTH_LONG).show();


                                t1.setText(" ");
                                t2.setText(" ");
                                t3.setText(" ");
                                t4.setText(" ");
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("pid",sh.getString("lid",""));
                        params.put("mnth",mnth);


                        return params;
                    }
                };
                // Add the request to the RequestQueue.
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