package com.example.e_plants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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

public class diary extends AppCompatActivity implements AdapterView.OnItemSelectedListener, ReInterface {
    RecyclerView recyclerView;
    public static  final String SHARED_PREFS="sharedPrefs";
    public static  final String TXET="text";
    private  String t;
    ArrayList<Cricketer1> list;
    DatabaseReference databaseReference;
    MyAdapter1 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);
        recyclerView=findViewById(R.id.recycleView2);
        ImageButton aDiary = findViewById(R.id.addDiary);
        aDiary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(diary.this, writediary.class);
                startActivity(intent);
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        list=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new MyAdapter1(this,list,this);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()) {
                    Cricketer1 user_dataa = ds.getValue(Cricketer1.class);
                    list.add(user_dataa);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
                Intent intent =new Intent(diary.this, frag_sixth_A.class);
                startActivity(intent);
                return true;

            case R.id.it_second:
                Intent second =new Intent(diary.this, frag_second_A.class);
                startActivity(second);
                return true;

            case R.id.it_first2:
                Intent first2 =new Intent(diary.this, diary.class);
                startActivity(first2);
                return true;

            case R.id.it_add:
                Intent adds =new Intent(diary.this, new_plant_A.class);
                startActivity(adds);
                return true;

            case R.id.it_first:
                Intent first =new Intent(diary.this, frag_first_A.class);
                startActivity(first);
                return true;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent logOut = new Intent(diary.this, login_A.class);
                startActivity(logOut);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick1(int position) {
        Intent intent=new Intent(com.example.e_plants.diary.this,writediary.class);
        startActivity(intent);
    }
}


