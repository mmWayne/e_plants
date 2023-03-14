package com.example.e_plants;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
//植物百科
public class frag_forth_A extends AppCompatActivity {
    ImageButton back;
    TextView name,scName,pf,pfo,growth,plantCare,note;
    ImageView image;
    FirebaseAuth auth;
    FirebaseFirestore fireStore;
    StorageReference storage;
    static  String t="";String n=" ";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forth);
        name = findViewById(R.id.plant_name);
        scName = findViewById(R.id.plant_scientificName);
        pf = findViewById(R.id.plant_family);
        pfo = findViewById(R.id.plant_placeofOrigin);
        growth = findViewById(R.id.growth_environ);
        plantCare = findViewById(R.id.plant_care);
        note = findViewById(R.id.plant_note);
        image = findViewById(R.id.imageView6);
        plantCare.setMovementMethod(ScrollingMovementMethod.getInstance());
        auth = FirebaseAuth.getInstance();
        //userId=auth.getCurrentUser().getUid();
        fireStore = FirebaseFirestore.getInstance();
        System.out.println("--"+t);
        fireStore.collection("植物百科資料表").document(t).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot=task.getResult();
                    if(snapshot.exists()&&snapshot!=null){
                        name.setText(t);
                        //"仙人掌"
                        String scname=snapshot.getString("學名");
                        String aPf=snapshot.getString("科屬");
                        String aPfo=snapshot.getString("原產地");
                        String aGrowth=snapshot.getString("適合光照")+"\n"+"\n"+snapshot.getString("適合溫度")
                                +"\n"+"\n"+snapshot.getString("適合濕度")+"\n"+"\n"+snapshot.getString("施肥時間");
                        String aPlantCare=snapshot.getString("栽培方式")+"\n"+"\n"+snapshot.getString("病蟲害防治");
                        scName.setText(scname);
                        pf.setText(aPf);
                        pfo.setText(aPfo);
                        growth.setText(aGrowth);
                        plantCare.setText(aPlantCare);
                    }
                }
                else{
                    Toast.makeText(frag_forth_A.this, "error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        if(t.length()<3){
            String tt="小"+t;
            n=tt+".png";
            System.out.println("n="+n);
        }
        else{
            n=t+".png";
            System.out.println("n="+n);
        }
        storage=FirebaseStorage.getInstance().getReference().child(n);
        try {
            final File file=File.createTempFile(n,"png");
            storage.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(frag_forth_A.this, "picture retrieved",Toast.LENGTH_SHORT).show();
                    Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                    image.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(frag_forth_A.this, "error!",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void fsearch(String a) {
        t = a;
        System.out.println("t="+t);
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
                Intent intent =new Intent(frag_forth_A.this, com.example.e_plants.frag_sixth_A.class);
                startActivity(intent);
                return true;

            case R.id.it_second:
                Intent second =new Intent(frag_forth_A.this, frag_second_A.class);
                startActivity(second);
                return true;

            case R.id.it_first2:
                Intent first2 =new Intent(frag_forth_A.this, com.example.e_plants.diary.class);
                startActivity(first2);
                return true;

            case R.id.it_add:
                Intent adds =new Intent(frag_forth_A.this, com.example.e_plants.new_plant_A.class);
                startActivity(adds);
                return true;

            case R.id.it_first:
                Intent first =new Intent(frag_forth_A.this, com.example.e_plants.frag_first_A.class);
                startActivity(first);
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent logOut = new Intent(frag_forth_A.this,com.example.e_plants.login_A.class);
                startActivity(logOut);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}