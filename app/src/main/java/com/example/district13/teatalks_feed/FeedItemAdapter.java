package com.example.district13.teatalks_feed;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.district13.R;
import com.example.district13.sticker_user.StickerUser;

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

    @Override
    public void onBindViewHolder(@NonNull FeedItemViewHolder holder, int position) {
        holder.poster.setText(feedItems.get(position).getPoster());
        holder.date.setText(feedItems.get(position).getDate());
        holder.instruction.setText(feedItems.get(position).getInstruction());
        // Handle image
        Glide.with(context)
                .load(feedItems.get(position).getAvatarURL())
                .into(holder.avatar);
        Glide.with(context)
                .load(feedItems.get(position).getImageURL())
                .into(holder.image);
        final FeedItem feedItem = feedItems.get(position);
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
                } else {
                    Glide.with(context)
                            .load(R.drawable.ic_unlike)
                            .into(holder.likeIcon);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return feedItems.size();
    }
}
