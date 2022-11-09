package com.example.district13.sticker_user_history;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.district13.R;

public class StickerUserHistoryViewHolder extends RecyclerView.ViewHolder {
    public ImageView sticker;
    public TextView name;
    public TextView date;

    public StickerUserHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.textView_history_name);
        this.date = itemView.findViewById(R.id.textView_history_date);
        this.sticker = itemView.findViewById(R.id.imageView_history_sticker);
    }
}
