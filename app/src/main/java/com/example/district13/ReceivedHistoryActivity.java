package com.example.district13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.district13.sticker_user_history.StickerUserHistory;
import com.example.district13.sticker_user_history.StickerUserHistoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReceivedHistoryActivity extends AppCompatActivity {

    private final String TAG = "ReceivedHistoryActivity";

    DatabaseReference dbRef;
    List<StickerUserHistory> userHistoryList;
    RecyclerView userHistoryRecyclerView;
    TextView receivedCount;


    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_history);

        Intent intent = getIntent();
        username = intent.getStringExtra("Username");

        userHistoryList = new ArrayList<>();
        receivedCount = findViewById(R.id.textView_received_title);

        dbRef = FirebaseDatabase.getInstance().getReference();
        dbRef.child("users").child(username).child("received_stickers").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userHistoryList.clear();
                int count = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    Log.d(TAG, data.getKey());
                    count++;
                    String userName = (String) data.child("userName").getValue();
                    String imageUrl = (String) data.child("stickerURL").getValue();
                    String date = (String) data.child("date").getValue();
                    userHistoryList.add(new StickerUserHistory(userName, imageUrl, date));
                }
                receivedCount.setText("Received History " + "(" + count + " total)");

                userHistoryRecyclerView = findViewById(R.id.recyclerView_history_received);

                userHistoryRecyclerView.setHasFixedSize(true);
                //This defines the way in which the RecyclerView is oriented
                userHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(ReceivedHistoryActivity.this));

                //Associates the adapter with the RecyclerView
                userHistoryRecyclerView.setAdapter(new StickerUserHistoryAdapter(userHistoryList, ReceivedHistoryActivity.this));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                // ...
            }
        });
    }
}