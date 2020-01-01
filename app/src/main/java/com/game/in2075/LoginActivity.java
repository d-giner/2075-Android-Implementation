package com.game.in2075;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.game.in2075.Retrofit.JsonClasses.FormReg;
import com.game.in2075.Retrofit.JsonClasses.SharedData;
import com.game.in2075.Retrofit.JsonClasses.UserTO;
import com.google.gson.Gson;
import com.google.gson.JsonElement;


public class LoginActivity extends AppCompatActivity {

    private TextView userTxt, passTxt;
    private Boolean userVerified = false;
    private Button logButt, regButt;
    private Dialog myDialog;
    private SharedData sharedData = SharedData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /** New code from here */

        getSupportActionBar().hide();

        myDialog = new Dialog(this);

        logButt = findViewById(R.id.logButton);
        regButt = findViewById(R.id.regButton);
        userTxt = findViewById(R.id.userText);
        passTxt = findViewById(R.id.passText);

        loginUser(); //We let ready the buttons to execute the functions on click
        registerUser();
    }

    //Actions of buttons
    private void loginUser(){
        logButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = userTxt.getText().toString();
                String b = passTxt.getText().toString();

                if (a.equals("") || b.equals(""))
                    showWarning(new UserTO(), "Ey!" + "\n\n" + "Don't leave any field in blank!");
                else
                    logUser();
            }
        });
    }

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
                            showWarning(new UserTO(), "Are you registered?" + "\n\n" + "Seems like user or password is wrong!");
                            break;
                        default:
                            showWarning(new UserTO(), "Oops!" + "\n\n" + "Seem something goes wrong :S");
                            break;
                    }
                    return;
                } else {
                    UserTO userResponse = new Gson().fromJson(questions.getAsJsonObject(), UserTO.class); //Obtain the JsonObject and transform to type UserTO
                    //userVerified = true;
                    goIn(userResponse);
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d("JSON", t.toString());
            }
        });
    }

    //Callable Layout for warnings. Now is used for login
    public void showWarning(UserTO u, String s){
        TextView closeWarning, warningMsg;
        final UserTO auxUser = u;
        myDialog.setContentView(R.layout.popup_messages);
        closeWarning = (TextView) myDialog.findViewById(R.id.closeTxt);
        warningMsg = (TextView) myDialog.findViewById(R.id.warningtTxt);

//        if (userVerified && u != null) {
//            //warningMsg.setText("Welcome back: " + u.getUsername() + "\n\n" + "We were missing you!");
//        }
//        else
            warningMsg.setText(s);

        closeWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
//                if (userVerified) {
//                    Intent intent = new Intent (v.getContext(), MainMenuActivity.class); /** Go to Main Nav. Menu Activy if login success*/
//                    startActivityForResult(intent, 0);
//                    sharedData.setUser(auxUser);
//                    userVerified = false;
//                    finish();
//                }
            }
        });
        myDialog.show();
    }

    public void goIn(UserTO u){
        Toast.makeText(this, "Welcome back " + u.getUsername() + "\n\n" + "We were missing you!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent (this, MainMenuActivity.class); /** Go to Main Nav. Menu Activy if login success*/
        startActivityForResult(intent, 0);
        sharedData.setUser(u);
        //userVerified = false;
        finish();
    }

    /** -^- END LOGIN AND REGISTRATION -^- */

}
