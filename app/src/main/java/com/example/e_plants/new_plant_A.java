package com.example.e_plants;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class new_plant_A extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    TextView cancel;
    ImageButton searchDate;
    Calendar calendar = Calendar.getInstance();
    TextView showdate,extraname;
    EditText personName;
    Button  addnew,alreadyadd;
    public  static  String t;
    ListView LV;
    int x_last=0;
    Context context=this;
    String x_select;
    ArrayList<Cricketer> cricketerArrayList=new ArrayList<>();
    DatabaseReference databaseUser;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_plant );
        personName=findViewById(R.id.editTextTextPersonName);
        addnew=findViewById(R.id.btn_addnew);
        alreadyadd=findViewById(R.id.btn_alreadyadd);
        extraname=findViewById(R.id.editTextTextPersonName);
        databaseUser=FirebaseDatabase.getInstance().getReference();
        alreadyadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(new_plant_A.this, com.example.e_plants.list.class);
                startActivity(intent);
            }
        });
        DAOEmployee dao=new DAOEmployee();
        Cricketer  cricketer_edit=(Cricketer)getIntent().getSerializableExtra("EDIT");
        if(cricketer_edit !=null){
            addnew.setText("更新");
            personName.setText(cricketer_edit.getPlantName());
            showdate.setText(cricketer_edit.getDateName());
            extraname.setText(cricketer_edit.getExtraName());
            alreadyadd.setVisibility(View.GONE);
        }
        else{
            addnew.setText("新增");
            alreadyadd.setVisibility(View.VISIBLE);
        }
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = extraname.getText().toString();
                String id = databaseUser.push().getKey();
                System.out.println("text="+t);
                Cricketer CR = new Cricketer(t, showdate.getText().toString(), n);
                if(cricketer_edit==null) {
                    /*dao.add(CR).addOnSuccessListener(suc-> {
                        Toast.makeText(new_plant_A.this, "record is inserted", Toast.LENGTH_SHORT).show();
                    }).addOnFailureListener(er->{
                        Toast.makeText(new_plant_A.this, er.getMessage(), Toast.LENGTH_SHORT).show();
                    });*/
                    databaseUser.child("users").child(id).setValue(CR).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(new_plant_A.this, "add successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(new_plant_A.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("plantname",personName.getText().toString());
                    hashMap.put("showdate",showdate.getText().toString());
                    hashMap.put("extraname",extraname.getText().toString());
                    dao.update(cricketer_edit.getKey(),hashMap).addOnSuccessListener(suc->{
                        Toast.makeText(new_plant_A.this, "record is update", Toast.LENGTH_SHORT).show();
                        finish();
                    }).addOnFailureListener(er->{
                        Toast.makeText(new_plant_A.this, er.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    });

                }

            }
        });

        //x_last+=1;
        //myref1.child(String.valueOf(x_last)).setValue(new Cricketer(text,showdate.getText().toString(),extraname.getText().toString()));
        //firebase_select(myref1);
        //if(checkIfVaildAndRead()){
        //cricketerArrayList.clear();
        //for(int i=0;i<layoutList)
        // }
        //Intent intent = new Intent(new_plant_A.this, com.example.e_plants.frag_first_A.class);
        //startActivity(intent);
        /*
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView t1=findViewById(R.id.textView6);
                x_select=t1.getText().toString();
                TextView t2=findViewById(R.id.textView10);
                //t1.getText().toString();
            }
        });*/
        /** 頁面跳轉 **/
        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(new_plant_A.this, com.example.e_plants.frag_first_A.class);
                startActivity(intent);
            }
        });

        showdate = findViewById(R.id.showdate);
        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                showdate.setText( sdf.format(calendar.getTime()));
            }
        };

        searchDate = findViewById(R.id.searchDate);
        searchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(new_plant_A.this,
                        datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        Spinner spinner = findViewById(R.id.sp);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this
                ,R.array.plant_n,android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        t=adapterView.getItemAtPosition(i).toString();
        //print();
        System.out.println(t);
        //new frag_forth_A().fsearch(text);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                Intent intent =new Intent(new_plant_A.this, frag_sixth_A.class);
                startActivity(intent);
                return true;

            case R.id.it_second:
                Intent second =new Intent(new_plant_A.this, com.example.e_plants.frag_second_A.class);
                startActivity(second);
                return true;

            case R.id.it_first2:
                Intent first2 =new Intent(new_plant_A.this, com.example.e_plants.diary.class);
                startActivity(first2);
                return true;


            case R.id.it_first:
                Intent first =new Intent(new_plant_A.this, com.example.e_plants.frag_first_A.class);
                startActivity(first);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent logOut = new Intent(new_plant_A.this,com.example.e_plants.login_A.class);
                startActivity(logOut);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}