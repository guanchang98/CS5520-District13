package com.example.district13.teatalks_user_posts;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.district13.R;

public class UserPostsItemViewHolder extends RecyclerView.ViewHolder {
    public TextView poster;
    public TextView date;
    public TextView instruction;
    public ImageView avatar;
    public ImageView image;
    public ImageView likeIcon;
    public TextView noImageInstruction;
    public TextView likeCount;
    public TextView title;
    public TextView tags;

    public UserPostsItemViewHolder(@NonNull View itemView) {
        super(itemView);

        this.poster = itemView.findViewById(R.id.textView_feed_poster);
        this.date = itemView.findViewById(R.id.textView_feed_date);
        this.instruction = itemView.findViewById(R.id.textView_feed_instruction);
        this.avatar = itemView.findViewById(R.id.imageView_feed_avatar);
        this.image = itemView.findViewById(R.id.imageView_feed_image);
        this.likeIcon = itemView.findViewById(R.id.imageView_feed_item_like);
        this.noImageInstruction = itemView.findViewById(R.id.textView_no_image);
        this.likeCount = itemView.findViewById(R.id.textView_like_count);
        this.title = itemView.findViewById(R.id.textView_title);
        this.tags = itemView.findViewById(R.id.textView_tag);
    }
}
