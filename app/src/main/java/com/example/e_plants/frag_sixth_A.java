package com.example.e_plants;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class frag_sixth_A extends AppCompatActivity {
    Switch st_notice;
    Switch st_water;
    Switch st_bug;
    ImageButton IB2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sixth);

        st_notice = findViewById(R.id.st_notice);
        st_bug = findViewById(R.id.st_bug);
        st_water = findViewById(R.id.st_water);
        IB2 = findViewById(R.id.imageButton2);

        st_notice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    st_water.setEnabled(true);
                    st_bug.setEnabled(true);
                }else{
                    st_water.setEnabled(false);
                    st_bug.setEnabled(false);
                }
            }
        });
        /**
        st_water.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                }else{
                }
            }
        });
        st_bug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                }else{
                }
            }
        });
         **/

        IB2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(frag_sixth_A.this, com.example.e_plants.frag_sixth2_A.class);
                startActivity(intent);
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

            case R.id.it_second:
                Intent second =new Intent(frag_sixth_A.this, com.example.e_plants.frag_second_A.class);
                startActivity(second);
                return true;

            case R.id.it_first2:
                Intent first2 =new Intent(frag_sixth_A.this, com.example.e_plants.diary.class);
                startActivity(first2);
                return true;

            case R.id.it_add:
                Intent adds =new Intent(frag_sixth_A.this, com.example.e_plants.new_plant_A.class);
                startActivity(adds);
                return true;

            case R.id.it_first:
                Intent first =new Intent(frag_sixth_A.this, com.example.e_plants.frag_first_A.class);
                startActivity(first);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent logOut = new Intent(frag_sixth_A.this,com.example.e_plants.login_A.class);
                startActivity(logOut);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
