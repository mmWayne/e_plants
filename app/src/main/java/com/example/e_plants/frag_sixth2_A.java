package com.example.e_plants;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class frag_sixth2_A extends AppCompatActivity {
    Button confirm;
    EditText p;
    EditText newP;
    EditText againP;
    FirebaseAuth auth;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sixth2);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         auth=FirebaseAuth.getInstance();
        String email = user.getEmail();
        confirm = findViewById(R.id.button3);
        p = findViewById(R.id.register_password2);
        newP = findViewById(R.id.re_npwd);
        againP = findViewById(R.id.register_comfirmpass3);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
            private void changePassword() {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String password = p.getText().toString().trim();
                String newPassword = newP.getText().toString().trim();
                String confirmPassword = againP.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(frag_sixth2_A.this, "請輸入原始密碼 ", Toast.LENGTH_LONG).show();
                    return;
                } else if (TextUtils.isEmpty(newPassword)) {
                    Toast.makeText(frag_sixth2_A.this, "請輸入新密碼 ", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(frag_sixth2_A.this, "請再次輸入新密碼 ", Toast.LENGTH_LONG).show();
                } else if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(frag_sixth2_A.this, "新密碼與再次輸入新密碼不一致 ", Toast.LENGTH_LONG).show();
                } else if (newPassword.equals(confirmPassword)) {
                    if (user != null && user.getEmail() != null) {
                        AuthCredential authCredential = EmailAuthProvider.getCredential(email, password);
                        user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    //Toast.makeText(frag_sixth2_A.this, "身份驗證成功! ", Toast.LENGTH_SHORT).show();
                                    user.updatePassword(newPassword)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(frag_sixth2_A.this, "密碼更改成功，請重新登入 ", Toast.LENGTH_SHORT).show();
                                                        //user.signout
                                                        Intent intent = new Intent(frag_sixth2_A.this, login_A.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(frag_sixth2_A.this, "原始密碼輸入錯誤!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Intent intent = new Intent(frag_sixth2_A.this, login_A.class);
                        startActivity(intent);
                        finish();
                    }
                }
                else if (password.equals(newPassword)) {
                    Toast.makeText(frag_sixth2_A.this, "原始密碼與新密碼不能一致 ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(frag_sixth2_A.this, "新密码设置成功 ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
       // return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                Intent intent =new Intent(frag_sixth2_A.this, frag_sixth_A.class);
                startActivity(intent);
                return true;

            case R.id.it_second:
                Intent second =new Intent(frag_sixth2_A.this, frag_second_A.class);
                startActivity(second);
                return true;

            case R.id.it_first2:
                Intent first2 =new Intent(frag_sixth2_A.this, diary.class);
                startActivity(first2);
                return true;

            case R.id.it_add:
                Intent adds =new Intent(frag_sixth2_A.this, new_plant_A.class);
                startActivity(adds);
                return true;


            case R.id.it_first:
                Intent first =new Intent(frag_sixth2_A.this, frag_first_A.class);
                startActivity(first);
                return true;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent logOut = new Intent(frag_sixth2_A.this, login_A.class);
                startActivity(logOut);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


                /*if(user!=null&&user.getEmail()!=null){
                    AuthCredential authCredential = EmailAuthProvider.getCredential(email, password);
                    user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(frag_sixth2_A.this, "re-authentication success ", Toast.LENGTH_SHORT).show();
                                user.updatePassword(newPassword)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(frag_sixth2_A.this, "password changed success ", Toast.LENGTH_SHORT).show();
                                                    //user.signout
                                                    Intent intent = new Intent(frag_sixth2_A.this, login_A.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(frag_sixth2_A.this, "re-authentication failed ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                        Intent intent = new Intent(frag_sixth2_A.this, login_A.class);
                        startActivity(intent);
                        finish();
                }
            }*/

           /* private void modifyPsw(String newPassword) {
                String md5psw=MD5Utils.md5(newPsw);
                SharedPreferences sharedPreferences=getSharedPreferences("loginInfo",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(userName,md5psw);
                editor.commit();

            }*/
        //});
    //}
            /*private void updatePassword(String password, String newPassword) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email=user.getEmail();
                AuthCredential authCredential = EmailAuthProvider.getCredential(email, password);
                user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        user.updatePassword(newPassword)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(frag_sixth2_A.this, "password updated... ", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(frag_sixth2_A.this, frag_first_A.class);
                                        startActivity(intent);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(frag_sixth2_A.this, " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }


                });
            }*/

            /*public void onChangePassword(String oldPassword, final String newPassword) {
                //loadingDialog.setMessage("Changing password...");
                //loadingDialog.show(getSupportFragmentManager(),"Changing Password");
                String currentEmail = user.getEmail();
                AuthCredential credential = EmailAuthProvider.getCredential(currentEmail, oldPassword);
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(newPassword)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(frag_sixth2_A.this, "Password was changed successfully", Toast.LENGTH_LONG).show();
                                                    }
                                                    //loadingDialog.dismiss();
                                                }
                                            });
                                } else {
                                    Toast.makeText(frag_sixth2_A.this, "Authentication failed, wrong password?", Toast.LENGTH_LONG).show();
                                    //loadingDialog.dismiss();
                                }
                            }
                        });
            }*/


        //pwd = findViewById(R.id.re_npwd);
        //String spwd = pwd.getText().toString();
        //String scpwd = cpwd.getText().toString();
        //confirm = findViewById(R.id.button3);
        /*
        confirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                 if(spwd.equals(scpwd)){

                 }else{
                 Toast.makeText(register_A.this, "密碼不一致", Toast.LENGTH_SHORT).show();
                 }

                finish();
            }
        });

 */
    //}




