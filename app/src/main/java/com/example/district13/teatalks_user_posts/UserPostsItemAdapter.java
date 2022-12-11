package com.example.district13.teatalks_user_posts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.district13.R;
import com.example.district13.UserPostsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserPostsItemAdapter extends RecyclerView.Adapter<UserPostsItemViewHolder> {
    private final List<UserPostsItem> userPostsItems;
    private final Context context;

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference postRef;

    public UserPostsItemAdapter(List<UserPostsItem> userPostsItem, Context context) {
        this.userPostsItems = userPostsItem;
        this.context = context;
    }

    @NonNull
    @Override
    public UserPostsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserPostsItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_feed,null));
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostsItemViewHolder holder, int position) {
        final UserPostsItem userPostsItem = userPostsItems.get(position);

        // Initialize firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();

        //show number of likes
        postRef = database.getReference("Posts").child(userPostsItem.getPostID()).child("pLikes");
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

        //show post info
        holder.poster.setText(userPostsItem.getPoster());
        holder.date.setText(userPostsItem.getDate());
        holder.title.setText(userPostsItem.getPostTitle());
        if (userPostsItem.getPostTags() != null) holder.tags.setText("Tags: " + userPostsItem.getPostTags());
        if (userPostsItem.getAvatarURL() != null) {
            Glide.with(context)
                    .load(userPostsItem.getAvatarURL())
                    .into(holder.avatar);
        } else {
            Glide.with(context)
                    .load(R.drawable.ic_default_img)
                    .into(holder.avatar);
        }
        if (userPostsItem.getImageURL().equals("noImage")) {
            holder.noImageInstruction.setText(userPostsItem.getInstruction());
            holder.instruction.setVisibility(View.GONE);
            holder.image.setVisibility(View.GONE);
        } else {
            holder.instruction.setText(userPostsItem.getInstruction());
            // Handle image
            Glide.with(context)
                    .load(userPostsItem.getImageURL())
                    .into(holder.image);
        }
        //always show like icon
        Glide.with(context)
                .load(R.drawable.ic_like)
                .into(holder.likeIcon);
    }

    @Override
    public int getItemCount() {
        return userPostsItems.size();
    }
}
