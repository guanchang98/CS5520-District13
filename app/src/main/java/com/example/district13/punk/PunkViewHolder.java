package com.example.district13.punk;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.district13.R;

public class PunkViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView tagline;
    public TextView first_brewed;
    public TextView description;
    public ImageView image_url;

    public PunkViewHolder(@NonNull View itemView) {
        super(itemView);

        this.name = itemView.findViewById(R.id.textView_history_name);
        this.tagline = itemView.findViewById(R.id.textView_history_date);
        this.first_brewed = itemView.findViewById(R.id.textView_punk_line3);
        this.description = itemView.findViewById(R.id.textView_punk_line4);
        this.image_url = itemView.findViewById(R.id.imageView_history_sticker);
    }


}
