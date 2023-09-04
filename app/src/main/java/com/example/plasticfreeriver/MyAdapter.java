package com.example.plasticfreeriver;

import android.content.Context;
import android.content.Intent;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.viewHolder1>
{
    FirebaseAuth auth;
    public  boolean done;
Context context;
ArrayList<post> list;

    public MyAdapter(Context context, ArrayList<post> list, ArrayList<postWithoutstatus> withoutstatusArrayList) {
        this.context = context;
        this.list = list;
        this.withoutstatusArrayList = withoutstatusArrayList;
    }

    ArrayList<postWithoutstatus> withoutstatusArrayList;

    public MyAdapter(Context context, ArrayList<post> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.rv_feedcard,parent,false);
       return new viewHolder1(view);


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
    public void onBindViewHolder(@NonNull viewHolder1 holder, int position) {
post model=list.get(position);
        Picasso.get().load(model.getImg())
                .placeholder(R.drawable.baseline_downloading_24)
                .into(holder.binding.postImg);
       holder.binding.title.setText(list.get(position).getTitle());
       holder.binding.count.setText(list.get(position).getCount());
       // holder.binding.status.setImageResource(R.drawable.tick);
       holder.binding.postImg.setVisibility(View.VISIBLE);
        String s=list.get(position).getGeotag_url();
       holder.binding.postImg.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               openMap(Uri.parse(s));

           }
       });

if(model.isStatus())
{
    holder.binding.status.setImageResource(R.drawable.done);
    holder.binding.textStatus.setText("removed");
}

       holder.binding.status.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View view) {
               FirebaseDatabase.getInstance().getReference().child("posts").child(model.getPostId()).child("title").setValue("This has been done").addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                           holder.binding.status.setImageResource(R.drawable.done);
                            holder.binding.textStatus.setText("removed");
//FirebaseDatabase.getInstance().getReference()
//        .child("posts")
//        .child(model.getPostId())
//        .child("status").child("statuscount")
//        .setValue();
                           }
                       });
//                           public void onSuccess(Void unused) {
//                               Log.d("status","done");
//                               holder.binding.status.setImageResource(R.drawable.tick);
//                               holder.binding.textStatus.setText("removed");
//                           }
//                       });

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
    void checkstatus()
    {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class viewHolder1 extends RecyclerView.ViewHolder
    {
RvFeedcardBinding binding;
TextView title;
ImageView imgDisp;
        public viewHolder1(@NonNull View itemView) {
            super(itemView);
            binding=RvFeedcardBinding.bind(itemView);
            itemView.findViewById(R.id.title);
            itemView.findViewById(R.id.imageView);
        }
    }
}
