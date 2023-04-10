package com.example.patienttrackingsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.MyViewHolder> {
    Context context;
    ArrayList<SpecialtyModel> specialtyModels;

    public void setSearchList(ArrayList<SpecialtyModel> specialtyModels) {
        this.specialtyModels = specialtyModels;
        notifyDataSetChanged();
    }


    public SpecialtyAdapter(Context context, ArrayList<SpecialtyModel> specialtyModels) {
        this.context = context;
        this.specialtyModels = specialtyModels;

        //Toast.makeText(context, "Size Sent: "+specialtyModels.size(), Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public SpecialtyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This is where you inflate the layout (Giving a loom to our rows)

        View view = LayoutInflater.from(context).inflate(R.layout.specialty_row, parent, false);

        return new SpecialtyAdapter.MyViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Assigning values to the views we created in the recycler_view_row layout
        //based on the position of hte recycler view

        //holder.tvId.setText(specialtyModels.get(position).getId()+"");
        holder.tvId.setText(position+1+"");
        holder.tvName.setText(specialtyModels.get(position).getName());
        holder.tvDesc.setText(specialtyModels.get(position).getDesc());

        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,BookDoctor.class);
                intent.putExtra("ID",specialtyModels.get(holder.getAdapterPosition()).getId());
                intent.putExtra("Name",specialtyModels.get(holder.getAdapterPosition()).getName());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        // The Recycler view just wants to know how many number of items you want to be displayed
        //Toast.makeText(context, "Items Count: "+specialtyModels.size(), Toast.LENGTH_SHORT).show();

        return specialtyModels.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //Grabbing the views from our recycler_view_row layout file
        //Kinda like in the onCreate Method

        TextView tvId, tvName, tvDesc;
        ConstraintLayout row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.txt_id);
            tvName = itemView.findViewById(R.id.textViewName);
            tvDesc = itemView.findViewById(R.id.textViewDesc);

            row = itemView.findViewById(R.id.row);


        }
    }

}
