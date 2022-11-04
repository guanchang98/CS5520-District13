package com.example.district13.sticker_image;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.district13.R;

public class StickerImageViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public View view;

    public StickerImageViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;
        this.image = itemView.findViewById(R.id.imageView_punk);
    }
}
