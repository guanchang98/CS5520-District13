package com.example.district13;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    private final String TAG = "AccountFragment";
    //firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    //views
    ImageView avatarIv;
    TextView userEmailTv, userNameTv, userPostsTv, userFollowingTv, userFollowersTv;

    //buttons
    Button logOut;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //init firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("appUsers");

        //init views
        avatarIv = view.findViewById(R.id.user_selfie);
        userEmailTv = view.findViewById(R.id.user_email);
        userNameTv = view.findViewById(R.id.user_name);
        userPostsTv = view.findViewById(R.id.user_posts);
        userFollowingTv = view.findViewById(R.id.user_following);
        userFollowersTv = view.findViewById(R.id.user_followers);

        //Get user data from database
        Query query = reference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    //get data
                    String name = "" + dataSnapshot.child("name").getValue();
                    String email = "" + dataSnapshot.child("email").getValue();
                    String avatar = "" + dataSnapshot.child("avatar").getValue();
                    String posts = "" + dataSnapshot.child("posts").getValue();
                    String following = "" + dataSnapshot.child("following").getValue();
                    String followers = "" + dataSnapshot.child("followers").getValue();

                    //Log.w(TAG, name);

                    //set data
                    userEmailTv.setText(email);
                    userNameTv.setText("Welcome\n" + name);
                    userPostsTv.setText(posts + "\nPosts");
                    userFollowingTv.setText(following + "\nFollowing");
                    userFollowersTv.setText(followers + "\nFollowers");
                    try {
                        //load avatar if set
                        Picasso.get().load(avatar).into(avatarIv);
                    } catch (Exception e) {
                        //otherwise load default
                        Picasso.get().load(R.drawable.ic_add_user_selfie).into(avatarIv);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //init button
        logOut = view.findViewById(R.id.user_logout);

        //handle logout button
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
//                checkUserStatus();
                startActivity(new Intent(getActivity(), TeaTalksLoginActivity.class));
            }
        });
        return view;
    }

//    public void checkUserStatus() {
//
//        if (user != null) {
//            //user is signed in
//            userNameTv.setText("Welcome\n" + user.getEmail());
//        } else {
//            //user not signed in, go to tea_talks_login activity
//            startActivity(new Intent(getActivity(), TeaTalksLoginActivity.class));
//        }
//    }
//
//    @Override
//    public void onStart() {
//        checkUserStatus();
//        super.onStart();
//    }
}