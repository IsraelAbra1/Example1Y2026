package com.example.example1.gemini;

public interface GeminiCallback {
    void onSuccess(String result);
    void onError(Throwable error);
}