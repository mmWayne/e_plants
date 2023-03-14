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
        //remember = findViewById(R.id.remember);
        ID = findViewById(R.id.enterID);
        Password = findViewById(R.id.enterPass);
        Register = findViewById(R.id.toRegister);
        loginBtn = findViewById(R.id.btn_login);
        output();

        Register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(login_A.this, com.example.e_plants.register_A.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth1.signInWithEmailAndPassword(ID.getText().toString(),Password.getText().toString())
                        .addOnCompleteListener(login_A.this, task -> {
                            if (task.isSuccessful()) {
                                FirebaseUser user=mAuth1.getCurrentUser();
                                Toast.makeText(login_A.this, "sign success "+user.getEmail(), Toast.LENGTH_SHORT).show();
                                email=user.getEmail();
                                Intent intent = new Intent();
                                intent.setClass(login_A.this,frag_first_A.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(login_A.this, "sign unsuccessful"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
    private void input() {
        SharedPreferences user = getSharedPreferences("remember", MODE_PRIVATE);
        SharedPreferences.Editor edit = user.edit();
        if (remember.isChecked()) { //如果記住密碼那個選項有打勾就把這次成功登入的資料儲存起來
            edit.putString("account2",ID.getText().toString());
            edit.putString("password2",Password.getText().toString());
            edit.putBoolean("remember",true);  //把我自己設的remember狀態設為true
        } else {
            edit.remove("account2"); //若上次沒打勾就把它清除記住的資料
            edit.remove("password2");
            edit.putBoolean("remember",false);
        }
        edit.commit();
    }

    private void output(){
        SharedPreferences user =getSharedPreferences("remember",MODE_PRIVATE);
        String account2=user.getString("account2","");  //若沒取得就是沒任何東西 " "
        String password2=user.getString("password2","");
        boolean remember=user.getBoolean("remember",false);
        //在input()我是寫成若打勾則讓它remember設為true，所以若沒取到則是false

        ID.setText(account2); //有取到先前給予的資料的話就會顯示出來
        Password.setText(password2);
        //remember.setChecked(remember);//並且利用remember的狀態判斷上次是否打勾，有的話就顯示打勾
    }
}
