package com.example.user_data.model;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class repo {
    private FirebaseFirestore mfirestore;
    private FirebaseAuth mfirebaseauth;
    private Application application;
    private MutableLiveData<FirebaseUser> userdata;
    private MutableLiveData<FirebaseUser> userdatalogin;
    String UserId;


    public repo(Application application) {
        this.application = application;
        mfirebaseauth = FirebaseAuth.getInstance();
        mfirestore = FirebaseFirestore.getInstance();
        userdata = new MutableLiveData<>();
        userdatalogin = new MutableLiveData<>();
    }

    ;

    public void register(String Email, String Password, String Name , String City , String State , String Phone) {
        Log.d("users_data", Email + " " + Password);
        mfirebaseauth.createUserWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                UserId = mfirebaseauth.getCurrentUser().getUid();
                DocumentReference documentreference = mfirestore.collection("Users").document(UserId);
                Map<String,Object> user = new HashMap<>();
                user.put("Name",Name);
                user.put("City",City);
                user.put("State",State);
                user.put("Email",Email);
                user.put("Phone-No",Phone);
                documentreference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        userdata.postValue(mfirebaseauth.getCurrentUser());
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(application , e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void login(String Email, String Password) {
        mfirebaseauth.signInWithEmailAndPassword(Email, Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                userdatalogin.postValue(mfirebaseauth.getCurrentUser());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(application, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void findvalue(String value){
        String UserId = mfirebaseauth.getCurrentUser().getUid();
        DocumentReference documentReference = mfirestore.collection("Users").document(UserId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        Toast.makeText(application,"Your " + "' " + value + "' " + "is - " +
                                documentSnapshot.get(value).toString(),Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.d("values","no such documents");
                    }
                }else{
                    Log.d("values", String.valueOf(task.getException()));
                }

            }
        });

    }

    public MutableLiveData<FirebaseUser> getUserdata() {
        return userdata;
    }

    public MutableLiveData<FirebaseUser> getUserdatalogin() {
        return userdatalogin;
    }

}
