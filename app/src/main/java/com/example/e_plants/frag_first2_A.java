package com.example.e_plants;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class frag_first2_A extends AppCompatActivity {

    ImageView imageView;
    ImageButton searchDate;
    ImageButton back;
    Button PlantInfo;
    TextView showdate;
    Calendar calendar = Calendar.getInstance();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first2);
        searchDate = findViewById(R.id.searchDate2);
        imageView = findViewById(R.id.imageView2);
        showdate = findViewById(R.id.showdate2);
        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                showdate.setText("日期：" + sdf.format(calendar.getTime()));
            }
        };

        searchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(frag_first2_A.this,
                        datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        /**
         back = findViewById(R.id.back);
         back.setOnClickListener(new View.OnClickListener(){
        @Override public void onClick(View v){
        Intent intent = new Intent(frag_first2_A.this, frag_first_A.class);
        startActivity(intent);
        }
        });
         **/

        PlantInfo = findViewById(R.id.btn_plantInfo);
        PlantInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(frag_first2_A.this, frag_forth_A.class);
                startActivity(intent);
            }
        });
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
                Intent intent = new Intent(frag_first2_A.this, com.example.e_plants.frag_sixth_A.class);
                startActivity(intent);
                return true;

            case R.id.it_second:
                Intent second = new Intent(frag_first2_A.this, frag_second_A.class);
                startActivity(second);
                return true;


            case R.id.it_add:
                Intent adds = new Intent(frag_first2_A.this, com.example.e_plants.new_plant_A.class);
                startActivity(adds);
                return true;

            case R.id.it_first:
                Intent first = new Intent(frag_first2_A.this, com.example.e_plants.frag_first_A.class);
                startActivity(first);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent logOut = new Intent(frag_first2_A.this, com.example.e_plants.login_A.class);
                startActivity(logOut);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}