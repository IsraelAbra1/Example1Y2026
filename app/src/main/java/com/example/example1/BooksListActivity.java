package com.example.example1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BooksListActivity extends AppCompatActivity {

    private RecyclerView rvBooks;
    private BooksAdapter adapter;
    private List<Book> bookList;
    private DatabaseReference mDatabase;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_list);

        applyBackgroundColor();

        rvBooks = findViewById(R.id.rvBooks);
        btnBack = findViewById(R.id.btnBack);
        
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
        bookList = new ArrayList<>();
        adapter = new BooksAdapter(bookList);
        rvBooks.setAdapter(adapter);

        mDatabase = FirebaseDatabase.getInstance().getReference("books");
        
        fetchBooks();

        btnBack.setOnClickListener(v -> finish());
    }

    private void fetchBooks() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Book book = postSnapshot.getValue(Book.class);
                    bookList.add(book);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BooksListActivity.this, "שגיאה בטעינת נתונים: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

        View layout = findViewById(R.id.listBooksMainLayout);
        if (layout != null) {
            layout.setBackgroundColor(ContextCompat.getColor(this, colorRes));
        }
    }
}
