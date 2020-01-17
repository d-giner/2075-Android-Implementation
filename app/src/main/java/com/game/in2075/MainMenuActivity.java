package com.game.in2075;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.game.in2075.Retrofit.JsonClasses.SharedData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenuActivity extends AppCompatActivity {

    private SharedData sharedData = SharedData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //We inicialize the bottom image_objects menu
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.ic_home:
                        break;
                    case R.id.ic_stats:
                        Intent intent1 = new Intent(getApplicationContext(), StatsActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        break;
                    case R.id.ic_inventory:
                        Intent intent2 = new Intent(getApplicationContext(), InventoryActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        break;
                    case R.id.ic_shop:
                        Intent intent3 = new Intent(getApplicationContext(), ShopActivity.class);
                        startActivity(intent3);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent1 = new Intent(this, SettingsActivity.class); /** Go to Register Activity */
                startActivityForResult(intent1, 0);
                return true;
            case R.id.action_logout:
                Toast.makeText(this, "See you soon soldier!", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
                //sharedData.getUserInventory().clear();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "See you soon soldier!", Toast.LENGTH_SHORT).show();
        finish();
    }

//    public boolean onTouchEvent(MotionEvent touchEvent){
//        switch (touchEvent.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                this.x1 = touchEvent.getX();
//                break;
//            case MotionEvent.ACTION_UP:
//                this.x2 = touchEvent.getX();
//                break;
//
//        }
//        if (x1 > x2){
//            Intent intent4 = new Intent(getApplicationContext(), StatsActivity.class);
//            startActivity(intent4);
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            this.finish();
//        }
//
//        return false;
//    }
}
