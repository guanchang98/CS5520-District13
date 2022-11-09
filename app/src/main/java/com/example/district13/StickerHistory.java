package com.example.district13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

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

        Intent intentHistory = getIntent();
        username = intentHistory.getStringExtra("UsernameHistory");
        userNameText = findViewById(R.id.history_user);
        userNameText.setText("User: " + username);


        Button sentBtn = findViewById(R.id.button_history_sent);
        sentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StickerHistory.this, SentHistoryActivity.class);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });

        Button receivedBtn = findViewById(R.id.button_history_received);
        receivedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StickerHistory.this, ReceivedHistoryActivity.class);
                intent.putExtra("Username", username);
                startActivity(intent);
            }
        });

//        dbHisSe = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("sent_stickers");
//        dbHisSe.orderByKey().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<String> str = new ArrayList<>();
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    str.add(data.getKey());
//                }
//                String s = str.stream()
//                        .map(n -> String.valueOf(n)).collect(Collectors.joining("\n"));
//                stickerSent.setText("Sticker Sent: \n" + s);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "getUser:onCancelled", error.toException());
//            }
//        });
//
//        dbHisRe = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("received_stickers");
//        dbHisRe.orderByKey().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                List<String> str = new ArrayList<>();
//                for (DataSnapshot data : snapshot.getChildren()) {
//                    str.add(data.getKey());
//                }
//                String s = str.stream()
//                        .map(n -> String.valueOf(n)).collect(Collectors.joining("\n"));
//                stickerReceived.setText("Sticker Received: \n" + s);
//        }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "getUser:onCancelled", error.toException());
//            }
//        });
    }
}