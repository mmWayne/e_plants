package com.example.e_plants;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final RecycleviewInterface recycleviewInterface;
    Context context;
    ArrayList<Cricketer> list;

    public MyAdapter(Context context, ArrayList<Cricketer> list, RecycleviewInterface recycleviewInterface ) {

        this.context = context;
        this.list = list;
        this.recycleviewInterface = recycleviewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v,recycleviewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Cricketer cricketer=list.get(position);
        holder.plant.setText(cricketer.getPlantName());
        holder.date.setText(cricketer.getDateName());
        holder.annothername.setText(cricketer.getExtraName());
        holder.textOption.setOnClickListener(v->{
            PopupMenu  popupMenu=new PopupMenu(context, holder.textOption);
            popupMenu.inflate(R.menu.option_menu);
            popupMenu.setOnMenuItemClickListener(item->{
                switch (item.getItemId())
                {
                    case R.id.menu_edit:
                        Intent intent = new Intent(context, new_plant_A.class);
                        intent.putExtra("EDIT",cricketer);
                        context.startActivity(intent);
                        break;
                    case R.id.menu_remove:
                        DAOEmployee dao=new DAOEmployee();
                        dao.remove(cricketer.getKey()).addOnSuccessListener(suc->{
                            Toast.makeText(context, "record is removed", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                        }).addOnFailureListener(er->{
                            Toast.makeText(context, er.getMessage(), Toast.LENGTH_SHORT).show();

                        });
                        break;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        public Object text_option;
        TextView plant,date,annothername,textOption;
        public MyViewHolder(@NonNull View itemView,RecycleviewInterface recycleviewInterface) {
            super(itemView);
            plant=itemView.findViewById(R.id.textName);
            date=itemView.findViewById(R.id.textDate);
            annothername=itemView.findViewById(R.id.extraName);
            textOption=itemView.findViewById(R.id.text_option);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recycleviewInterface!=null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            recycleviewInterface.onItemClick(pos);

                        }
                    }
                }
            });
        }
    }
}
