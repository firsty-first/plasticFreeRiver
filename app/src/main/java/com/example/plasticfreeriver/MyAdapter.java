package com.example.plasticfreeriver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plasticfreeriver.databinding.RvFeedcardBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.viewHolder>
{
Context context;
ArrayList<post> list;

    public MyAdapter(Context context, ArrayList<post> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.rv_feedcard,parent,false);
       return new viewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.viewHolder holder, int position) {
post model=list.get(position);
        Picasso.get().load(model.getImg())
                .placeholder(R.drawable.tick)
                .into(holder.binding.postImg);
       holder.binding.title.setText(list.get(position).getTitle());
       holder.binding.postImg.setVisibility(View.VISIBLE);
        //User user=list.get(position);

//        FirebaseDatabase.getInstance().getReference().child("posts")
//                        .addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                User user=snapshot.getValue(User.class)
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });

//        holder.binding.title.setText(model.getTitle());
//        holder.binding.userName.setText(model.getPostedBy());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder
    {
RvFeedcardBinding binding;
TextView title;
ImageView imgDisp;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=RvFeedcardBinding.bind(itemView);
            itemView.findViewById(R.id.title);
            itemView.findViewById(R.id.imageView);
        }
    }
}
