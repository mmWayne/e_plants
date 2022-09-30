package com.example.e_plants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class diary extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TXET = "text";
    Button save;
    EditText text;
    private String t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);
        text = findViewById(R.id.diary);
        save = findViewById(R.id.button2);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDate();

            }
        });
        loadDate();
        upDate();
    }

    public void saveDate() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TXET, text.getText().toString());
        editor.apply();
        Toast.makeText(diary.this, "儲存成功", Toast.LENGTH_SHORT).show();
    }

    public void loadDate() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        t = sharedPreferences.getString(TXET, "");
    }

    public void upDate() {
        text.setText(t);
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
                Intent intent = new Intent(diary.this, frag_sixth_A.class);
                startActivity(intent);
                return true;

            case R.id.it_second:
                Intent second = new Intent(diary.this, frag_second_A.class);
                startActivity(second);
                return true;

            case R.id.it_first2:
                Intent first2 = new Intent(diary.this, diary.class);
                startActivity(first2);
                return true;

            case R.id.it_add:
                Intent adds = new Intent(diary.this, new_plant_A.class);
                startActivity(adds);
                return true;

            case R.id.it_first:
                Intent first = new Intent(diary.this, frag_first_A.class);
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
}


