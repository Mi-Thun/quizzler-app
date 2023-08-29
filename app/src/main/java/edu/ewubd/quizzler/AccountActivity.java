package edu.ewubd.quizzler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);


        int highlightedItemId = getIntent().getIntExtra("highlightItem", 0);
        if (highlightedItemId != 0) {
            bottomNavigationView.setSelectedItemId(highlightedItemId);
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.action_item1) {
                    Intent intent = new Intent(AccountActivity.this, CategoryActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item1);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.action_item2) {
                    Intent intent = new Intent(AccountActivity.this, LeaderboardActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item2);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}