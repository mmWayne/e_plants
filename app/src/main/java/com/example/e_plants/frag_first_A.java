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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class frag_first_A extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView textView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);


        ImageButton add = findViewById(R.id.addNewPlant);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(frag_first_A.this, new_plant_A.class);
                startActivity(intent);


            }
        });


        Button s = findViewById(R.id.search);
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new frag_forth_A().tent();
                Intent intent = new Intent(frag_first_A.this, frag_forth_A.class);
                startActivity(intent);

            }
        });

        Spinner spinner = findViewById(R.id.sp2);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this
                , R.array.plant_n, android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                Intent intent = new Intent(frag_first_A.this, frag_sixth_A.class);
                startActivity(intent);
                return true;

            case R.id.it_second:
                Intent second = new Intent(frag_first_A.this, frag_second_A.class);
                startActivity(second);
                return true;

            case R.id.it_first2:
                Intent first2 = new Intent(frag_first_A.this, com.example.e_plants.diary.class);
                startActivity(first2);
                return true;

            case R.id.it_add:
                Intent adds = new Intent(frag_first_A.this, new_plant_A.class);
                startActivity(adds);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent logOut = new Intent(frag_first_A.this, com.example.e_plants.login_A.class);
                startActivity(logOut);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        //print();
        System.out.println(text);
        new frag_forth_A().fsearch(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}