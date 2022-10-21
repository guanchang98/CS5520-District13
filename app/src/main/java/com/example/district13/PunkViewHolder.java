package com.example.district13;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PunkViewHolder extends RecyclerView.ViewHolder {

    public TextView name;
    public TextView first_brewed;

    public PunkViewHolder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.punk_name);
        this.first_brewed = itemView.findViewById(R.id.punk_first_brewed);
    }
}
