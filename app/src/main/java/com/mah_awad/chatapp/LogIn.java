package com.mah_awad.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LogIn extends AppCompatActivity {

    EditText email ,password;
    Button brn_logIn;

    FirebaseAuth auth;
    DatabaseReference reference;
    TextView forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // initialize tool bar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("LogIn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // showTitle

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        brn_logIn = findViewById(R.id.btn_signIn);
        forget_password = findViewById(R.id.forget_password);

        // initialize firebaseAuth
        auth =FirebaseAuth.getInstance();

        // click on forget password
        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogIn.this,ResetPassword.class));
            }
        });

       // click on btn_login
       brn_logIn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String txt_email  = email.getText().toString();
               String txt_password =password.getText().toString();

               if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                   Toast.makeText(LogIn.this, "All Fields are required", Toast.LENGTH_SHORT).show();
               }else {
                      // sign in  existing user
                     auth.signInWithEmailAndPassword(txt_email,txt_password)
                             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                 @Override
                                 public void onComplete(@NonNull Task<AuthResult> task) {

                                     if(task.isSuccessful()){
                                         // move to home Activity
                                         Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                         startActivity(intent);
                                         finish();
                                     }else{
                                         Toast.makeText(LogIn.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                                     }

                                 }
                             });

               }
           }
       });



    }
}