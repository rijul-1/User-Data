package com.example.user_data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user_data.R;
import com.example.user_data.viewmodel.LoginRegisterViewModel;
import com.google.firebase.auth.FirebaseUser;

public class Sign_Up extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText phone;
    EditText pwd;
    EditText city;
    EditText state;
    Button Sign_Up;
    Button Sign_In;
    private LoginRegisterViewModel loginRegisterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        loginRegisterViewModel = ViewModelProviders.of(this).get(LoginRegisterViewModel.class);
        loginRegisterViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser != null){
                    Toast.makeText(getApplicationContext() , "User Created",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Sign_Up.this , My_List.class);
                    startActivity(i);
                }
            }
        });

        name = findViewById(R.id.Name);
        phone = findViewById(R.id.Phone_No);
        email = findViewById(R.id.email_id);
        pwd = findViewById(R.id.pwd);
        city = findViewById(R.id.City);
        state = findViewById(R.id.State);
        Sign_Up = findViewById(R.id.Sign_Up);
        Sign_In = findViewById(R.id.sign_in);

        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginRegisterViewModel.register(email.getText().toString() , pwd.getText().toString() ,
                        name.getText().toString() , city.getText().toString() , state.getText().toString(),
                        phone.getText().toString());
            }
        });

        Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Sign_Up.this , MainActivity.class);
                startActivity(i);
            }
        });
    }
}