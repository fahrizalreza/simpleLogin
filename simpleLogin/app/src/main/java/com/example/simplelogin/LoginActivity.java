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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    Button signInBtn;
    EditText emailTxt, passwordTxt;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mFirebaseUser;
    String email, password;
    TextView signUpTxt;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailTxt = findViewById(R.id.emailText);
        passwordTxt = findViewById(R.id.passwordText);
        signInBtn = findViewById(R.id.signInButton);
        signUpTxt = findViewById(R.id.textView);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if( mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, ContentActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Please login first", Toast.LENGTH_SHORT).show();
                }
            }
        };

        signInBtn.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(LoginActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                    emailTxt.requestFocus();
                } else if (!email.isEmpty() && !password.isEmpty()) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()) {
                               Toast.makeText(LoginActivity.this, "Something wrong check connection", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(LoginActivity.this, ContentActivity.class));
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this,"Error on System", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
