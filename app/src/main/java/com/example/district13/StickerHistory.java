package com.example.district13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StickerHistory extends AppCompatActivity {
    private static final String TAG = "StickerHistory";

    DatabaseReference dbHis;

    TextView userNameText;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_history);

        Intent intent = getIntent();
        username = intent.getStringExtra("Username");
        userNameText = findViewById(R.id.history_user);
//        userNameText.setText(username);

//        dbHis = FirebaseDatabase.getInstance().getReference().child("users");

    }
}