package com.example.district13;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PunkAdapter extends RecyclerView.Adapter<PunkViewHolder> {

    private final List<Punk> links;
    private final Context context;

    public PunkAdapter(List<Punk> links, Context context) {
        this.links = links;
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

    }

    @Override
    public int getItemCount() {
        // Returns the size of the recyclerview that is the list of the arraylist.
        return links.size();
    }
}


