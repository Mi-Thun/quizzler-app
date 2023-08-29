package edu.ewubd.quizzler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        GridView gridView = findViewById(R.id.gridView);
        List<GridItem> items = generateItems(); // Implement this method to generate items

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
        items.add(new GridItem(R.drawable.quize2, "Item 1"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize2, "Item 1"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize_2, "Item 3"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize_2, "Item 3"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize_2, "Item 3"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize_2, "Item 3"));
        items.add(new GridItem(R.drawable.quize2, "Item 1"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize2, "Item 1"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize_2, "Item 3"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize_2, "Item 3"));
        items.add(new GridItem(R.drawable.quize2, "Item 1"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize2, "Item 1"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize_2, "Item 3"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize_2, "Item 3"));
        items.add(new GridItem(R.drawable.quize_1, "Item 2"));
        items.add(new GridItem(R.drawable.quize_2, "Item 3"));
        return items;
    }
}
