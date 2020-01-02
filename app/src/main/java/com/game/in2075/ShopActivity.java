package com.game.in2075;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.game.in2075.Retrofit.JsonClasses.Obj;
import com.game.in2075.Retrofit.JsonClasses.SharedData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;

public class ShopActivity extends AppCompatActivity {

    private TextView atkTxt1, priceTxt1;
    private TextView defTxt2, priceTxt2;
    private TextView defTxt3, priceTxt3;
    private ImageView ins1, ins2, ins3, ins4;
    private ImageView pur1, pur2, pur3, pur4;
    private SharedData sharedData = SharedData.getInstance();
    private LinkedList<Obj>  shoppingList = null;
    private Boolean obj1, obj2, obj3, obj4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        getSupportActionBar().hide();

        //We inicialize the bottom image_objects menu
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
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                        break;
                    case R.id.ic_stats:
                        Intent intent1 = new Intent(getApplicationContext(),StatsActivity.class);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                        break;
                    case R.id.ic_inventory:
                        Intent intent2 = new Intent(getApplicationContext(),InventoryActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
                        break;
                }
                return false;
            }
        });

        shoppingList = new LinkedList<>();

        atkTxt1 = findViewById(R.id.atkText1);
        priceTxt1 = findViewById(R.id.priceText1);
        defTxt2 = findViewById(R.id.defText2);
        priceTxt2 = findViewById(R.id.priceText2);
        defTxt3 = findViewById(R.id.defText3);
        priceTxt3 = findViewById(R.id.priceText3);
        ins1 = findViewById(R.id.inspect1);
        ins2 = findViewById(R.id.inspect2);
        ins3 = findViewById(R.id.inspect3);
        ins4 = findViewById(R.id.inspect4);
        pur1 = findViewById(R.id.purchase1);
        pur2 = findViewById(R.id.purchase2);
        pur3 = findViewById(R.id.purchase3);
        pur4 = findViewById(R.id.purchase4);
        obj1 = false;
        obj2 = false;
        obj2 = false;
        obj2 = false;

        atkTxt1.append(String.valueOf(sharedData.getUserInventory().get(0).getObjAttack()));
        priceTxt1.append(String.valueOf(sharedData.getUserInventory().get(0).getPrice()));
        defTxt2.append(String.valueOf(sharedData.getUserInventory().get(1).getObjDefense()));
        priceTxt2.append(String.valueOf(sharedData.getUserInventory().get(1).getPrice()));
        //defTxt3.append(String.valueOf(sharedData.getUserInventory().get(2).getObjDefense()));
        //priceTxt3.append(String.valueOf(sharedData.getUserInventory().get(2).getPrice()));

        ins1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ins2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ins3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ins4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        pur1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedData.getLightsaber())
                    Toast.makeText(getApplicationContext(),"You already have a Lightsaber in your inventory.",Toast.LENGTH_SHORT).show();

                else if (!obj1){
                    Toast.makeText(getApplicationContext(),"Lightsaber added to shopping list.",Toast.LENGTH_SHORT).show();
                    shoppingList.add(sharedData.getUserInventory().get(0)); //AQUÍ TIENE QUE IR LA LISTA SHOP!!!! NO LA DEL USUARIO
                    obj1 = true;
                }
                else
                    Toast.makeText(getApplicationContext(),"You already have a Lightsaber in your shopping list.",Toast.LENGTH_SHORT).show();
            }
        });

        pur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedData.getGreatShield())
                    Toast.makeText(getApplicationContext(),"You already have a Great Shield in your inventory.",Toast.LENGTH_SHORT).show();

                else if (!obj2){
                    Toast.makeText(getApplicationContext(),"Great Shield added to shopping list.",Toast.LENGTH_SHORT).show();
                    shoppingList.add(sharedData.getUserInventory().get(1));
                    obj2 = true;
                }
                else
                    Toast.makeText(getApplicationContext(),"You already have a Great Shield in your shopping list.",Toast.LENGTH_SHORT).show();
            }
        });

        pur3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedData.getHelmet())
                    Toast.makeText(getApplicationContext(),"You already have a Helmet in your inventory.",Toast.LENGTH_SHORT).show();
                else if(!obj3){
                    Toast.makeText(getApplicationContext(),"Helmet added to shopping list.",Toast.LENGTH_SHORT).show();
                    shoppingList.add(sharedData.getUserInventory().get(2));
                    obj3 = true;
                }
                else
                    Toast.makeText(getApplicationContext(),"You already have a Helmet in your shopping list.",Toast.LENGTH_SHORT).show();
            }
        });

        pur4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedData.getHelmet())
                    Toast.makeText(getApplicationContext(),"You already have a XXX in your inventory.",Toast.LENGTH_SHORT).show();
                else if(!obj4){
                    Toast.makeText(getApplicationContext(),"XXX added to shopping list.",Toast.LENGTH_SHORT).show();
                    //shoppingList.add(sharedData.getUserInventory().get(3));
                    obj4 = true;
                }
                else
                    Toast.makeText(getApplicationContext(),"You already have a XXX in your shopping list.",Toast.LENGTH_SHORT).show();

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
