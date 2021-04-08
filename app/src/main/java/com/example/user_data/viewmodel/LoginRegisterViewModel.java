package com.example.user_data.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.user_data.model.repo;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class LoginRegisterViewModel extends AndroidViewModel {
    private final com.example.user_data.model.repo repo;
    private final MutableLiveData<FirebaseUser> user;
    private final MutableLiveData<FirebaseUser> userlogin;


    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);
        repo = new repo(application);
        user = repo.getUserdata();
        userlogin = repo.getUserdatalogin();
    }

    public void register(String Email , String Password , String Name , String City , String State , String Phone){
        repo.register(Email,Password,Name,City,State,Phone);
    }

    public  void findvalue(String value){
        repo.findvalue(value);
    }

    public void login(String Email , String Password){
        repo.login(Email,Password);
    }

    public MutableLiveData<FirebaseUser> getUserlogin() {
        return userlogin;
    }

    public MutableLiveData<FirebaseUser> getUser() {
        return user;
    }

}
