package com.example.e_plants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final RecycleviewInterface recycleviewInterface;
    Context context;
    ArrayList<Cricketer> list;

    public MyAdapter(Context context, ArrayList<Cricketer> list, RecycleviewInterface recycleviewInterface) {

        this.context = context;
        this.list = list;
        this.recycleviewInterface = recycleviewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry, parent, false);
        return new MyViewHolder(v, recycleviewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cricketer cricketer = list.get(position);
        holder.plant.setText(cricketer.getPlantName());
        holder.date.setText(cricketer.getDateName());
        holder.annothername.setText(cricketer.getExtraName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView plant, date, annothername;

        public MyViewHolder(@NonNull View itemView, RecycleviewInterface recycleviewInterface) {
            super(itemView);
            plant = itemView.findViewById(R.id.textName);
            date = itemView.findViewById(R.id.textDate);
            annothername = itemView.findViewById(R.id.extraName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recycleviewInterface != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recycleviewInterface.onItemClick(pos);

                        }
                    }
                }
            });
        }
    }
}
