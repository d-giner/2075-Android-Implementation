package com.game.in2075;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.game.in2075.Retrofit.JsonClasses.Obj;
import com.game.in2075.Retrofit.JsonClasses.SharedData;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.LinkedList;

public class ShopActivity extends AppCompatActivity {

    private TextView userMoneyTxt, shoppingCounterTxt, totalPriceTxt;
    private TextView itemTxt1, atkTxt1, defTxt1, priceTxt1;
    private TextView itemTxt2, atkTxt2, defTxt2, priceTxt2;
    private TextView itemTxt3, atkTxt3, defTxt3, priceTxt3;
    private TextView itemTxt4, atkTxt4, defTxt4, priceTxt4;
    private TextView itemTxt5, atkTxt5, defTxt5, priceTxt5;
    private ImageView ins1, ins2, ins3, ins4, ins5;
    private ImageView pur1, pur2, pur3, pur4, pur5;
    private Button confirmPurchaseBttn, cancelBttn;
    private SharedData sharedData = SharedData.getInstance();
    private LinkedList<Obj>  shoppingList = null;
    private Boolean obj1, obj2, obj3, obj4, obj5;
    private int userWallet = sharedData.getUser().getMoney();
    private int totalPurchasePrice = 0;
    private Dialog listDialog, confDialog, inspDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        getSupportActionBar().hide();

        //We inicialize the bottom image_objects menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavView_Bar);
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

        listDialog = new Dialog(this);
        confDialog = new Dialog(this);
        inspDialog =  new Dialog(this);

        //Variables to show the parameters of the items
        userMoneyTxt = findViewById(R.id.userMoneyText);
        shoppingCounterTxt = findViewById(R.id.shoppingListCounterText);
        totalPriceTxt = findViewById(R.id.totalPriceText);
        itemTxt1 = findViewById(R.id.itemText1);
        atkTxt1 = findViewById(R.id.atkText1);
        defTxt1 = findViewById(R.id.defText1);
        priceTxt1 = findViewById(R.id.priceText1);
        itemTxt2 = findViewById(R.id.itemText2);
        atkTxt2 = findViewById(R.id.atkText2);
        defTxt2 = findViewById(R.id.defText2);
        priceTxt2 = findViewById(R.id.priceText2);
        itemTxt3 = findViewById(R.id.itemText3);
        atkTxt3 = findViewById(R.id.atkText3);
        defTxt3 = findViewById(R.id.defText3);
        priceTxt3 = findViewById(R.id.priceText3);
        itemTxt4 = findViewById(R.id.itemText4);
        atkTxt4 = findViewById(R.id.atkText4);
        defTxt4 = findViewById(R.id.defText4);
        priceTxt4 = findViewById(R.id.priceText4);
        itemTxt5 = findViewById(R.id.itemText5);
        atkTxt5 = findViewById(R.id.atkText5);
        defTxt5 = findViewById(R.id.defText5);
        priceTxt5 = findViewById(R.id.priceText5);
        confirmPurchaseBttn = findViewById(R.id.confirmPurchaseButton);
        cancelBttn = findViewById(R.id.cancelButton);
        //Variables to inspect the improvements provided by the item on click
        ins1 = findViewById(R.id.inspect1);
        ins2 = findViewById(R.id.inspect2);
        ins3 = findViewById(R.id.inspect3);
        ins4 = findViewById(R.id.inspect4);
        ins5 = findViewById(R.id.inspect5);
        //We set the images of the items on the shop
        ins1.setImageResource(R.drawable.lightsaber);
        ins2.setImageResource(R.drawable.shield);
        ins3.setImageResource(R.drawable.helmet);
        ins4.setImageResource(R.drawable.coins);
        ins5.setImageResource(R.drawable.coins);
        //Variables to add the items into shopping list
        pur1 = findViewById(R.id.purchase1);
        pur2 = findViewById(R.id.purchase2);
        pur3 = findViewById(R.id.purchase3);
        pur4 = findViewById(R.id.purchase4);
        pur5 = findViewById(R.id.purchase5);
        obj1 = false;
        obj2 = false;
        obj3 = false;
        obj4 = false;
        obj5 = false;

