package com.example.district13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.district13.sticker_image.StickerImage;
import com.example.district13.sticker_image.StickerImageAdapter;
import com.example.district13.sticker_user.StickerUser;
import com.example.district13.sticker_user.StickerUserAdapter;
import com.example.district13.sticker_user.StickerUserHistory;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StickerActivity extends AppCompatActivity {
    private static final String TAG = "StickerActivity";

    DatabaseReference dbRef;

    private final String CHANNEL_ID = "sticker_activity";

    TextView welcomeText;
    RecyclerView usersRecyclerView;
    RecyclerView stickersRecyclerView;
    List<StickerUser> userList;
    List<StickerImage> stickerList;
    // User history list
    List<StickerUserHistory> userHistoryList;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

        Intent intent = getIntent();
        username = intent.getStringExtra("Username");
        welcomeText = findViewById(R.id.textView_sticker_welcome);
        welcomeText.setText(getString(R.string.text_sticker_welcome_user, username));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        createNotificationChannel();

        // Navigate to user history
        Button stickerHistory = findViewById(R.id.history);
        stickerHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StickerActivity.this, StickerHistory.class));
            }
        });

        userList = new ArrayList<>();

        // Write a message to the database
        dbRef = FirebaseDatabase.getInstance().getReference();
//        for (int i = 0; i < 10; i++) {
//            dbRef.child("users").child("user" + i).child("sent_stickers").push().setValue(new StickerUserHistory("tester", "testURL"));
//            dbRef.child("users").child("user" + i).child("received_stickers").push().setValue(new StickerUserHistory("tester", "testURL"));
//        }


        dbRef.child("users").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    Log.d(TAG, data.getKey());
                    if (data.getKey().equals(username)) continue;
                    StickerUser stickerUser = new StickerUser(data.getKey());
                    userList.add(stickerUser);
                }
                usersRecyclerView = findViewById(R.id.recyclerView_sticker_user);

                usersRecyclerView.setHasFixedSize(true);
                //This defines the way in which the RecyclerView is oriented
                usersRecyclerView.setLayoutManager(new LinearLayoutManager(StickerActivity.this));

                //Associates the adapter with the RecyclerView
                usersRecyclerView.setAdapter(new StickerUserAdapter(userList, StickerActivity.this));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                // ...
            }
        });

//        dbRef.child("imageURLs").child("0").setValue("https://i.picsum.photos/id/0/5000/3333.jpg?hmac=_j6ghY5fCfSD6tvtcV74zXivkJSPIfR9B8w34XeQmvU");
//        dbRef.child("imageURLs").child("1").setValue("https://i.picsum.photos/id/0/5000/3333.jpg?hmac=_j6ghY5fCfSD6tvtcV74zXivkJSPIfR9B8w34XeQmvU");
//        dbRef.child("imageURLs").child("2").setValue("https://i.picsum.photos/id/0/5000/3333.jpg?hmac=_j6ghY5fCfSD6tvtcV74zXivkJSPIfR9B8w34XeQmvU");

        stickerList = new ArrayList<>();

        dbRef.child("imageURLs").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                stickerList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                    Log.d(TAG, data.getKey());
                    StickerImage stickerImage = new StickerImage((String) data.getValue());
                    stickerList.add(stickerImage);
                }

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = (View) inflater.inflate(R.layout.recycler_view_stickers, null);

                stickersRecyclerView = dialogView.findViewById(R.id.recyclerView_image);

                stickersRecyclerView.setHasFixedSize(true);
                //This defines the way in which the RecyclerView is oriented
                stickersRecyclerView.setLayoutManager(new LinearLayoutManager(StickerActivity.this));

                //Associates the adapter with the RecyclerView
                stickersRecyclerView.setAdapter(new StickerImageAdapter(stickerList, StickerActivity.this));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                // ...
            }
        });




        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.child("userName").getValue());
                String cur = (String) dataSnapshot.child("userName").getValue();
                String urlString = (String) dataSnapshot.child("stickerURL").getValue();
                showNotification(cur, urlString);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "postComments:onCancelled", databaseError.toException());
            }
        };
        dbRef.child("users").child(username).child("received_stickers").addChildEventListener(childEventListener);


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void showNotification(String sentUser, String urlString) {
        Bitmap myBitmap = null;
        try {
            URL url = new URL(urlString);
            myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch(IOException e) {
            System.out.println(e);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(StickerActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Receive new sticker")
                .setContentText(sentUser + " send you a new sticker ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(myBitmap)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(myBitmap)
                        .bigLargeIcon(null));
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(StickerActivity.this);

// notificationId is a unique int for each notification that you must define
        int notificationId = 1233425;
        notificationManager.notify(notificationId, builder.build());
    }

    public void showSelectStickersDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = (View) inflater.inflate(R.layout.recycler_view_stickers, null);


        builder.setTitle(R.string.textView_sticker_dialog_title);
        Log.v(TAG, "The sticker list size is: " + stickerList.size());

        RecyclerView rv = (RecyclerView) dialogView.findViewById(R.id.recyclerView_image);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        StickerImageAdapter adapter = new StickerImageAdapter(stickerList, StickerActivity.this);
        rv.setAdapter(adapter);

        builder.setView(dialogView);




        builder.setPositiveButton(R.string.textView_sticker_dialog_send, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                List<String> selectedUsers = new ArrayList<>();
                List<String> selectedImageUrls = new ArrayList<>();
                for (StickerUser stickerUser : userList) {
                    if (stickerUser.isSelected()) selectedUsers.add(stickerUser.getUsername());
                }

                for (StickerImage stickerImage : stickerList) {
                    if (stickerImage.isSelected()) selectedImageUrls.add(stickerImage.getImagePath());
                }
                if (selectedUsers.size() == 0) {
                    Toast.makeText(StickerActivity.this, "Please choose at least one user!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (selectedImageUrls.size() == 0) {
                    Toast.makeText(StickerActivity.this, "Please choose at least one sticker!", Toast.LENGTH_SHORT).show();
                    return;
                }

//                selectedImageUrls.add("https://i.picsum.photos/id/0/5000/3333.jpg?hmac=_j6ghY5fCfSD6tvtcV74zXivkJSPIfR9B8w34XeQmvU");
                for (String user : selectedUsers) {
                    for (String url : selectedImageUrls) {
//                        Log.v(TAG, user + " " + url);
                        StickerUserHistory sent = new StickerUserHistory(username, url);
                        StickerUserHistory received = new StickerUserHistory(user, url);
                        dbRef.child("users").child(user).child("received_stickers").push().setValue(sent);
                        dbRef.child("users").child(username).child("sent_stickers").push().setValue(received);
                    }
                }

                Toast.makeText(StickerActivity.this, "Send stickers successfully！", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton(R.string.textView_sticker_dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }




}