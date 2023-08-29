package com.example.plasticfreeriver;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    void openMap(Uri uri)
    {
        Intent imap=new Intent(Intent.ACTION_VIEW,uri);
        imap.setPackage("com.google.android.apps.maps");

        //com.google.android.apps.maps
        //
        if (imap.resolveActivity(context.getPackageManager()) != null) {
            Toast.makeText(context, "Could not find google map on your device", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            //Toast.makeText(getContext(), "locating", Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
            //    }
        }
        // startActivity(imap);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.viewHolder holder, int position) {
post model=list.get(position);
        Picasso.get().load(model.getImg())
                .placeholder(R.drawable.baseline_downloading_24)
                .into(holder.binding.postImg);
       holder.binding.title.setText(list.get(position).getTitle());
       holder.binding.postImg.setVisibility(View.VISIBLE);
        String s=list.get(position).getGeotag_url();
       holder.binding.postImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               openMap(Uri.parse(s));

           }
       });
       holder.binding.status.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Toast.makeText(context, "woah", Toast.LENGTH_SHORT).show();
// Retrieve the drawable resource using the resource identifier
               //holder.binding.status.setImageDrawable(Resources.getSystem().getDrawable(R.drawable.tick));


           }
       });
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
