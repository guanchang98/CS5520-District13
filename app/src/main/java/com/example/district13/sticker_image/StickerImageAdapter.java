package com.example.district13.sticker_image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.district13.R;

import java.io.File;
import java.util.List;

public class StickerImageAdapter extends RecyclerView.Adapter<StickerImageViewHolder> {
    private final List<StickerImage> images;
    private final Context context;

    public StickerImageAdapter(List<StickerImage> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @NonNull
    @Override
    public StickerImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create an instance of the view holder by passing it the layout inflated as view and no root.
        return new StickerImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sticker_image, null));
    }

    @Override
    public void onBindViewHolder(@NonNull StickerImageViewHolder holder, int position) {
        final StickerImage stickerImage = images.get(position);
        Glide.with(context)
                .load(images.get(position).getImagePath())
                .into(holder.image);
        if(stickerImage.isSelected())
            holder.view.setBackgroundColor(Color.LTGRAY);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stickerImage.setSelected(!stickerImage.isSelected());
                if(stickerImage.isSelected())
                    holder.view.setBackgroundColor(Color.LTGRAY);
            }
        });
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return images.size();
    }

}
