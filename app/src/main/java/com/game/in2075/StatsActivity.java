package com.game.in2075;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.game.in2075.Retrofit.JsonClasses.SharedData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StatsActivity extends AppCompatActivity {

    private SharedData sharedData = SharedData.getInstance(); //Declaring the access to singletone class
    private TextView userNameTxt, userIdTxt, nameUserTxt, userHpTxt, userAttackTxt, userDefTxt;
    private Button myGamesBttn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        getSupportActionBar().hide();

        //We inicialize the bottom image_objects menu
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
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                        break;
                    case R.id.ic_inventory:
                        Intent intent2 = new Intent(getApplicationContext(),InventoryActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        break;
                    case R.id.ic_shop:
                        Intent intent3 = new Intent(getApplicationContext(),ShopActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        break;
                }
                return false;
            }
        });

        //Setting the button
        myGamesBttn = findViewById(R.id.myGamesButton);
        myGamesBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),myGamesActivity.class);
                startActivity(intent);
            }
        });

        //We set the user data
        userNameTxt = findViewById(R.id.userNameText);
        userIdTxt = findViewById(R.id.userIdText);
        nameUserTxt = findViewById(R.id.nameUserText);
        userHpTxt = findViewById(R.id.userHpText);
        userAttackTxt = findViewById(R.id.userAttackText);
        userDefTxt = findViewById(R.id.userDefText);

        userNameTxt.setText(sharedData.getUser().getUsername());
        userIdTxt.setText("#" + String.valueOf(sharedData.getUser().getID()));
        nameUserTxt.setText(sharedData.getUser().getName());
        userHpTxt.setText(String.valueOf(sharedData.getUser().getHealthPoints()));
        userAttackTxt.setText(String.valueOf(sharedData.getUser().getAttack()));
        userDefTxt.setText(String.valueOf(sharedData.getUser().getDefense()));
    }

    @Override
    public void onBackPressed()
    {
        Intent intent1 = new Intent(getApplicationContext(),MainMenuActivity.class);
        startActivity(intent1);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
