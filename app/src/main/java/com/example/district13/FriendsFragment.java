package com.example.district13;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendsFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView newRecyclerView;
    AdapterUsers adapterUsers;
    AdapterUsersNew adapterUsersNew;
    List<ModelUser> followUserList;
    List<ModelUser> newUserList;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        recyclerView = view.findViewById(R.id.followUserList);
        newRecyclerView = view.findViewById(R.id.newUserList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        newRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        followUserList = new ArrayList<>();
        newUserList = new ArrayList<>();
        getAllUsers();
        return view;
    }

    private void getAllUsers() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("appUsers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                followUserList.clear();
                for (DataSnapshot ds: snapshot.getChildren()) {
                    ModelUser modelUser = ds.getValue(ModelUser.class);
                    String my_uid = modelUser.getUid();
                    String other_uid = firebaseUser.getUid();
                    if((!my_uid.equals(firebaseUser.getUid())) && snapshot.child("followers").child(other_uid).exists()){
                        followUserList.add(modelUser);
                    }
                    else if((!my_uid.equals(firebaseUser.getUid())) && !snapshot.child("followers").child(other_uid).exists()) {
                        newUserList.add(modelUser);
                    }
                    adapterUsers = new AdapterUsers(getActivity(), followUserList);
                    recyclerView.setAdapter(adapterUsers);
                    adapterUsersNew = new AdapterUsersNew(getActivity(), newUserList);
                    newRecyclerView.setAdapter(adapterUsersNew);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}