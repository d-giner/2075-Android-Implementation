package com.game.in2075;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //We inicialize the bottom navigation menu
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ic_home:
                        Intent intent3 = new Intent(getApplicationContext(),MainMenuActivity.class);
                        startActivity(intent3);
                        finish();
                        break;
                    case R.id.ic_stats:
                        Intent intent1 = new Intent(getApplicationContext(),StatsActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.ic_inventory:
                        Intent intent2 = new Intent(getApplicationContext(),InventoryActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                }
                return false;
            }
        });
    }
}
