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

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText signInEmailEdidText, signInPasswordEdidText;
    private TextView signUpTextView;
    private Button signInButton;
    private FirebaseAuth mAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        this.setTitle("Sign In");
        mAuth = FirebaseAuth.getInstance();

        signInEmailEdidText = findViewById(R.id.logEmail);
        signInPasswordEdidText = findViewById(R.id.logPassword);
        signInButton = findViewById(R.id.btnSignIn);
        signUpTextView = findViewById(R.id.gosignup);
        progressBar = findViewById(R.id.progressbarId);

        signUpTextView.setOnClickListener(this);
        signInButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignIn:
                userLogin();
                break;

            case R.id.gosignup:
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
                break;


        }

    }

    private void userLogin() {

        String email = signInEmailEdidText.getText().toString().trim();
        String password =signInPasswordEdidText.getText().toString().trim();

        if(email.isEmpty()){
            signInEmailEdidText.setError("Enter an email address");
            signInEmailEdidText.requestFocus();
            return;

        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signInEmailEdidText.setError("Enter an email address");
            signInEmailEdidText.requestFocus();
            return;

        }

        if(password.isEmpty()){
            signInPasswordEdidText.setError("Enter an email address");
            signInPasswordEdidText.requestFocus();
            return;

        }
        if(password.length()<6)
        {
            signInPasswordEdidText.setError("Minimum length of Password should be 6");
            signInPasswordEdidText.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.VISIBLE);

                if(task.isSuccessful()){
                    finish();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{

                    Toast.makeText(getApplicationContext(), "Login Unsuccessfull", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }


            }
        });

    }

}
