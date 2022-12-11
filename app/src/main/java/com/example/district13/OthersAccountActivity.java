package com.example.district13;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class OthersAccountActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    private TextView username_txt;
    private TextView email_txt;
    private TextView posts_txt;
    private TextView followings_txt;
    private TextView followers_txt;
    private ImageView avatar_image;

    private int postCount, followingCount, followersCount;

    private String otherUserId;
    private String userId;
    private Switch followSwitch;

    private Button historyPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_account);

        Intent intent = getIntent();
        otherUserId = intent.getStringExtra("PosterID");

        // Initialize firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("appUsers");

        // Initialize views
        username_txt = findViewById(R.id.textView_username);
        email_txt = findViewById(R.id.textView_email);
        posts_txt = findViewById(R.id.textView_posts);
        followings_txt = findViewById(R.id.textView_followings);
        followers_txt = findViewById(R.id.textView1_followers);
        avatar_image = findViewById(R.id.imageView_other_user_avatar);

        // Initialize variables
        userId = user.getUid();

        Log.v("OthersAccountActivity", "Other user id: " + otherUserId);

        // Set view
        reference.child(otherUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //get data
                String name = "" + dataSnapshot.child("name").getValue();
                String email = "" + dataSnapshot.child("email").getValue();
                String avatar = "" + dataSnapshot.child("avatar").getValue();

                //set data
                email_txt.setText(email);
                username_txt.setText(name);
                postCount = (int) dataSnapshot.child("posts").getChildrenCount();
                posts_txt.setText(postCount + " Posts");

                followingCount = (int) dataSnapshot.child("following").getChildrenCount();
                followings_txt.setText(followingCount + " Following");

                followersCount = (int) dataSnapshot.child("followers").getChildrenCount();
                if (dataSnapshot.child("followers").child(userId).exists()) {
                    followSwitch.setChecked(true);
                }
                followers_txt.setText(followersCount + " Followers");
                try {
                    //load avatar if set
                    Picasso.get().load(avatar).into(avatar_image);
                } catch (Exception e) {
                    //otherwise load default
                    Picasso.get().load(R.drawable.ic_add_user_selfie).into(avatar_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        followSwitch = findViewById(R.id.switch_follow);
        followSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!followSwitch.isChecked()) {
                    removeFollowRelationship();
                } else {
                    addFollowRelationship();
                }
            }
        });

//        historyPosts = findViewById(R.id.history_posts);
//        historyPosts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    }

    private void removeFollowRelationship() {
        reference.child(userId).child("following").child(otherUserId).removeValue();
        reference.child(otherUserId).child("followers").child(userId).removeValue();
    }

    private void addFollowRelationship() {
        reference.child(userId).child("following").child(otherUserId).setValue("true");
        reference.child(otherUserId).child("followers").child(userId).setValue("true");
    }
}