package com.example.district13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class StickerActivity extends AppCompatActivity {
    private static final String TAG = "StickerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("message").child("test").setValue("Hello, World!");
    }




}