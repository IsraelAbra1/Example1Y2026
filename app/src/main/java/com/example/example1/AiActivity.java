package com.example.example1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.example1.gemini.GeminiCallback;
import com.example.example1.gemini.GeminiManager;

public class AiActivity extends AppCompatActivity {

    private static final String TAG = "AiActivity";
    private EditText etQuestion;
    private Button btnAsk, btnHome;
    private TextView tvAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai);

        etQuestion = findViewById(R.id.etQuestion);
        tvAnswer = findViewById(R.id.tvAnswer);
        btnAsk = findViewById(R.id.btnAsk);
        btnHome = findViewById(R.id.btnHome);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AiActivity.this, MainActivity.class));
            }
        });

        btnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q = etQuestion.getText().toString();
                if (q.isEmpty()) {
                    q = "מה הספר הכי נפוץ בישראל";
                }

                String prompt = q + " תשובה עד 50 מילים";
                GeminiManager.getInstance().sendMessage(prompt, new GeminiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        runOnUiThread(() -> tvAnswer.setText(response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Gemini error", e);
                        runOnUiThread(() -> Toast.makeText(AiActivity.this,
                                "Error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show());
                    }
                });
            }
        });
    }
}
