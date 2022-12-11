package com.example.district13.teatalks_feed;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.district13.OthersAccountActivity;
import com.example.district13.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FeedItemAdapter extends RecyclerView.Adapter<FeedItemViewHolder> {
    private final List<FeedItem> feedItems;
    private final Context context;

    public FeedItemAdapter(List<FeedItem> feedItems, Context context) {
        this.feedItems = feedItems;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create an instance of the view holder by passing it the layout inflated as view and no root.
        return new FeedItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_feed, null));
    }


    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference postRef;

    @Override
    public void onBindViewHolder(@NonNull FeedItemViewHolder holder, int position) {
        final FeedItem feedItem = feedItems.get(position);

        // Initialize firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        postRef = database.getReference("Posts").child(feedItem.getPostID()).child("pLikes");
        postRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.v("Post likes count", "post likes count " + snapshot.getChildrenCount());
                holder.likeCount.setText(String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        if (feedItem.isFollowing()) {
            if (feedItem.getPoster() == null || feedItem.getPoster().equals("")) {
                holder.poster.setText("Anonymous\n(Following)");
            } else {
                holder.poster.setText(feedItem.getPoster() + "\n(Following)");
            }
        } else {
            if (feedItem.getPoster() == null) {
                holder.poster.setText("Anonymous\n(Recommended)");
            } else {
                holder.poster.setText(feedItem.getPoster() + "\n(Recommended)");
            }
        }
        holder.date.setText(feedItem.getDate());

        holder.title.setText(feedItem.getPostTitle());
        if (feedItem.getPostTags() != null) holder.tags.setText("Tags: " + feedItem.getPostTags());
        if (feedItem.getAvatarURL() != null && !feedItem.getAvatarURL().equals("")) {
            Glide.with(context)
                    .load(feedItem.getAvatarURL())
                    .into(holder.avatar);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_default_img)
                    .into(holder.avatar);
        }
        if (feedItem.getImageURL().equals("noImage")) {
            holder.noImageInstruction.setText(feedItem.getInstruction());
            holder.instruction.setVisibility(View.GONE);
            holder.image.setVisibility(View.GONE);
        } else {
            holder.instruction.setText(feedItem.getInstruction());
            // Handle image
            Glide.with(context)
                    .load(feedItem.getImageURL())
                    .into(holder.image);
        }

        
        if(feedItem.isLike()) {
            Glide.with(context)
                    .load(R.drawable.ic_like)
                    .into(holder.likeIcon);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_unlike)
                    .into(holder.likeIcon);
        }

        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedItem.setLike(!feedItem.isLike());
                if(feedItem.isLike()) {
                    Glide.with(context)
                            .load(R.drawable.ic_like)
                            .into(holder.likeIcon);
                    postRef.child(user.getUid()).setValue("true");
                } else {
                    Glide.with(context)
                            .load(R.drawable.ic_unlike)
                            .into(holder.likeIcon);
                    postRef.child(user.getUid()).removeValue();
                }
                postRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        holder.likeCount.setText(String.valueOf(snapshot.getChildrenCount()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent;
                intent =  new Intent(context, OthersAccountActivity.class);
                intent.putExtra("PosterID", feedItem.getId());
                context.startActivity(intent);
            }
        });

        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent;
                intent =  new Intent(context, OthersAccountActivity.class);
                intent.putExtra("PosterID", feedItem.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return feedItems.size();
    }
}
