package edu.ewubd.quizzler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StartTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);

        String categoryName = getIntent().getStringExtra("categoryName");

        findViewById(R.id.start_testB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StartTestActivity.this, QuestionsActivity.class);
                i.putExtra("categoryName", categoryName);
                startActivity(i);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_item2) {
                    Intent intent = new Intent(StartTestActivity.this, LeaderboardActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item2);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.action_item3) {
                    Intent intent = new Intent(StartTestActivity.this, AccountActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item3);
                    startActivity(intent);
                    return true;
                }
                else if (itemId == R.id.action_item1) {
                    Intent intent = new Intent(StartTestActivity.this, CategoryActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item1);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}