        //Set the data on the scroll view of the shop
        userMoneyTxt.setText(String.valueOf(sharedData.getUser().getMoney()));
        itemTxt1.setText(sharedData.getShopItems().get(0).getObjName());
        atkTxt1.append(String.valueOf(sharedData.getShopItems().get(0).getObjAttack()));
        defTxt1.append(String.valueOf(sharedData.getShopItems().get(0).getObjDefense()));
        priceTxt1.append(String.valueOf(sharedData.getShopItems().get(0).getPrice()));
        itemTxt2.setText(sharedData.getShopItems().get(1).getObjName());
        atkTxt2.append(String.valueOf(sharedData.getShopItems().get(1).getObjAttack()));
        defTxt2.append(String.valueOf(sharedData.getShopItems().get(1).getObjDefense()));
        priceTxt2.append(String.valueOf(sharedData.getShopItems().get(1).getPrice()));
        itemTxt3.setText(sharedData.getShopItems().get(2).getObjName());
        atkTxt3.append(String.valueOf(sharedData.getShopItems().get(2).getObjAttack()));
        defTxt3.append(String.valueOf(sharedData.getShopItems().get(2).getObjDefense()));
        priceTxt3.append(String.valueOf(sharedData.getShopItems().get(2).getPrice()));
        shoppingCounterTxt.setVisibility(View.GONE);

        //We set the behavioural of the images On Click
        ins1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView closeWarning, warningMsg;
                listDialog.setContentView(R.layout.inspect_item_messages);
                closeWarning = listDialog.findViewById(R.id.closeTxt);
                warningMsg = listDialog.findViewById(R.id.warningtTxt);
                warningMsg.setText("The Lightsaber increases" + "\n\n");
                int a = sharedData.getShopItems().get(0).getObjAttack() + sharedData.getUser().getAttack();
                warningMsg.append("your attack from: " + sharedData.getUser().getAttack() + " to: " + a + "\n");
                warningMsg.append("\n" + "I would recommend that you always carry one with you!");

                closeWarning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDialog.dismiss();
                    }
                });
                listDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                listDialog.show();
            }
        });

        ins2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView closeWarning, warningMsg;
                listDialog.setContentView(R.layout.inspect_item_messages);
                closeWarning = listDialog.findViewById(R.id.closeTxt);
                warningMsg = listDialog.findViewById(R.id.warningtTxt);
                warningMsg.setText("The Great Shield increases" + "\n\n");
                int a = sharedData.getShopItems().get(1).getObjDefense() + sharedData.getUser().getDefense();
                warningMsg.append("your defense from: " + sharedData.getUser().getDefense() + " to: " + a + "\n");
                warningMsg.append("\n" + "Could you survive without one?");

                closeWarning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDialog.dismiss();
                    }
                });
                listDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                listDialog.show();
            }
        });

        ins3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView closeWarning, warningMsg;
                listDialog.setContentView(R.layout.inspect_item_messages);
                closeWarning = listDialog.findViewById(R.id.closeTxt);
                warningMsg = listDialog.findViewById(R.id.warningtTxt);
                warningMsg.setText("The Helmet increases" + "\n\n");
                int a = sharedData.getShopItems().get(2).getObjDefense() + sharedData.getUser().getDefense();
                warningMsg.append("your defense from: " + sharedData.getUser().getDefense() + " to: " + a + "\n");
                warningMsg.append("\n" + "Protect your ideas!");

                closeWarning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listDialog.dismiss();
                    }
                });
                listDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                listDialog.show();
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

                else if (!obj1 && userWallet - totalPurchasePrice > sharedData.getShopItems().get(0).getPrice()){
                    Toast.makeText(getApplicationContext(),"Lightsaber added to shopping list.",Toast.LENGTH_SHORT).show();
                    shoppingList.add(sharedData.getShopItems().get(0));
                    totalPurchasePrice+=sharedData.getShopItems().get(0).getPrice();
                    totalPriceTxt.setText("Total: " + String.valueOf(totalPurchasePrice));
                    shoppingCounterTxt.setText(String.valueOf(shoppingList.size()));
                    shoppingCounterTxt.setVisibility(View.VISIBLE);
                    obj1 = true;
                }
                else if (obj1)
                    Toast.makeText(getApplicationContext(),"You already have a Lightsaber in your shopping list.",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"You do not have enough money." + "\n\n" + "Play to gain it!",Toast.LENGTH_SHORT).show();
            }
        });

        pur2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedData.getGreatShield())
                    Toast.makeText(getApplicationContext(),"You already have a Great Shield in your inventory.",Toast.LENGTH_SHORT).show();

                else if (!obj2 && userWallet - totalPurchasePrice > sharedData.getShopItems().get(1).getPrice()){
                    Toast.makeText(getApplicationContext(),"Great Shield added to shopping list.",Toast.LENGTH_SHORT).show();
                    shoppingList.add(sharedData.getShopItems().get(1));
                    totalPurchasePrice+=sharedData.getShopItems().get(1).getPrice();
                    totalPriceTxt.setText("Total: " + String.valueOf(totalPurchasePrice));
                    shoppingCounterTxt.append(String.valueOf(shoppingList.size()));
                    shoppingCounterTxt.setVisibility(View.VISIBLE);
                    obj2 = true;
                }
                else if (obj2)
                    Toast.makeText(getApplicationContext(),"You already have a Great Shield in your shopping list.",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"You do not have enough money." + "\n\n" + "Play to gain it!",Toast.LENGTH_SHORT).show();

            }
        });

        pur3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedData.getHelmet())
                    Toast.makeText(getApplicationContext(),"You already have a Helmet in your inventory.",Toast.LENGTH_SHORT).show();

                else if (!obj3 && userWallet - totalPurchasePrice > sharedData.getShopItems().get(2).getPrice()){
                    Toast.makeText(getApplicationContext(),"Helmet added to shopping list.",Toast.LENGTH_SHORT).show();
                    shoppingList.add(sharedData.getShopItems().get(2));
                    totalPurchasePrice+=sharedData.getShopItems().get(2).getPrice();
                    totalPriceTxt.setText("Total: " + String.valueOf(totalPurchasePrice));
                    shoppingCounterTxt.setText(String.valueOf(shoppingList.size()));
                    shoppingCounterTxt.setVisibility(View.VISIBLE);
                    obj3 = true;
                }
                else if (obj3)
                    Toast.makeText(getApplicationContext(),"You already have a Helmet in your shopping list.",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"You do not have enough money." + "\n\n" + "Play to gain it!",Toast.LENGTH_SHORT).show();

            }
        });

        pur4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (sharedData.getXXXX())
