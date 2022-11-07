package com.example.district13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.district13.sticker_image.StickerImage;
import com.example.district13.sticker_image.StickerImageAdapter;
import com.example.district13.sticker_user.StickerUser;
import com.example.district13.sticker_user.StickerUserAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StickerActivity extends AppCompatActivity {
    private static final String TAG = "StickerActivity";

    TextView welcomeText;
    RecyclerView usersRecyclerView;
    List<StickerUser> userList;
    List<StickerImage> stickerList;

    List<String> selectedUsers;
    List<String> selectedImagePaths;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

        Intent intent = getIntent();
        username = intent.getStringExtra("Username");
        welcomeText = findViewById(R.id.textView_sticker_welcome);
        welcomeText.setText(getString(R.string.text_sticker_welcome_user, username));

        userList = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            String cur = "user" + i;
//            userList.add(new StickerUser(cur));
//        }

        // Write a message to the database
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
//        dbRef.child("users").child("user0").child("sent_stickers").setValue(new ArrayList<>(Arrays.asList("sent_image_0")));
//        dbRef.child("users").child("user0").child("received_stickers").setValue(new ArrayList<>(Arrays.asList("received_image_0")));
        dbRef.child("imageURLs").child("0").setValue("0.png");
        dbRef.child("users").child("user0").child("received_stickers").push().setValue("sticker3");
        dbRef.child("users").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.d(TAG, data.getKey());
                    StickerUser stickerUser = new StickerUser(data.getKey());
                    userList.add(stickerUser);
                }
                usersRecyclerView = findViewById(R.id.recyclerView_sticker_user);

                usersRecyclerView.setHasFixedSize(true);
                //This defines the way in which the RecyclerView is oriented
                usersRecyclerView.setLayoutManager(new LinearLayoutManager(StickerActivity.this));

                //Associates the adapter with the RecyclerView
                usersRecyclerView.setAdapter(new StickerUserAdapter(userList, StickerActivity.this));

                Log.v(TAG, userList.size() + " before");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                // ...
            }
        });




        Log.v(TAG, "Initialize sticker list...");
        stickerList = new ArrayList<>();
//        URL res = getClass().getClassLoader().getResource("sticker_resources/angry.png");
//        File file = null;
//        try {
//            file = Paths.get(res.toURI()).toFile();
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        String absolutePath = file.getAbsolutePath();
//        Log.v(TAG, absolutePath);

        selectedUsers = new ArrayList<>();
        selectedImagePaths = new ArrayList<>();


        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                String cur = (String) dataSnapshot.getValue();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(StickerActivity.this, "channel_id")
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Receive new sticker")
                        .setContentText("Someone send you a new sticker " + cur)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

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

    public void showSelectStickersDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = (View) inflater.inflate(R.layout.recycler_view_stickers, null);

        builder.setView(dialogView);
        builder.setTitle(R.string.textView_sticker_dialog_title);

        RecyclerView rv = (RecyclerView) dialogView.findViewById(R.id.recyclerView_image);

        StickerImageAdapter adapter = new StickerImageAdapter(stickerList, StickerActivity.this);
        rv.setAdapter(adapter);
        builder.setPositiveButton(R.string.textView_sticker_dialog_send, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO: Add all selected stickers' paths to all selected users
                Log.v(TAG, userList.size() + " after");
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