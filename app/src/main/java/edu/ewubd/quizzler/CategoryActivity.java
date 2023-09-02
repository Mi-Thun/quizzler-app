package edu.ewubd.quizzler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;


public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        GridView gridView = findViewById(R.id.gridView);
        List<GridItem> items = generateItems();

        GridAdapter gridAdapter = new GridAdapter(this, items);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Intent i = new Intent(CategoryActivity.this, StartTestActivity.class);
                startActivity(i);
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
                if (itemId == R.id.action_item2) {
                    Intent intent = new Intent(CategoryActivity.this, LeaderboardActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item2);
                    startActivity(intent);
                    return true;
                } else if (itemId == R.id.action_item3) {
                    Intent intent = new Intent(CategoryActivity.this, AccountActivity.class);
                    intent.putExtra("highlightItem", R.id.action_item3);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    private List<GridItem> generateItems() {
        List<GridItem> items = new ArrayList<>();
        items.add(new GridItem(R.drawable.java, "Java"));
        items.add(new GridItem(R.drawable.python, "Python"));
        items.add(new GridItem(R.drawable.c, "C++"));
        items.add(new GridItem(R.drawable.javascript, "JavaScript"));
        items.add(new GridItem(R.drawable.ruby, "Ruby"));
        items.add(new GridItem(R.drawable.swift, "Swift"));
        return items;
    }

    private void scheduleReminderNotification(Context context) {
        Intent notificationIntent = new Intent(context, MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                uniqueRequestCode(),
                notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        long nextNotificationMillis = System.currentTimeMillis() + 6 * 60 * 60 * 1000;

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextNotificationMillis, pendingIntent);
    }

    private int uniqueRequestCode() {
        return (int) System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        scheduleReminderNotification(getApplicationContext());
        super.onDestroy();
    }
}
