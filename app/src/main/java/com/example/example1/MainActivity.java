package com.example.example1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        applyBackgroundColor();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText loginEmail = findViewById(R.id.login_email);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            String name = loginEmail.getText().toString();
            if (name.isEmpty()) {
                loginEmail.setError("Please enter your name");
                return;
            }
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            intent.putExtra("USER_NAME", name);
            startActivity(intent);
        });
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

        findViewById(R.id.main).setBackgroundColor(ContextCompat.getColor(this, colorRes));
    }
}