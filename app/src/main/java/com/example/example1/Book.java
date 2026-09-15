package com.example.example1;

public class Book {
    public String title;
    public String author;

    // Default constructor required for calls to DataSnapshot.getValue(Book.class)
    public Book() {
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
