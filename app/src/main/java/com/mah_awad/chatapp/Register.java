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
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    EditText userName, email, password;
    Button btn_register;
    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initialize tool bar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // showTitle


        // initialize userName , email ,Password
        userName = findViewById(R.id.editTextTextPersonName);
        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        btn_register = findViewById(R.id.btn_signUp);

        // initialize firebaseAuth
        auth = FirebaseAuth.getInstance();

        // btn_register Onclick
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get information from user
                String txt_userName = userName.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();

                // check if userName ,email, password is empty or no
                if (TextUtils.isEmpty(txt_userName) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(Register.this, "All Fields are required", Toast.LENGTH_SHORT).show();

                } else if (txt_password.length() < 6) {
                    Toast.makeText(Register.this, "password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                } else {
                    // implement fun register
                    register(txt_userName, txt_email, txt_password);
                }

            }
        });


    }

    // Create fun register
    private void register(final String userName, String email, String password) {

        // Sign up  new User
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // initialize firebaseUser
                            FirebaseUser user = auth.getCurrentUser();
                            assert user != null;
                            String userId = user.getUid();  // getUid - get a real user ID

                            //initialize firebaseDatabase with name of table
                            reference = FirebaseDatabase.getInstance().getReference("users").child(userId);

                            // initialize arrayList like hashMap as k,v as column of table
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("userName", userName);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");
                            hashMap.put("search", userName.toLowerCase());

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    // move to home Activity
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                        // clear any task before the activity is started and start new task
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Register.this, "you can't register with this account", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

}