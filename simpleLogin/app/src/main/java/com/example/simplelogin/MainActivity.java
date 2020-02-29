package com.example.simplelogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button signUpBtn;
    EditText emailTxt, passwordTxt;
    FirebaseAuth mFirebaseAuth;
    Intent intent;
    String email, password;
    TextView signInTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailTxt = findViewById(R.id.emailText);
        passwordTxt = findViewById(R.id.passwordText);
        signUpBtn = findViewById(R.id.signUpButton);
        signInTxt = findViewById(R.id.textView);

        // Login screen
        signInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


        // Sign Up
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailTxt.getText().toString();
                password = passwordTxt.getText().toString();

                if (email.isEmpty()) {
                    emailTxt.setError("Please enter email addrerss");
                    emailTxt.requestFocus();
                } else if (password.isEmpty()) {
                    passwordTxt.setError("Please insert password");
                    passwordTxt.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                    emailTxt.requestFocus();
                } else if (!email.isEmpty() && !password.isEmpty()) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this,"Something wrong check connection",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(MainActivity.this, ContentActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this,"Error on System", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
