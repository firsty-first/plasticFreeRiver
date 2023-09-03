package com.example.plasticfreeriver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private EditText email;
    private   EditText pswd, name;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.etUsername);
        pswd=findViewById(R.id.etPassword);
        //name=findViewById(R.id.etName);
        Button register_btn = findViewById(R.id.register_button);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid_string=  email.getText().toString();
                String pswd_string=pswd.getText().toString();
                if(pswd_string.length()<6 && pswd_string.length()>0)
                    Toast.makeText(registerActivity.this, "Password too short", Toast.LENGTH_SHORT).show();
                if(pswd_string.length()==0)
                    Toast.makeText(registerActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                if(pswd_string.length()>=6)
                    registerUser(emailid_string,pswd_string);

            }
        });
    }

    private void registerUser(String emailid_string, String pswd_string) {
        Log.d("auth","gsoo soo status");
        auth.createUserWithEmailAndPassword(emailid_string, pswd_string).addOnCompleteListener((Activity) registerActivity.this, new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("auth","goood status");
                if(task.isSuccessful()) {
//                    User user=new User();
//                    user.setName("ravi");
//
//                    database.getReference().child("user").push().setValue(user);

                    Toast.makeText((Activity)registerActivity.this, "SuccessFully registered", Toast.LENGTH_SHORT).show();
                    ImageView logo=findViewById(R.id.logo);
                    logo.setImageDrawable(getDrawable(R.drawable.tick));
                }
                else
                {
                    Toast.makeText(registerActivity.this, "Failed to register ", Toast.LENGTH_SHORT).show();
                }
                    if(task.isCanceled())
                        Toast.makeText(registerActivity.this, "Registeration cancelled ", Toast.LENGTH_SHORT).show();
                }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //DatabaseReference reference=database.getReference();
    }
}
