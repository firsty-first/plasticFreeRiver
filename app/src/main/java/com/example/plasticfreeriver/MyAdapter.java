package com.example.plasticfreeriver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plasticfreeriver.databinding.RvFeedcardBinding;

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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder
    {
RvFeedcardBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=RvFeedcardBinding.bind(itemView);


        }
    }
}
