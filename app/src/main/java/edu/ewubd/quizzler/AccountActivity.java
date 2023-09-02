package edu.ewubd.quizzler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        TextView tvName = (TextView) findViewById(R.id.tvName);
        tvName.setText(sharedPreferences.getString("name", ""));

        TextView tvREmail = (TextView) findViewById(R.id.tvREmail);
        tvREmail.setText(sharedPreferences.getString("email", ""));

        TextView tvMobile = (TextView) findViewById(R.id.tvMobile);
        tvMobile.setText(sharedPreferences.getString("mobile", ""));

        LinearLayout profileTextView = findViewById(R.id.profile);
        profileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_edit_profile, null);

                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                popupView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

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

        findViewById(R.id.logoutB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isRememberMe", false);
                editor.apply();
                Intent i = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}