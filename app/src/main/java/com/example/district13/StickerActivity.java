package com.example.district13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StickerActivity extends AppCompatActivity {
    private static final String TAG = "StickerActivity";

    TextView welcomeText;
    RecyclerView usersRecyclerView;
    List<StickerUser> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        welcomeText = findViewById(R.id.textView_sticker_welcome);
        welcomeText.setText(getString(R.string.text_sticker_welcome_user, username));

        userList = new ArrayList<>();
        userList.add(new StickerUser("user1"));
        userList.add(new StickerUser("user2"));
        userList.add(new StickerUser("user3"));

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("message").child("test").setValue("Hello, World!");

        usersRecyclerView = findViewById(R.id.recyclerView);
        //This defines the way in which the RecyclerView is oriented
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(StickerActivity.this));

        //Associates the adapter with the RecyclerView
        usersRecyclerView.setAdapter(new StickerUserAdapter(userList, StickerActivity.this));
    }




}