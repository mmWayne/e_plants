package com.example.e_plants;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder> {
    private final ReInterface recycleviewInterface;
    Context context;
    ArrayList<Cricketer1> list;

    public MyAdapter1(Context context, ArrayList<Cricketer1> list, ReInterface recycleviewInterface ) {

        this.context = context;
        this.list = list;
        this.recycleviewInterface = recycleviewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.userentry1,parent,false);
        return new MyViewHolder(v,recycleviewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cricketer1 cricketer=list.get(position);
        holder.date.setText(cricketer.getAddDate());
        holder.content.setText(cricketer.getDiaryContent());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView date,content;
        public MyViewHolder(@NonNull View itemView,ReInterface recycleviewInterface) {
            super(itemView);
            date=itemView.findViewById(R.id.textAddDate);
            content=itemView.findViewById(R.id.addContent);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recycleviewInterface!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            recycleviewInterface.onItemClick1(pos);

                        }
                    }
                }
            });
        }
    }
}
