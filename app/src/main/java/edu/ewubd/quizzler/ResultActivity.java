package edu.ewubd.quizzler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ResultActivity extends AppCompatActivity {
    private TextView tvScore, tvCorrect, tvWrong, tvNotAnswer, totalquestion, timeneed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tvScore = findViewById(R.id.tvScore);
        tvCorrect = findViewById(R.id.tvCorrect);
        tvWrong = findViewById(R.id.tvWrong);
        tvNotAnswer = findViewById(R.id.tvNotAnswer);
        totalquestion = findViewById(R.id.totalquestion);
        timeneed = findViewById(R.id.timeneed);

        tvScore.setText(String.valueOf(getIntent().getIntExtra("SCORE", -1)));
        tvCorrect.setText(String.valueOf(getIntent().getIntExtra("correct", -1)));
        tvWrong.setText(String.valueOf(getIntent().getIntExtra("wrong", -1)));
        tvNotAnswer.setText(String.valueOf(getIntent().getIntExtra("not_answer", -1)));
        totalquestion.setText(String.valueOf(getIntent().getIntExtra("correct", -1) +
                getIntent().getIntExtra("wrong", -1) +
                getIntent().getIntExtra("not_answer", -1)));
        timeneed.setText(String.format("%d Minutes", getIntent().getLongExtra("time", -1) / (60 * 1000)));

        findViewById(R.id.btnReturnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, CategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, CategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_item2) {
                    Intent intent = new Intent(ResultActivity.this, LeaderboardActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item2);
                    startActivity(intent);
                    finish();
                    return true;
                } else if (itemId == R.id.action_item3) {
                    Intent intent = new Intent(ResultActivity.this, AccountActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item3);
                    startActivity(intent);
                    finish();
                    return true;
                }
                else if (itemId == R.id.action_item1) {
                    Intent intent = new Intent(ResultActivity.this, CategoryActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item1);
                    startActivity(intent);
                    finish();
                    return true;
                }
                return false;
            }
        });
    }
}