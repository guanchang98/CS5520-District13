package com.example.district13;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PostFragment extends Fragment {

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_post, container, false);
        Button anotherPost = (Button) rootView.findViewById(R.id.anotherPostButton);
        Button backToFeed = (Button) rootView.findViewById(R.id.feedButton);
        context = rootView.getContext();
        Intent intent = new Intent(context, PostActivity.class);
        Log.d("PostFragment", "Starting post activity");
        startActivity(intent);
        anotherPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostActivity.class);
                Log.d("PostFragment", "Starting post activity");
                startActivity(intent);
            }
        });
        backToFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FeedFragment.class);
                Log.d("PostFragment", "Starting feed fragment");
                startActivity(intent);
            }
        });

        return rootView;
    }
}