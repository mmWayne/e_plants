package com.example.e_plants;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import  com.example.e_plants.frag_forth_A;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class frag_first_A extends AppCompatActivity implements AdapterView.OnItemSelectedListener, RecycleviewInterface {
    TextView textView;
    RecyclerView recyclerView;
    ArrayList<Cricketer> list=new ArrayList<>();
    DatabaseReference databaseReference;
    MyAdapter adapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.li );
        recyclerView=findViewById(R.id.recycleView1);
        //databaseReference= FirebaseDatabase.getInstance().getReference().child("users");
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter(this,list,this);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds:snapshot.getChildren()) {
                    Cricketer user_data = ds.getValue(Cricketer.class);
                    user_data.setKey(ds.getKey());
                    list.add(user_data);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        ImageButton add = findViewById(R.id.addNewPlant1);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(frag_first_A.this, new_plant_A.class);
                startActivity(intent);
            }
        });
        Button s = findViewById(R.id.search1);
        s.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //new frag_forth_A().tent();
                Intent intent = new Intent(frag_first_A.this, frag_forth_A.class);
                startActivity(intent);
            }
        });
        //Button a = findViewById(R.id.btn_already);
        //a.setOnClickListener(new View.OnClickListener() {
          //  @Override
          //  public void onClick(View v) {
                //Intent intent = new Intent(frag_first_A.this, com.example.e_plants.list.class);
                //startActivity(intent);
            //}
        //});
        Spinner spinner = findViewById(R.id.sp3);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this
                ,R.array.plant_n,android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Intent intent =new Intent(frag_first_A.this, frag_sixth_A.class);
                startActivity(intent);
                return true;

            case R.id.it_second:
                Intent second =new Intent(frag_first_A.this, frag_second_A.class);
                startActivity(second);
                return true;

            case R.id.it_first2:
                Intent first2 =new Intent(frag_first_A.this, com.example.e_plants.diary.class);
                startActivity(first2);
                return true;

            case R.id.it_add:
                Intent adds =new Intent(frag_first_A.this, new_plant_A.class);
                startActivity(adds);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent logOut = new Intent(frag_first_A.this,com.example.e_plants.login_A.class);
                startActivity(logOut);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text=adapterView.getItemAtPosition(i).toString();
        //print();
        System.out.println(text);
        new frag_forth_A().fsearch(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(com.example.e_plants.frag_first_A.this,diary.class);
        startActivity(intent);
    }
    /*
    class uu  extends AppCompatActivity implements RecycleviewInterface {
        RecyclerView recyclerView;
        ArrayList<Cricketer> list;
        DatabaseReference databaseReference;
        MyAdapter adapter;
    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(list.this,new_plant_A.class));
        finish();
    }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.li);
            recyclerView=findViewById(R.id.recycleView1);
            databaseReference= FirebaseDatabase.getInstance().getReference("users");
            list=new ArrayList<>();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter=new MyAdapter(this,list,this);
            recyclerView.setAdapter(adapter);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds:snapshot.getChildren()) {
                        Cricketer user_data = ds.getValue(Cricketer.class);
                        list.add(user_data);
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
            Intent intent=new Intent(com.example.e_plants.frag_first_A.this,diary.class);
            startActivity(intent);
        }
    }

*/
}

