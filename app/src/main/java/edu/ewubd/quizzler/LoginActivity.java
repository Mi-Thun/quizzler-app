package edu.ewubd.quizzler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private EditText email, pass;
    private Button loginB;
    private TextView forgetPassB, signupB;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailId);
        progressBar = findViewById(R.id.pbProgress);
        pass = findViewById(R.id.passwordId);
        loginB = findViewById(R.id.signInId);
        signupB = findViewById(R.id.SignupTextId);
        forgetPassB = findViewById(R.id.forgotPassword);
        rememberMe = findViewById(R.id.rememberMe);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData()){
                    login();
                }
            }
        });

        signupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    private boolean validateData(){
        boolean status = false;
        if(email.getText().toString().isEmpty()){
            email.setError("Enter Email ID");
            return false;
        }
        if(pass.getText().toString().isEmpty()){
            pass.setError("Enter password");
            return false;
        }
        return true;
    }
    private void login(){

    }
}