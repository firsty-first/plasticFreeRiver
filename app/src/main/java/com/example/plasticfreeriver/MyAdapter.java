package com.example.plasticfreeriver;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.viewHolder>
{
    ArrayList<post> list;
    Context context;


public MyAdapter(ArrayList<post> list, Context context)
{
    this.list=list;
    this.context=context;
}
    @NonNull
    @Override
    public MyAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.rv_feedcard,parent,false);
       viewHolder vh=new viewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.viewHolder holder, int position) {
post model=list.get(position);
    Picasso.get()
            .load(model.getImg())
            .placeholder(R.drawable.background)
            .into(holder.imageView);
    holder.textView.setText(list.get(position).getPostId());
holder.imageView.setVisibility(View.VISIBLE);
holder.itemView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{


        public ImageView imageView;

        public TextView textView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.postImg);
            textView=itemView.findViewById(R.id.title);
        }
    }
}
