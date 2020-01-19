package com.game.in2075;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.game.in2075.Retrofit.JsonClasses.SharedData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {

    private Button updateBttn, cancelBttn;
    private TextView oldPassTxt, newPassTxt;
    private SharedData sharedData = SharedData.getInstance();
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Dialog msgDialog;
    Boolean userVerified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        getSupportActionBar().hide();

        msgDialog = new Dialog(this);
        msgDialog.setCancelable(false);
        msgDialog.setCanceledOnTouchOutside(false);

        updateBttn = findViewById(R.id.updateButton);
        cancelBttn = findViewById(R.id.cancelButton);
        oldPassTxt = findViewById(R.id.oldPassText);
        newPassTxt = findViewById(R.id.newPassText);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPref.edit();

        cancelBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = oldPassTxt.getText().toString();
                String b = newPassTxt.getText().toString();

                if (a.equals("") || b.equals(""))
                    showWarning("Hey!" + "\n\n" + "Don't leave any field in blank!");
                else if (!a.equals(sharedData.getUser().getPassword()))
                    showWarning("Hey!" + "\n\n" + "You wrote wrong your old password!");
                else {
                    sharedData.getUser().setPassword(b);
                    editor.putString("password",b);
                    editor.commit();
                    updatePasswordUser();
                }
            }
        });
    }

    public void updatePasswordUser(){
        Call<Void> call = sharedData.useRetrofit().updateUserPassword(sharedData.getUser());

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()) {
                    switch (response.code()) {
                        case 400:
                            showWarning("Please check your information." + "\n\n" + "Don't leave any field in blank!");
                            break;
                        default:
                            showWarning("Oops!" + "\n\n" + "Seem something goes wrong :S");
                            break;
                    }
                    return;
                }
                else if (response.code() == 200){
                    userVerified = true;
                    showWarning("Your password has been updated correctly.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("JSON", t.toString());
            }
        });
    }

    //Callable Layout for warnings
    public void showWarning(String s){
        TextView closeWarning, warningMsg;
        msgDialog.setContentView(R.layout.popup_messages);
        closeWarning = msgDialog.findViewById(R.id.closeTxt);
        warningMsg = msgDialog.findViewById(R.id.warningtTxt1);
        oldPassTxt.setText("");
        newPassTxt.setText("");

        if (userVerified){
            warningMsg.setText(s);
        }
        else
            warningMsg.setText(s);

        closeWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgDialog.dismiss();
                if (userVerified) {
                    userVerified = false;
                    finish();
                }
            }
        });
        msgDialog.show();
        msgDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }



}
