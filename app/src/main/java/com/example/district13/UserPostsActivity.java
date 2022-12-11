package com.example.district13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.district13.teatalks_user_posts.UserPostsItem;
import com.example.district13.teatalks_user_posts.UserPostsItemAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserPostsActivity extends AppCompatActivity {
    private List<UserPostsItem> userPostsList;
    private RecyclerView userPostsRecyclerView;
    private List<String> userPostsIds;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_posts);

        //init lists
        userPostsList = new ArrayList<>();
        userPostsIds = new ArrayList<>();

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        Query query = database.getReference("appUsers").child(user.getUid());

        //get user's posts ids and add them to list
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userPostsList.clear();
                for (DataSnapshot dataSnapshot: snapshot.child("posts").getChildren()) {
                    Log.v("User Posts", "Posts Ids is " + dataSnapshot.getKey().toString());
                    userPostsIds.add(dataSnapshot.getKey());
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
                userPostsList.clear();

                //add posts found in database to the recyclerview
                for (String id : userPostsIds) {
                    Log.v("User Posts Id", "current post id: " + id);
                    DataSnapshot dataSnapshot = snapshot.child(id);
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
                    userPostsList.add(new UserPostsItem(
                            id, (String)dataSnapshot.child("uid").getValue(),
                            (String)dataSnapshot.child("uName").getValue(),
                            sf.format(new Date(Long.parseLong((String)dataSnapshot.child("pID").getValue()))),
                            (String)dataSnapshot.child("pImage").getValue(),
                            (String)dataSnapshot.child("uAvatar").getValue(),
                            (String)dataSnapshot.child("pContent").getValue(),
                            dataSnapshot.child("pLikes").child(user.getUid()).exists(),
                            (String)dataSnapshot.child("pLikeCount").getValue(),
                            (String)dataSnapshot.child("pTitle").getValue(),
                            (String)dataSnapshot.child("pTags").getValue(),
                            true));
                }
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(UserPostsActivity.this);
                userPostsRecyclerView = findViewById(R.id.recyclerView_user_posts);
                userPostsRecyclerView.setHasFixedSize(true);
                userPostsRecyclerView.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(userPostsRecyclerView.getContext(),
                ((LinearLayoutManager) layoutManager).getOrientation());
                userPostsRecyclerView.addItemDecoration(dividerItemDecoration);
                userPostsRecyclerView.setAdapter(new UserPostsItemAdapter(userPostsList, UserPostsActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}