package com.example.district13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.district13.sticker_image.StickerImage;
import com.example.district13.sticker_user.StickerUser;
import com.example.district13.sticker_user.StickerUserAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class StickerActivity extends AppCompatActivity {
    private static final String TAG = "StickerActivity";

    TextView welcomeText;
    RecyclerView usersRecyclerView;
    List<StickerUser> userList;
    List<StickerImage> stickerList;

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

        stickerList = new ArrayList<>();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("message").child("test").setValue("Hello, World!");

        usersRecyclerView = findViewById(R.id.recyclerView_sticker_user);

        usersRecyclerView.setHasFixedSize(true);
        //This defines the way in which the RecyclerView is oriented
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(StickerActivity.this));

        //Associates the adapter with the RecyclerView
        usersRecyclerView.setAdapter(new StickerUserAdapter(userList, StickerActivity.this));
    }

    public void showSelectStickersDialog(View view) {

    }




}