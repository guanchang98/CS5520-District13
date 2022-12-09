package com.example.district13;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterUsers extends RecyclerView.Adapter<AdapterUsers.MyHolder>{

    Context context;
    List<ModelUser> userList;

    public AdapterUsers(Context context, List<ModelUser> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Nullable
    @androidx.annotation.NonNull
    @Override
    public MyHolder onCreateViewHolder(@Nullable @androidx.annotation.NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_users, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@Nullable @androidx.annotation.NonNull MyHolder myHolder, int i) {
        String avatar = userList.get(i).getAvatar();
        String name = userList.get(i).getName();
        String email = userList.get(i).getEmail();

        myHolder.name.setText(name);
        myHolder.email.setText(email);
        try {
            Picasso.get().load(avatar).placeholder(R.drawable.ic_default_img).into(myHolder.avatar);
        } catch (Exception e) {

        }
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ""+email, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name, email;

        public MyHolder(@Nullable @androidx.annotation.NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
        }
    }
}
