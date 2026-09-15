package com.example.example1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        applyBackgroundColor();

        String name = getIntent().getStringExtra("USER_NAME");
        TextView welcomeText = findViewById(R.id.welcome_text);
        if (name != null && !name.isEmpty()) {
            welcomeText.setText(getString(R.string.welcome_message, name));
        }

        LinearLayout btnInstructions = findViewById(R.id.btninstructions);
        btnInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MenuActivity.this, InstructionActivity.class);
                Intent intent = new Intent(MenuActivity.this, InstructionInHebrew.class);
                startActivity(intent);
            }
        });

        LinearLayout btnLogout = findViewById(R.id.btnlogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        LinearLayout btnAddBook = findViewById(R.id.btnaddbook);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });


        LinearLayout btnAi = findViewById(R.id.btnAi);
        btnAi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AiActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyBackgroundColor();
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

        View layout = findViewById(R.id.menuMainLayout);
        if (layout != null) {
            layout.setBackgroundColor(ContextCompat.getColor(this, colorRes));
        }
    }
}
