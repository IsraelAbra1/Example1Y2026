package com.example.example1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        String name = getIntent().getStringExtra("USER_NAME");
        TextView welcomeText = findViewById(R.id.welcome_text);
        if (name != null && !name.isEmpty()) {
            welcomeText.setText(getString(R.string.welcome_message, name));
        }

        LinearLayout btnInstructions = findViewById(R.id.btninstructions);
        btnInstructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, InstructionActivity.class);
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
}
