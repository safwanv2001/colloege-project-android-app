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

public class vooice_search_student extends ListeningActivity {
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
                Intent ik = new Intent(getApplicationContext(), student_home.class);
                ik.putExtra("s","v");
                startActivity(ik);



            }
        });


        try {
            VoiceRecognitionListener.getInstance().setListener(this);
        } catch (Exception e) {
            Toast.makeText(context, "++++"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
        tts = new TextToSpeech(vooice_search_student.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                    tts.setSpeechRate(1.0f);
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
        if (text.contains("notification")) {
            ConvertTextToSpeech("showing notification's");
            Intent ik = new Intent(getApplicationContext(), view_notification_student.class);
            ik.putExtra("n","o");
            startActivity(ik);
        }


        else if (text.contains("mark"))
        {
            ConvertTextToSpeech("displaying mark's");
            Intent ik = new Intent(getApplicationContext(), view_marks_student.class);
            ik.putExtra("m","k");
            startActivity(ik);
        }

        else if (text.contains("time table"))
        {
            ConvertTextToSpeech("showing timetable");
            Intent ik = new Intent(getApplicationContext(), view_timetable_student.class);
            ik.putExtra("t","q");
            startActivity(ik);
        }

        else if(text.contains("attendance")){
            ConvertTextToSpeech("showing attendance");
            Intent ik = new Intent(getApplicationContext(), view_attendance_student.class);
            ik.putExtra("a","d");
            startActivity(ik);
        }

        else if(text.contains("work")){
            ConvertTextToSpeech("showing work's");
            Intent ik = new Intent(getApplicationContext(), view_work_student.class);
            ik.putExtra("s","v");
            startActivity(ik);
        }




        else {
            ConvertTextToSpeech("sorry i can't reply to that");
        }









        startListening();

    }
}