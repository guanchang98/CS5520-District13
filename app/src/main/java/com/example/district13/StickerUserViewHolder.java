package com.example.district13;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StickerUserViewHolder extends RecyclerView.ViewHolder {
    public TextView username;
    public View view;

    public StickerUserViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        this.username = itemView.findViewById(R.id.textView_sticker_user);
    }
}