//                    Toast.makeText(getApplicationContext(),"You already have a XXXX in your inventory.",Toast.LENGTH_SHORT).show();
//
//                else if (!obj4 && userWallet - totalPurchasePrice > sharedData.getShopItems().get(3).getPrice()){
//                    Toast.makeText(getApplicationContext(),"XXXX added to shopping list.",Toast.LENGTH_SHORT).show();
//                    shoppingList.add(sharedData.getShopItems().get(3));
//                    totalPurchasePrice+=sharedData.getShopItems().get(3).getPrice();
                //        shoppingCounterTxt.setText(String.valueOf(shoppingList.size()));
                //totalPriceTxt.setText("Total: " + String.valueOf(totalPurchasePrice));
                //shoppingCounterTxt.setVisibility(View.VISIBLE);
//                    obj4 = true;
//                }
//                else if (obj4)
//                    Toast.makeText(getApplicationContext(),"You already have a XXXX in your shopping list.",Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(getApplicationContext(),"You do not have enought money." + "\n\n" + "Play to gain it!",Toast.LENGTH_SHORT).show();
            }
        });

        pur5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (sharedData.getXXXX())
//                    Toast.makeText(getApplicationContext(),"You already have a XXXX in your inventory.",Toast.LENGTH_SHORT).show();
//
//                else if (!obj5 && userWallet - totalPurchasePrice > sharedData.getShopItems().get(4).getPrice()){
//                    Toast.makeText(getApplicationContext(),"XXXX added to shopping list.",Toast.LENGTH_SHORT).show();
//                    shoppingList.add(sharedData.getShopItems().get(4));
//                    totalPurchasePrice+=sharedData.getShopItems().get(4).getPrice();
                //        shoppingCounterTxt.setText(String.valueOf(shoppingList.size()));
                //totalPriceTxt.setText("Total: " + String.valueOf(totalPurchasePrice));
                //shoppingCounterTxt.setVisibility(View.VISIBLE);
//                    obj5 = true;
//                }
//                else if (obj5)
//                    Toast.makeText(getApplicationContext(),"You already have a XXXX in your shopping list.",Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(getApplicationContext(),"You do not have enought money." + "\n\n" + "Play to gain it!",Toast.LENGTH_SHORT).show();
            }
        });

        shoppingCounterTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shoppingList.size()>0) {
                    TextView closeWarning, warningMsg;
                    listDialog.setContentView(R.layout.shoplist_messages);
                    closeWarning = listDialog.findViewById(R.id.closeTxt);
                    warningMsg = listDialog.findViewById(R.id.warningtTxt);
                    warningMsg.setText("Shopping cart:" + "\n");
                    for (Obj o : shoppingList){
                        warningMsg.append("-" + o.getObjName() + "\n");
                    }

                    closeWarning.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            listDialog.dismiss();
                        }
                    });
                    listDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    listDialog.show();
                }
            }
        });

        //Send the shopping list to the backend  if the user confirms the purchase
        confirmPurchaseBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //What to do if cancel button is pressed
        cancelBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shoppingList.size()>0) {
                    shoppingList.clear();
                    totalPurchasePrice = 0;
                    totalPriceTxt.setText("Total: " + String.valueOf(totalPurchasePrice));
                    shoppingCounterTxt.setText(String.valueOf(shoppingList.size()));
                    shoppingCounterTxt.setVisibility(View.GONE);
                    obj1 = false;
                    obj2 = false;
                    obj3 = false;
                    obj4 = false;
                    obj5 = false;
                    Toast.makeText(getApplicationContext(), "The purchase has been canceled.", Toast.LENGTH_SHORT).show();
                }
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
