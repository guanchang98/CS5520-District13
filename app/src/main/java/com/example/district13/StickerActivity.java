package com.example.district13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class StickerActivity extends AppCompatActivity {
    private static final String TAG = "StickerActivity";

    TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        welcomeText = findViewById(R.id.textView_sticker_welcome);
        welcomeText.setText(getString(R.string.text_sticker_welcome_user, username));

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("message").child("test").setValue("Hello, World!");
    }




}