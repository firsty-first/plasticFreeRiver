package com.example.plasticfreeriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText email;
    private   EditText pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.etUsername);
        pswd=findViewById(R.id.etPassword);
        Button register_btn = findViewById(R.id.register_button);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid_string=  email.getText().toString();
                String pswd_string=pswd.getText().toString();
                if(pswd_string.length()<6 && pswd_string.length()>0)
                    Toast.makeText(getApplicationContext(), "Password too short", Toast.LENGTH_SHORT).show();
                if(pswd_string.length()==0)
                    Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                if(pswd_string.length()>=6)
                    registerUser(emailid_string,pswd_string);

            }
        });
    }

    private void registerUser(String emailid_string, String pswd_string) {
        auth.createUserWithEmailAndPassword(emailid_string, pswd_string).addOnCompleteListener((Activity) getApplicationContext(), new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "SuccessFully registered", Toast.LENGTH_SHORT).show();
                    ImageView logo=findViewById(R.id.logo);
                    logo.setImageDrawable(getDrawable(R.drawable.tick));
                }
                    if(task.isCanceled())
                        Toast.makeText(registerActivity.this, "Registeration cancelled ", Toast.LENGTH_SHORT).show();
                }
        });
    }



    }
