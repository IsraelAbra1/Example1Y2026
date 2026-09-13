package com.example.example1.gemini;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.ImagePart;
import com.google.ai.client.generativeai.type.Part;
import com.google.ai.client.generativeai.type.TextPart;

import java.util.ArrayList;
import java.util.List;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class GeminiManager {
    // In local.properties, add this line: gemini.api.key=YOUR_GEMINI_API_KEY
    private String apiKey = com.example.example1.BuildConfig.GEMINI_API_KEY;
    private static GeminiManager instance;
    private GenerativeModel gemini;

    private GeminiManager() {
        initModel();
    }

    private void initModel() {
        gemini = new GenerativeModel(
                "gemini-3.5-flash-lite",
                apiKey
        );
    }

    public void setApiKey(String newApiKey) {
        if (newApiKey != null && !newApiKey.isEmpty()) {
            this.apiKey = newApiKey;
            initModel();
        }
    }

    public static GeminiManager getInstance() {
        if (null == instance) {
            instance = new GeminiManager();
        }
        return instance;
    }


    public void sendMessage(String prompt, GeminiCallback callback) {
        gemini.generateContent(prompt,
                new Continuation<GenerateContentResponse>() {
                    @NonNull
                    @Override
                    public CoroutineContext getContext() {
                        return EmptyCoroutineContext.INSTANCE;
                    }

                    @Override
                    public void resumeWith(@NonNull Object result) {
                        if (result instanceof Result.Failure) {
                            callback.onError(((Result.Failure) result).exception);
                        } else {
                            callback.onSuccess(((GenerateContentResponse) result).getText());
                        }
                    }
                }
        );
    }


    public void sendMessageWithPhoto(String prompt, Bitmap photo, GeminiCallback callback) {
        List<Part> parts = new ArrayList<Part>();
        parts.add(new TextPart(prompt));
        parts.add(new ImagePart(photo));
        Content[] content = new Content[1];
        content[0] = new Content(parts);

        gemini.generateContent(content,
                new Continuation<GenerateContentResponse>() {
                    @NonNull
                    @Override
                    public CoroutineContext getContext() {
                        return EmptyCoroutineContext.INSTANCE;
                    }

                    @Override
                    public void resumeWith(@NonNull Object result) {
                        if (result instanceof Result.Failure) {
                            callback.onError(((Result.Failure) result).exception);
                        } else {
                            callback.onSuccess(((GenerateContentResponse) result).getText());
                        }
                    }
                }
        );
    }
}




