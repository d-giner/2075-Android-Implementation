package com.game.in2075;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.game.in2075.Retrofit.JsonClasses.FormReg;
import com.game.in2075.Retrofit.JsonClasses.SharedData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {


    private Button regBttn, cancelBttn;
    private TextView userTxt, passTxt, nameTxt;
    private SharedData sharedData = SharedData.getInstance();
    private Dialog msgDialog;
    Boolean userVerified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        regBttn = findViewById(R.id.regButton);
        cancelBttn = findViewById(R.id.cancelButton);
        userTxt = findViewById(R.id.oldPassText);
        passTxt = findViewById(R.id.passText);
        nameTxt = findViewById(R.id.nameText);

        msgDialog = new Dialog(this);

        cancelRegister();
        registerUser();
    }

    public void cancelRegister(){
        cancelBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void registerUser(){
        regBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = userTxt.getText().toString();
                String b = passTxt.getText().toString();
                String c = nameTxt.getText().toString();

                if (a.equals("") || b.equals("") || c.equals(""))
                    showWarning(new FormReg(), "Ey!" + "\n\n" + "Don't leave any field in blank!");
                else
                    regUser();
            }
        });
    }

    public void regUser(){
        final FormReg regUserData = new FormReg(userTxt.getText().toString(),passTxt.getText().toString(), nameTxt.getText().toString());
        Call<FormReg> call = sharedData.useRetrofit().setUserReg(regUserData);

        call.enqueue(new Callback<FormReg>() {
            @Override
            public void onResponse(Call<FormReg> call, Response<FormReg> response) {

                if(!response.isSuccessful()) {
                    switch (response.code()) {
                        case 400:
                            showWarning(regUserData, "Please check your information." + "\n\n" + "Don't leave any field in blank!");
                            break;
                        case 406:
                            showWarning(regUserData, "The user already exists!" + "\n\n" + "What about: " + regUserData.getUsername() + "2075" + "\n\n" + "Hehe :P");
                            break;
                        default:
                            showWarning(regUserData, "Oops!" + "\n\n" + "Seem something goes wrong :S");
                            break;
                    }
                    return;
                }
                 else {
                    FormReg regResponse = response.body();
                    userVerified = true;
                    showWarning(regResponse, "");
                }
            }

            @Override
            public void onFailure(Call<FormReg> call, Throwable t) {
                Log.d("JSON", t.toString());
            }
        });
    }

    //Callable Layout for warnings. Now is used for background_main_menu
    public void showWarning(FormReg u, String s){
        TextView closeWarning, warningMsg;
        msgDialog.setContentView(R.layout.popup_messages);
        closeWarning = (TextView) msgDialog.findViewById(R.id.closeTxt);
        warningMsg = (TextView) msgDialog.findViewById(R.id.warningtTxt);

        if (userVerified)
            warningMsg.setText("Welcome: " + u.getUsername() + "!" + "\n\n" + "The universe was waiting for you!");
        else
        {
            warningMsg.setText(s);
            userTxt.setText("");
            passTxt.setText("");
            nameTxt.setText("");
        }

        closeWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgDialog.dismiss();
                if (userVerified) {
                    finish();
                }
            }
        });
        msgDialog.show();
    }
}
