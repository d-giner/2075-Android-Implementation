package com.game.in2075;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.game.in2075.Retrofit.JsonClasses.FormReg;
import com.game.in2075.Retrofit.JsonClasses.Obj;
import com.game.in2075.Retrofit.JsonClasses.SharedData;
import com.game.in2075.Retrofit.JsonClasses.UserTO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.LinkedList;


public class LoginActivity extends AppCompatActivity {

    private TextView userTxt, passTxt, errorInfoTxt;
    private ImageView creator;
    private Button logButt, regButt;
    private Dialog myDialog;
    private SharedData sharedData = SharedData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide(); //Hide the action bar.

        myDialog = new Dialog(this);

        logButt = findViewById(R.id.logButton);
        regButt = findViewById(R.id.regButton);
        userTxt = findViewById(R.id.userText);
        passTxt = findViewById(R.id.passText);
        creator = findViewById(R.id.imageLogoView);
        errorInfoTxt = findViewById(R.id.errorInfoText);

        creator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Created by: daniel.giner.gil@estudiant.upc.edu",Toast.LENGTH_SHORT).show();
            }
        });

        loginUser(); //We let ready the buttons to execute the functions on click
        registerUser();
    }

    //Actions of login buttons
    private void loginUser(){
        logButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = userTxt.getText().toString();
                String b = passTxt.getText().toString();

                if (a.equals("") || b.equals(""))
                    showWarning("Hey!" + "\n\n" + "Don't leave any field in blank!");
                else
                    logUser();
            }
        });
    }

    //Acces to the register new user activity
    private void registerUser(){
        regButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), RegisterActivity.class); /** Go to Register Activity */
                startActivityForResult(intent, 0);
                userTxt.setText("");
                passTxt.setText("");
            }
        });
    }

    //Log and Reg Callable Methods
    public void logUser(){
        final FormReg logUserData = new FormReg(userTxt.getText().toString(),passTxt.getText().toString());
        Call<JsonElement> call = sharedData.useRetrofit().setUserLog(logUserData);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                JsonElement questions = response.body();

                if(!response.isSuccessful()){
                    switch (response.code()) {
                        case 403:
                            showWarning("Are you registered?" + "\n\n" + "Seems like user or password is wrong!");
                            break;
                        default:
                            showWarning("Oops!" + "\n\n" + "Seem something goes wrong :S");
                            break;
                    }
                    return;
                } else if (response.code() == 200){
                    UserTO userResponse = new Gson().fromJson(questions.getAsJsonObject(), UserTO.class); //Obtain the JsonObject and transform to type UserTO
                    goIn(userResponse);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("JSON", t.toString());
            }
        });
    }

    //Function that starts next activity in case of successful login
    public void goIn(UserTO u){
        Toast.makeText(this, "Welcome back " + u.getUsername() + "\n\n" + "We were missing you!", Toast.LENGTH_SHORT).show();
        sharedData.setUser(u);
        getInventory();
        loadShopItems();
        Intent intent = new Intent (this, MainMenuActivity.class); /** Go to Main Nav. Menu Activy if login success*/
        startActivityForResult(intent, 0);
        finish();
    }

    //Requesting the user inventory to backend
    public void getInventory(){
        Call<LinkedList<Obj>> call = sharedData.useRetrofit().getUserInventory(sharedData.getUser().getID());
        call.enqueue(new Callback<LinkedList<Obj>>() {

            @Override
            public void onResponse(Call<LinkedList<Obj>> call, Response<LinkedList<Obj>> response) {

                if(!response.isSuccessful()){
                    switch (response.code()) {
                        case 404:
                            sharedData.initializeItemBools(); //This player do not has items, so we need to initialize some things
                            break;
                        default:
                            errorInfoTxt.setText("Oops!" + "\n\n" + "Seem something goes wrong :S");
                            break;
                    }
                    return;
                } else if (response.code() == 201) {
                    LinkedList<Obj> myInventory = response.body();
                        sharedData.setUserInventory(myInventory); //Saving the user inventory in sharedData singletone class
                }
            }

            @Override
            public void onFailure(Call<LinkedList<Obj>> call, Throwable t) {
                Log.d("JSON", t.toString());
            }
        });
    }

    public void loadShopItems(){
        LinkedList<Obj> itemsList = new LinkedList<>();
        itemsList.add(new Obj(String.valueOf(sharedData.getUser().getID()), "Lightsaber", 120, 0, 100, 100));
        itemsList.add(new Obj(String.valueOf(sharedData.getUser().getID()), "Great Shield", 0, 50, 50, 100));
        itemsList.add(new Obj(String.valueOf(sharedData.getUser().getID()), "Helmet", 0, 10, 10, 100));
        itemsList.add(new Obj(String.valueOf(sharedData.getUser().getID()), "Oxygen Bottle", 0, 0, 5, 100));
        itemsList.add(new Obj(String.valueOf(sharedData.getUser().getID()), "Wingman", 350, 0, 4999, 1000));
        sharedData.setShopItems(itemsList);
    }


    //Callable Layout for warnings.
    public void showWarning(String s){
        TextView closeWarning, warningMsg;
        myDialog.setContentView(R.layout.popup_messages);
        closeWarning = myDialog.findViewById(R.id.closeTxt);
        warningMsg = myDialog.findViewById(R.id.warningtTxt1);
        warningMsg.setText(s);

        closeWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        myDialog.show();
    }
}
