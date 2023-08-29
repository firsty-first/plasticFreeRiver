package com.example.plasticfreeriver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plasticfreeriver.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class loginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText pswd;
    private Button btnlogin;
    FirebaseDatabase database;
    GoogleSignInClient googleSignInClient;
    //ProgressDialog progressDialog;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        EditText email;
        EditText pswd;

        email=findViewById(R.id.etUsername);
        pswd=findViewById(R.id.etPassword);
        btnlogin=findViewById(R.id.btnLogin);
        CardView card=findViewById(R.id.card);


        TextView register_btn=findViewById(R.id.register_btn);
        mAuth = FirebaseAuth.getInstance();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid_string=  email.getText().toString();
                String pswd_string=pswd.getText().toString();
                mAuth.signInWithEmailAndPassword(emailid_string,pswd_string).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        btnlogin.setText("Logged in");
                        ImageView logo=findViewById(R.id.logo);
                        Drawable drawable = getResources().getDrawable(R.drawable.tick);
                        logo.setImageDrawable(drawable);
                        card.setCardBackgroundColor(getResources().getColor(R.color.green));
                        startActivity(new Intent(loginActivity.this, MainActivity.class));
                        finish();
                    }

                });
                mAuth.signInWithEmailAndPassword(emailid_string,pswd_string).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(loginActivity.this, "Incorrect credential", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1=new Intent(loginActivity.this,registerActivity.class);
                startActivity(i1);
            }
        });


// ...
// Initialize Firebase Auth

    }


    @Override
    public void onStart() {

        super.onStart();
       // FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
}