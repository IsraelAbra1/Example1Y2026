package com.example.example1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBookActivity extends AppCompatActivity {

    private EditText etBookTitle;
    private EditText etBookAuthor;
    private Button btnSaveBook;
    private Button btnBackFromAdd;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        applyBackgroundColor();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        etBookTitle = findViewById(R.id.etBookTitle);
        etBookAuthor = findViewById(R.id.etBookAuthor);
        btnSaveBook = findViewById(R.id.btnSaveBook);
        btnBackFromAdd = findViewById(R.id.btnBackFromAdd);

        btnSaveBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etBookTitle.getText().toString().trim();
                String author = etBookAuthor.getText().toString().trim();

                if (title.isEmpty()) {
                    etBookTitle.setError("אנא הזן את שם הספר");
                    return;
                }

                saveBookToFirebase(title, author);
            }
        });

        btnBackFromAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void saveBookToFirebase(String title, String author) {
        String bookId = mDatabase.child("books").push().getKey();
        Book book = new Book(title, author);

        if (bookId != null) {
            mDatabase.child("books").child(bookId).setValue(book)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(AddBookActivity.this, "הספר '" + title + "' נשמר ב-Firebase!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(AddBookActivity.this, "שגיאה בשמירה: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
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

        View layout = findViewById(R.id.addBookMainLayout);
        if (layout != null) {
            layout.setBackgroundColor(ContextCompat.getColor(this, colorRes));
        }
    }
}
