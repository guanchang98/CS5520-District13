package com.example.district13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.district13.teatalks_feed.FeedItem;
import com.example.district13.teatalks_feed.FeedItemAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OthersPostsActivity extends AppCompatActivity {
    private List<FeedItem> othersPostsList;
    private RecyclerView othersPostsRecyclerView;
    private List<String> othersPostsIds;

    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_posts);

        Intent intent = getIntent();
        String othersId = intent.getStringExtra("OtherUserId");
        Log.v("OtherUserId", othersId);

        //init lists
        othersPostsList = new ArrayList<>();
        othersPostsIds = new ArrayList<>();

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        Query query = database.getReference("appUsers").child(othersId);

        //get user's posts ids and add them to list
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                othersPostsList.clear();
                for (DataSnapshot dataSnapshot: snapshot.child("posts").getChildren()) {
                    Log.v("User Posts", "Posts Ids is " + dataSnapshot.getKey().toString());
                    othersPostsIds.add(dataSnapshot.getKey());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //match posts ids in list with posts in database
        Query query1 = database.getReference("Posts").orderByKey().limitToLast(100);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                othersPostsList.clear();

                //add posts found in database to the recyclerview
                //reverse lists so the newest is at top
                Collections.reverse(othersPostsIds);
                for (String id : othersPostsIds) {
                    Log.v("User Posts Id", "current post id: " + id);
                    DataSnapshot dataSnapshot = snapshot.child(id);
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
                    othersPostsList.add(new FeedItem(
                            id, (String)dataSnapshot.child("uid").getValue(),
                            (String)dataSnapshot.child("uName").getValue(),
                            sf.format(new Date(Long.parseLong((String)dataSnapshot.child("pID").getValue()))),
                            (String)dataSnapshot.child("pImage").getValue(),
                            (String)dataSnapshot.child("uAvatar").getValue(),
                            (String)dataSnapshot.child("pContent").getValue(),
                            dataSnapshot.child("pLikes").child(id).exists(),
                            (String)dataSnapshot.child("pLikeCount").getValue(),
                            (String)dataSnapshot.child("pTitle").getValue(),
                            (String)dataSnapshot.child("pTags").getValue(),
                            true));
                }
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(OthersPostsActivity.this);
                othersPostsRecyclerView = findViewById(R.id.recyclerView_others_posts);
                othersPostsRecyclerView.setHasFixedSize(true);
                othersPostsRecyclerView.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(othersPostsRecyclerView.getContext(),
                        ((LinearLayoutManager) layoutManager).getOrientation());
                othersPostsRecyclerView.addItemDecoration(dividerItemDecoration);
                othersPostsRecyclerView.setAdapter(new FeedItemAdapter(othersPostsList, OthersPostsActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}