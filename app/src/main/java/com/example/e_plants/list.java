package com.example.e_plants;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class list  extends AppCompatActivity implements RecycleviewInterface {

    RecyclerView recyclerView;
    ArrayList<Cricketer> alist;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(list.this,new_plant_A.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ulist);
        recyclerView=findViewById(R.id.recycleView);
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        alist=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(this,alist,this);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()) {
                    Cricketer user_data = ds.getValue(Cricketer.class);
                    alist.add(user_data);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(list.this,diary.class);
        startActivity(intent);
    }
    //*/

}
