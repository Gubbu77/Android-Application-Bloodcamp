package com.example.bloodcamp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<blooddonars> list;


    public MyAdapter(Context context, ArrayList<blooddonars> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_layout,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        blooddonars user = list.get(position);
        holder.dbname.setText(user.getName());
        holder.dbage.setText(user.getAge());
        holder.dbphone.setText(user.getPhone());
        holder.dbgroup.setText(user.getBloodgroup());
        holder.dbaddress.setText(user.getAddress());
        holder.dbdate.setText(user.getDate());


    }

    @Override
    public int getItemCount() {

        return list.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dbname, dbage, dbphone, dbgroup, dbaddress, dbdate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dbname = itemView.findViewById(R.id.db_bld_donorname);
            dbage = itemView.findViewById(R.id.db_bld_donorage);
            dbphone = itemView.findViewById(R.id.db_bld_donorphone);
            dbgroup = itemView.findViewById(R.id.db_bld_donorbldgroup);
            dbaddress = itemView.findViewById(R.id.db_bld_donoraddress);
            dbdate = itemView.findViewById(R.id.db_bld_donordate);

        }
    }

}