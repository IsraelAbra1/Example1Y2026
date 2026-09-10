package com.example.example1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SettingActivity extends AppCompatActivity {

    private Spinner spinnerColors;
    private Button btnSave, btnBack;
    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mainLayout = findViewById(R.id.settingMainLayout);
        spinnerColors = findViewById(R.id.spinnerColors);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        SharedPreferences prefs = getSharedPreferences("gemini_prefs", MODE_PRIVATE);

        // Load Background Color by name
        String savedColorName = prefs.getString("bg_color_name", "לבן");
        String[] colors = getResources().getStringArray(R.array.colors_array);
        int savedColorIndex = 0;
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals(savedColorName)) {
                savedColorIndex = i;
                break;
            }
        }
        spinnerColors.setSelection(savedColorIndex);
        updateBackgroundColor(savedColorIndex);

        spinnerColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateBackgroundColor(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedColorName = spinnerColors.getSelectedItem().toString();

                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("bg_color_name", selectedColorName);
                editor.apply();

                Toast.makeText(SettingActivity.this, "הגדרות נשמרו", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateBackgroundColor(int index) {
        int colorRes;
        switch (index) {
            case 1: colorRes = R.color.light_blue; break;
            case 2: colorRes = R.color.light_grey; break;
            case 3: colorRes = R.color.light_pink; break;
            case 0:
            default: colorRes = R.color.white; break;
        }
        mainLayout.setBackgroundColor(ContextCompat.getColor(this, colorRes));
    }
}
