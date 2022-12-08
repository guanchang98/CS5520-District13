package com.example.district13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OthersAccountActivity extends AppCompatActivity {

    private String userId;
    private TextView posterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_account);
        Intent intent = getIntent();
        userId = intent.getStringExtra("PosterID");
        posterName = findViewById(R.id.user_name);
        posterName.setText(userId);
    }
}