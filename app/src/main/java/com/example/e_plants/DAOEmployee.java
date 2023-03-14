package com.example.e_plants;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.PrivateKey;
import java.util.HashMap;

public class DAOEmployee {
    private DatabaseReference  databaseReference;
    public DAOEmployee(){
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        databaseReference=db.getReference(Cricketer.class.getSimpleName());
    }
    public Task<Void> add(Cricketer emp){

           return  databaseReference.push().setValue(emp);
    }
    public Task<Void> update(String key, HashMap<String,Object>hashMap){

        return  databaseReference.child(key).updateChildren(hashMap);
    }
    public Task<Void> remove(String key){

        return  databaseReference.child(key).removeValue();
    }
}
