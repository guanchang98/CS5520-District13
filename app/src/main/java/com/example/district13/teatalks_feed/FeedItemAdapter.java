package com.example.district13.teatalks_feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.district13.R;

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
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return feedItems.size();
    }
}
