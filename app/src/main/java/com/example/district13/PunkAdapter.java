package com.example.district13;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PunkAdapter extends RecyclerView.Adapter<PunkViewHolder> {

    private final List<Punk> punks;
    private final Context context;

    public PunkAdapter(List<Punk> punks, Context context) {
        this.punks = punks;
        this.context = context;
    }

    @NonNull
    @Override
    public PunkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create an instance of the viewholder by passing it the layout inflated as view and no root.
        return new PunkViewHolder(LayoutInflater.from(context).inflate(R.layout.item_punk, null));
    }

    @Override
    public void onBindViewHolder(@NonNull PunkViewHolder holder, int position) {
        holder.name.setText("Name: " + punks.get(position).getName());
        holder.tagline.setText("Tag: " + punks.get(position).getTagline());
        holder.first_brewed.setText("Year: " + punks.get(position).getFirst_brewed());
        holder.description.setText("Description: " + punks.get(position).getDescription());
        // Handle image
        Glide.with(context)
                .load(punks.get(position).getImage_url())
                .into(holder.image_url);
    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return punks.size();
    }

}


