package com.example.district13.teatalks_feed;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.district13.R;

public class FeedItemViewHolder extends RecyclerView.ViewHolder {

    public TextView poster;
    public TextView date;
    public TextView instruction;
    public ImageView avatar;
    public ImageView image;
    public ImageView likeIcon;

    public FeedItemViewHolder(@NonNull View itemView) {
        super(itemView);

        this.poster = itemView.findViewById(R.id.textView_feed_poster);
        this.date = itemView.findViewById(R.id.textView_feed_date);
        this.instruction = itemView.findViewById(R.id.textView_feed_instruction);
        this.avatar = itemView.findViewById(R.id.imageView_feed_avatar);
        this.image = itemView.findViewById(R.id.imageView_feed_image);
        this.likeIcon = itemView.findViewById(R.id.imageView_feed_item_like);
    }

}
