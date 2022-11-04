package com.example.district13;

import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StickerActivity extends AppCompatActivity {
    private static final String TAG = "StickerActivity";

    TextView welcomeText;
    RecyclerView usersRecyclerView;
    List<StickerUser> userList;
    List<StickerImage> stickerList;

    List<String> selectedUsers;
    List<String> selectedImagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker);

        Intent intent = getIntent();
        String username = intent.getStringExtra("Username");
        welcomeText = findViewById(R.id.textView_sticker_welcome);
        welcomeText.setText(getString(R.string.text_sticker_welcome_user, username));

        userList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String cur = "user" + i;
            userList.add(new StickerUser(cur));
        }

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

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        usersRecyclerView = findViewById(R.id.recyclerView_sticker_user);

        usersRecyclerView.setHasFixedSize(true);
        //This defines the way in which the RecyclerView is oriented
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(StickerActivity.this));

        //Associates the adapter with the RecyclerView
        usersRecyclerView.setAdapter(new StickerUserAdapter(userList, StickerActivity.this));
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
//                Snackbar snackbar;
//                snackbar = Snackbar.make(findViewById(R.id.snackbar_sticker_dialog), R.string.textView_sticker_dialog_send_success, 2000);
//                snackbar.show();
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