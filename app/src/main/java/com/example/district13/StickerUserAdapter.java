package com.example.district13;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class StickerUserAdapter extends RecyclerView.Adapter<StickerUserViewHolder> {

    private final List<StickerUser> users;
    private final Context context;

    public StickerUserAdapter(List<StickerUser> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public StickerUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create an instance of the view holder by passing it the layout inflated as view and no root.
        return new StickerUserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sticker_user, null));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerUserViewHolder holder, int position) {
        final StickerUser user = users.get(position);
        holder.username.setText(users.get(position).getUsername());
        holder.view.setBackgroundColor(user.isSelected() ? Color.YELLOW : Color.WHITE);
        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setSelected(!user.isSelected());
                holder.view.setBackgroundColor(user.isSelected() ? Color.YELLOW : Color.WHITE);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return users.size();
    }

}
