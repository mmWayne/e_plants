package com.example.e_plants;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class register_A extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText accountEdit;
    EditText passwordEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();
        accountEdit = (EditText) findViewById(R.id.register_email);
        passwordEdit = (EditText) findViewById(R.id.re_pwd);
        Button signUpBtn =  findViewById(R.id.btn_regisComplete);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(accountEdit.getText().toString(),passwordEdit.getText().toString())
                        .addOnCompleteListener(register_A.this, task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(register_A.this, "success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.setClass(register_A.this, login_A.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(register_A.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
