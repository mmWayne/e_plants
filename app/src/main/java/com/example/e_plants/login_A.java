package com.example.e_plants;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_A extends AppCompatActivity {
    FirebaseAuth mAuth1;
    Button loginBtn;
    String email;
    CheckBox remember;
    EditText ID;
    EditText Password;
    TextView Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth1 = FirebaseAuth.getInstance();
        remember = findViewById(R.id.remember);
        ID = findViewById(R.id.enterID);
        Password = findViewById(R.id.enterPass);
        Register = findViewById(R.id.toRegister);
        loginBtn = findViewById(R.id.btn_login);
        output();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login_A.this, com.example.e_plants.register_A.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth1.signInWithEmailAndPassword(ID.getText().toString(), Password.getText().toString())
                        .addOnCompleteListener(login_A.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth1.getCurrentUser();
                                Toast.makeText(login_A.this, "sign success " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                email = user.getEmail();
                                Intent intent = new Intent();
                                intent.setClass(login_A.this, frag_first_A.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(login_A.this, "sign unsuccessful" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void input() {
        SharedPreferences user = getSharedPreferences("remember", MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        if (remember.isChecked()) { //????????????????????????????????????????????????????????????????????????????????????
            edit.putString("account2", ID.getText().toString());
            edit.putString("password2", Password.getText().toString());
            edit.putBoolean("remember", true);  //??????????????????remember????????????true
        } else {
            edit.remove("account2"); //????????????????????????????????????????????????
            edit.remove("password2");
            edit.putBoolean("remember", false);
        }
        edit.commit();
    }

    private void output() {
        SharedPreferences user = getSharedPreferences("remember", MODE_PRIVATE);
        String account2 = user.getString("account2", "");  //????????????????????????????????? " "
        String password2 = user.getString("password2", "");
        boolean remember = user.getBoolean("remember", false);
        //???input()??????????????????????????????remember??????true???????????????????????????false

        ID.setText(account2); //??????????????????????????????????????????????????????
        Password.setText(password2);
        //remember.setChecked(remember);//????????????remember????????????????????????????????????????????????????????????
    }
}
