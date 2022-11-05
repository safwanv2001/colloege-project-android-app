package com.example.chatbot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class voice_search extends ListeningActivity {
    TextToSpeech tts;
    TextView tv;
    Button b1;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voice_search);
        tv=findViewById(R.id.textView59);
        context = getApplicationContext();
        b1=findViewById(R.id.button29);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ik = new Intent(getApplicationContext(), Staff_home.class);
                ik.putExtra("s","v");
                startActivity(ik);


            }
        });


        try {
            VoiceRecognitionListener.getInstance().setListener(this);
        } catch (Exception e) {
            Toast.makeText(context, "++++"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        tts = new TextToSpeech(voice_search.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                    tts.setSpeechRate(1.2f);
                }
                ConvertTextToSpeech("hello");
            }
        });
        startListening();
    }


    private void ConvertTextToSpeech(String text) {
        // TODO Auto-generated method stub
        if (text == null || "".equals(text)) {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        } else
            tts.speak(text + "", TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void processVoiceCommands(String... voiceCommands) {

      String  text = voiceCommands[0];
        tv.setText(text);
        if (text.contains("mark attendance")) {
            ConvertTextToSpeech("mark attendance");
            Intent ik = new Intent(getApplicationContext(), mark_attendance_staff.class);
            ik.putExtra("s","v");
            startActivity(ik);
        }

           else if(text.contains("attendance")){
                ConvertTextToSpeech("showing attendance");
            Intent ik = new Intent(getApplicationContext(), View_attendance.class);
            startActivity(ik);
            ik.putExtra("s","v");
            startActivity(ik);
            }
            else if(text.contains("work")){
                ConvertTextToSpeech("showing allocated work's");
                Intent ik = new Intent(getApplicationContext(), View_work_allocated.class);
                startActivity(ik);
            ik.putExtra("s","v");
            startActivity(ik);
            }

            else if(text.contains("report")){
                ConvertTextToSpeech("showing work report's");
                Intent ik = new Intent(getApplicationContext(), view_work_report.class);
                startActivity(ik);
            ik.putExtra("s","v");
            startActivity(ik);
            }

            else if (text.contains("my subject"))
            {
                ConvertTextToSpeech("showing your subject's");
                Intent ik = new Intent(getApplicationContext(), view_allocated_subject.class);
                startActivity(ik);
                ik.putExtra("s","v");
                startActivity(ik);
            }

            else if (text.contains("mark"))
            {
                ConvertTextToSpeech("displaying mark's of your subject");
                Intent ik = new Intent(getApplicationContext(), view_mark_staff.class);
                startActivity(ik);
                ik.putExtra("s","v");
                startActivity(ik);
            }
            else if (text.contains("time table"))
            {
                ConvertTextToSpeech("showing timetable");
                Intent ik = new Intent(getApplicationContext(), view_timetable.class);
                startActivity(ik);
                ik.putExtra("s","v");
                startActivity(ik);
            }
            else if (text.contains("gear shifter 400"))
            {
                ConvertTextToSpeech("duke amal");

            }

            else {
                ConvertTextToSpeech("sorry i can't reply to that");
            }









        startListening();

    }
}