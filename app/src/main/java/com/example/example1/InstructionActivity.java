package com.example.example1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class InstructionActivity extends AppCompatActivity {

    TextToSpeech textToSpeech;
    Button btn;
    TextView tvInstructions, tvEnglish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        applyBackgroundColor();

        btn = findViewById(R.id.btn);
        tvInstructions = findViewById(R.id.tvInstructions);
        tvEnglish = findViewById(R.id.tvEnglish);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS)
                {
                    int lang = textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = tvEnglish.getText().toString();


                textToSpeech.speak(
                        str,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "instruction_utterance"
                );

            }
        });






    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    private void applyBackgroundColor() {
        SharedPreferences prefs = getSharedPreferences("gemini_prefs", MODE_PRIVATE);
        String savedColorName = prefs.getString("bg_color_name", "לבן");

        int colorRes;
        switch (savedColorName) {
            case "תכלת":
                colorRes = R.color.light_blue;
                break;
            case "אפור בהיר":
                colorRes = R.color.light_grey;
                break;
            case "ורוד בהיר":
                colorRes = R.color.light_pink;
                break;
            case "לבן":
            default:
                colorRes = R.color.white;
                break;
        }

        View layout = findViewById(R.id.instructionMainLayout);
        if (layout != null) {
            layout.setBackgroundColor(ContextCompat.getColor(this, colorRes));
        }
    }
}
