package com.example.district13.sticker_user;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.district13.R;

public class StickerUserViewHolder extends RecyclerView.ViewHolder {
    public TextView username;
    public View view;

    public StickerUserViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        this.username = itemView.findViewById(R.id.textView_sticker_user);
    }
}
