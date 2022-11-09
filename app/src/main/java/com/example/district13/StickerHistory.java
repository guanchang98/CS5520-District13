package com.example.district13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.district13.sticker_user.StickerUserHistory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StickerHistory extends AppCompatActivity {
    private static final String TAG = "StickerHistory";

    DatabaseReference dbHisRe;
    DatabaseReference dbHisSe;

    TextView userNameText;
    TextView stickerSent;
    TextView stickerReceived;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_history);

        stickerReceived = findViewById(R.id.history_received);
        stickerSent = findViewById(R.id.history_sent);

        Intent intentHistory = getIntent();
        username = intentHistory.getStringExtra("UsernameHistory");
        userNameText = findViewById(R.id.history_user);
        userNameText.setText("User: " + username);

        dbHisSe = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("sent_stickers");
        dbHisSe.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> str = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    str.add(data.getKey());
                }
                String s = str.stream()
                        .map(n -> String.valueOf(n)).collect(Collectors.joining("\n"));
                stickerSent.setText("Sticker Sent: \n" + s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "getUser:onCancelled", error.toException());
            }
        });

        dbHisRe = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("received_stickers");
        dbHisRe.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> str = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    str.add(data.getKey());
                }
                String s = str.stream()
                        .map(n -> String.valueOf(n)).collect(Collectors.joining("\n"));
                stickerReceived.setText("Sticker Received: \n" + s);
        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "getUser:onCancelled", error.toException());
            }
        });
    }
}