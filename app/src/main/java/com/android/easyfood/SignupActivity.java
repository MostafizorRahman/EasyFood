package com.android.easyfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText signUpEmailEdidText, signUpPasswordEdidText;
    private TextView signInTextView;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.setTitle("Sign Up");
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbarId);

        signUpEmailEdidText = findViewById(R.id.regEmail);
        signUpPasswordEdidText = findViewById(R.id.regPassword);
        signUpButton = findViewById(R.id.btnSignUp);
        signInTextView = findViewById(R.id.gosignin);

        signInTextView.setOnClickListener(this);
        signUpButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                userRegister();
                break;

            case R.id.gosignin:
                Intent intent = new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(intent);
                break;


        }

    }

    private void userRegister() {
        String email = signUpEmailEdidText.getText().toString().trim();
        String password =signUpPasswordEdidText.getText().toString().trim();

        if(email.isEmpty()){
            signUpEmailEdidText.setError("Enter an email address");
            signUpEmailEdidText.requestFocus();
            return;

        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signUpEmailEdidText.setError("Enter an email address");
            signUpEmailEdidText.requestFocus();
            return;

        }

        if(password.isEmpty()){
            signUpPasswordEdidText.setError("Enter an email address");
            signUpPasswordEdidText.requestFocus();
            return;

        }
        if(password.length()<6)
        {
            signUpPasswordEdidText.setError("Minimum length of Password should be 6");
            signUpPasswordEdidText.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    Toast.makeText(getApplicationContext(), "Register is successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                } else {

                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(), "User is already Registered", Toast.LENGTH_SHORT).show();
                    }
                    else{

                        Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }

}
