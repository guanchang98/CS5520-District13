package com.example.district13.sticker_user_history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.district13.R;
import com.example.district13.sticker_image.StickerImage;
import com.example.district13.sticker_image.StickerImageViewHolder;
import com.example.district13.sticker_user.StickerUser;

import java.util.List;


public class StickerUserHistoryAdapter extends RecyclerView.Adapter<StickerUserHistoryViewHolder>{
    private final List<StickerUserHistory> userHistories;
    private final Context context;

    @NonNull
    @Override
    public StickerUserHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StickerUserHistoryViewHolder(LayoutInflater.from(context).inflate(R.layout.item_history, null));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerUserHistoryViewHolder holder, int position) {

        final StickerUserHistory stickerUserHistory = userHistories.get(position);
        Glide.with(context)
                .load(userHistories.get(position).getStickerURL())
                .into(holder.sticker);
        holder.name.setText(userHistories.get(position).getUserName());
        holder.date.setText(userHistories.get(position).getDate());
    }

    public StickerUserHistoryAdapter(List<StickerUserHistory> userHistories, Context context) {
        this.userHistories = userHistories;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return userHistories.size();
    }
}
