package com.example.example1;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class InstructionInHebrew extends AppCompatActivity {

    TextToSpeech textToSpeech;
    Button btnSpeak;
    TextView tvInstructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instruction_in_hebrew);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSpeak = findViewById(R.id.btnSpeak);
        tvInstructions = findViewById(R.id.tvInstructions);

        textToSpeech = new TextToSpeech(this,status -> {

            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(new Locale("he", "IL"));
                textToSpeech.setSpeechRate(0.9f);
            }
        });

        btnSpeak.setOnClickListener(v -> {

            String hebrewText =
                    "ברוכה הבאה לטריפ פלאנר. " +
                            "כאן תוכלי לתכנן את הטיול שלך, לבחור יעד, " +
                            "להוסיף אטרקציות, לתכנן את המסלול ולנהל את התקציב שלך.";

            String englishText =
                    "Welcome to TripPlanner. " +
                            "Here you can plan your trip, choose a destination, " +
                            "add attractions, organize your itinerary and manage your budget.";

            textToSpeech.setLanguage(new Locale("he", "IL"));

            textToSpeech.speak(
                    hebrewText,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "HEBREW"
            );

            textToSpeech.setLanguage(Locale.ENGLISH);

            textToSpeech.speak(
                    englishText,
                    TextToSpeech.QUEUE_ADD,
                    null,
                    "ENGLISH"
            );
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}