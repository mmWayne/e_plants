package com.example.e_plants;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class writediary extends AppCompatActivity {
    FirebaseAuth mAuth1;
    TextView textday;
    ImageButton tDate;
    TextView showDate1;
    TextView textcontent;
    EditText editTextContent;
    Button btn_adddiary;
    DatabaseReference databaseUser;
    FirebaseUser mCurrentUser;

    public static  final String SHARED_PREFS="sharedPrefs";
    public static  final String TXET="text";
    private  String t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writwdiary);
        Calendar calendar = Calendar.getInstance();
        mAuth1 = FirebaseAuth.getInstance();
        textday = findViewById(R.id.textday);
        textcontent = findViewById(R.id.textcontent);
        editTextContent = findViewById(R.id.editTextContent);
        btn_adddiary = findViewById(R.id.btn_adddiary);
        tDate = findViewById(R.id.tDate);
        showDate1 = findViewById(R.id.showDate1);
        databaseUser= FirebaseDatabase.getInstance().getReference();
        DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.TAIWAN);
                showDate1.setText(sdf.format(calendar.getTime()));
            }
        };
        tDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(writediary.this,
                        datePicker,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        btn_adddiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDate();

            }
        });
        loadDate();
        upDate();
        btn_adddiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String n = editTextContent.getText().toString();
                String id = databaseUser.push().getKey();
                //mAuth1 = FirebaseAuth.getInstance();
                //mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
                //String current_uid = mCurrentUser.getUid();
                System.out.println("text="+t);
                Cricketer1 CR = new Cricketer1(showDate1.getText().toString(), n);
                databaseUser.child("users").child(id).child("diary").setValue(CR).
                //databaseUser.child("users").child(current_uid).child("diary").setValue(CR).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(writediary.this, "add successfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
        public void saveDate() {
            String can=Integer.toString(Calendar.YEAR+Calendar.MONTH+Calendar.DAY_OF_MONTH);
            SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(TXET,can);
            editor.putString(TXET,editTextContent.getText().toString());
            editor.apply();
            Toast.makeText(writediary.this,"儲存成功",Toast.LENGTH_SHORT).show();
        }
        public void loadDate() {
            SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
            t=sharedPreferences.getString(TXET,"");
        }
        public void upDate(){
            editTextContent.setText(t);
        }


}
