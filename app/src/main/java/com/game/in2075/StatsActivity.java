package com.game.in2075;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.game.in2075.Retrofit.JsonClasses.SharedData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StatsActivity extends AppCompatActivity {

    SharedData sharedData = SharedData.getInstance(); //Declaring the access to singletone

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        //We inicialize the bottom navigation menu
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ic_home:
                        Intent intent1 = new Intent(getApplicationContext(),MainMenuActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.ic_inventory:
                        Intent intent2 = new Intent(getApplicationContext(),InventoryActivity.class);
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.ic_shop:
                        Intent intent3 = new Intent(getApplicationContext(),ShopActivity.class);
                        startActivity(intent3);
                        finish();
                        break;
                }
                return false;
            }
        });

        //We set the user data
        TextView userDataTxt = findViewById(R.id.userDataText);
        userDataTxt.setText(sharedData.getUser().getName());

    }
}
