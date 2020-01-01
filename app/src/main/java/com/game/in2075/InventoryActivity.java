package com.game.in2075;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.game.in2075.Retrofit.JsonClasses.DataAdapterGames;
import com.game.in2075.Retrofit.JsonClasses.DataAdapterInventory;
import com.game.in2075.Retrofit.JsonClasses.Game;
import com.game.in2075.Retrofit.JsonClasses.Obj;
import com.game.in2075.Retrofit.JsonClasses.SharedData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventoryActivity extends AppCompatActivity {

    private SharedData sharedData = SharedData.getInstance();
    private TextView errorInfoTxt;
    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        getSupportActionBar().hide();

        errorInfoTxt = findViewById(R.id.errorInfoText);
        recyclerList = findViewById(R.id.myRecycler);

        recyclerList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        //We inicialize the bottom image_objects menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ic_home:
                        Intent intent2 = new Intent(getApplicationContext(),MainMenuActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                        break;
                    case R.id.ic_stats:
                        Intent intent1 = new Intent(getApplicationContext(),StatsActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

        getInventory();
    }

    public void getInventory(){
        Call<List<Obj>> call = sharedData.useRetrofit().getUserInventory(sharedData.getUser().getID());
        call.enqueue(new Callback<List<Obj>>() {

            @Override
            public void onResponse(Call<List<Obj>> call, Response<List<Obj>> response) {

                if(!response.isSuccessful()){
                    switch (response.code()) {
                        case 404:
                            errorInfoTxt.setText("Player ID not found.");
                            break;
                        default:
                            errorInfoTxt.setText("Oops!" + "\n\n" + "Seem something goes wrong :S");
                            break;
                    }
                    return;
                } else {
                    List<Obj> myInventory = response.body();
                    //errorInfoTxt.setText(myInventory.get(0).getObjName());
                    recyclerList.setAdapter(new DataAdapterInventory(myInventory));
                }
            }

            @Override
            public void onFailure(Call<List<Obj>> call, Throwable t) {
                Log.d("JSON", t.toString());
            }
        });

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
