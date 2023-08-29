package edu.ewubd.quizzler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartTestActivity extends AppCompatActivity {
    Button start_testB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        start_testB = findViewById(R.id.start_testB);

        start_testB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StartTestActivity.this, QuestionsActivity.class);
                startActivity(i);
            }
        });

    }
}