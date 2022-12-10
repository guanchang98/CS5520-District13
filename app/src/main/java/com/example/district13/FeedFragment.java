package com.example.district13;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.district13.teatalks_feed.FeedItem;
import com.example.district13.teatalks_feed.FeedItemAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    
    private List<FeedItem> feedItemList;
    private RecyclerView feedItemRecyclerView;
    private Set<String> followingIDs;
    private List<String> followingPostIds;
    private List<String> recommendPostIds;

    View view;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_feed, container, false);

        // Initialize firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        Query query = database.getReference("appUsers").child(user.getUid());

        followingIDs = new HashSet<>();
        followingPostIds = new ArrayList<>();
        feedItemList = new ArrayList<>();
        recommendPostIds = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedItemList.clear();
                for (DataSnapshot dataSnapshot : snapshot.child("following").getChildren()) {
                    followingIDs.add(dataSnapshot.getKey());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        CollectFollowingAndRecentPosts(followingIDs);

        return view;
    }

    private void CollectFollowingAndRecentPosts(Set<String> followingIDs) {
        Query query = database.getReference("Posts").orderByKey().limitToLast(100);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedItemList.clear();
                followingPostIds.clear();
                recommendPostIds.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (followingIDs.contains((String) dataSnapshot.child("uid").getValue())) {
                        Log.v("CollectFollowingPosts", "Following post is: " + dataSnapshot.getKey().toString());
                        followingPostIds.add((String) dataSnapshot.getKey());
                    }
                }
                Set<String> existedPosts = new HashSet<>(followingPostIds);

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Log.v("CollectRecentPosts", "Current post is: " + dataSnapshot.getKey());
                    if (!existedPosts.contains(dataSnapshot.getKey()) && !dataSnapshot.child("uid").getValue().equals(user.getUid()))
                        recommendPostIds.add(dataSnapshot.getKey());
                }


                for (String id : followingPostIds) {
//                            Log.v("Following post id:", "current post id: " + id);
                    DataSnapshot dataSnapshot = snapshot.child(id);
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
                    feedItemList.add(new FeedItem(
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

//                        Log.v("Feed Item List size: ", "Feed Item list size: " + feedItemList.size());

                for (String id : recommendPostIds) {
//                            Log.v("Recommend post id: ", "current post id: " + id);
                    DataSnapshot dataSnapshot = snapshot.child(id);
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
                    feedItemList.add(new FeedItem(
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
                            false));
                }

                feedItemRecyclerView = view.findViewById(R.id.recyclerView_feed);
                feedItemRecyclerView.setHasFixedSize(true);
                feedItemRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
                feedItemRecyclerView.setAdapter(new FeedItemAdapter(feedItemList, getContext()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}