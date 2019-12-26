package com.game.in2075;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.game.in2075.Retrofit.Json2075API;
import com.game.in2075.Retrofit.JsonClasses.FormReg;


public class MainActivity extends AppCompatActivity {

    private Json2075API jsonAPI;
    private TextView userTxt, passTxt;
    private Boolean userVerified = false;
    private Button logButt, regButt;
    Dialog myDialog;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { /** Close activity by receiving a broadcast signal */

        @Override
        public void onReceive(Context arg0, Intent intent) {
            String action = intent.getAction();
            if (action.equals("finish_activity")) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** New code from here */

        myDialog = new Dialog(this);

        logButt = findViewById(R.id.logButton);
        regButt = findViewById(R.id.regButton);
        userTxt = findViewById(R.id.userText);
        passTxt = findViewById(R.id.passText);

        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/2075App/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonAPI =  retrofit.create(Json2075API.class);
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
                    showWarning(new FormReg(), "Ey!" + "\n\n" + "Don't leave any field in blank!");
                else
                    logUser();
                //startDrawerMenu(); /** Solo para testeos, luego borrar!! */
            }
        });
    }

    private void registerUser(){
        regButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), RegisterActivity.class); /** Go to Register Activity */
                startActivityForResult(intent, 0);
            }
        });
    }

    //Log and Reg Callable Methods
    public void logUser(){
        final FormReg logUserData = new FormReg(userTxt.getText().toString(),passTxt.getText().toString());
        Call<FormReg> call = jsonAPI.setUserLog(logUserData);

        passTxt.setText("");

        call.enqueue(new Callback<FormReg>() {
            @Override
            public void onResponse(Call<FormReg> call, Response<FormReg> response) {

                if(!response.isSuccessful()){
                    switch (response.code()) {
                        case 403:
                            showWarning(logUserData, "Are you registered?" + "\n\n" + "Seems like user or password is wrong!");
                            break;
                        default:
                            showWarning(logUserData, "Oops!" + "\n\n" + "Seem something goes wrong :S");
                            break;
                    }
                    return;
                } else {
                    FormReg logResponse = response.body();
                    userVerified = true;
                    showWarning(logResponse,"");
                }
            }

            @Override
            public void onFailure(Call<FormReg> call, Throwable t) {
                Log.d("JSON", t.toString());
            }
        });
    }

    //Callable Layout for warnings. Now is used for login
    public void showWarning(FormReg u, String s){
        TextView closeWarning, warningMsg;
        myDialog.setContentView(R.layout.popup_messages);
        closeWarning = (TextView) myDialog.findViewById(R.id.closeTxt);
        warningMsg = (TextView) myDialog.findViewById(R.id.warningtTxt);

        if (userVerified)
            warningMsg.setText("Welcome back: " + u.getUsername() + "\n\n" + "We were missing you!");
        else
            warningMsg.setText(s);

        closeWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                if (userVerified) {
                    Intent intent = new Intent (v.getContext(), MainMenuActivity.class); /** Go to Main Nav. Menu Activy if login success*/
                    startActivityForResult(intent, 0);
                    userVerified = false;
                }
            }
        });
        myDialog.show();
    }

    /** -^- END LOGIN AND REGISTRATION -^- */
}
