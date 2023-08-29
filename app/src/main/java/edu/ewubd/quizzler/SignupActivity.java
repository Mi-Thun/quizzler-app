package edu.ewubd.quizzler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {
    private EditText name, email, mobile, pass, confirmPass;
    private Button signupB;
    private ProgressBar progressBar;
    private TextView backB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.NameId);
        email = findViewById(R.id.EmailId);
        mobile = findViewById(R.id.MobileId);
        pass = findViewById(R.id.PasswordId);
        confirmPass = findViewById(R.id.RePasswordId);
        signupB = findViewById(R.id.SignUpButtonId);
        backB = findViewById(R.id.LogInTextId);

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, CategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}