package com.game.in2075;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.game.in2075.Retrofit.JsonClasses.SharedData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteUserActivity extends AppCompatActivity {

    private Button delBttn, cancelBttn;
    private TextView userTxt, passTxt, noseTxt;
    private SharedData sharedData = SharedData.getInstance();
    private Boolean userVerified = false;
    private Dialog popDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        getSupportActionBar().hide();

        popDialog = new Dialog(this);
        popDialog.setCancelable(false);
        popDialog.setCanceledOnTouchOutside(false);

        delBttn = findViewById(R.id.deleteButton);
        cancelBttn = findViewById(R.id.cancelButton);
        userTxt = findViewById(R.id.oldPassText);
        passTxt = findViewById(R.id.passText);
        noseTxt = findViewById(R.id.textView3);

        deleteUser();
        cancelDeleteUser();
    }

    public void deleteUser(){
        delBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = userTxt.getText().toString();
                String b = passTxt.getText().toString();
                if (!a.equals("") && b.equals(sharedData.getUser().getPassword()))
                    delUser();
                else
                    showWarning("Username or password is incorrect." + "\n\n" + "Check your credentials.");
            }
        });
    }

    public void cancelDeleteUser(){
        cancelBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void delUser(){
        final String username = userTxt.getText().toString();
        Call<Void> call = sharedData.useRetrofit().setUserDel(username);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(!response.isSuccessful()) {
                    switch (response.code()) {
                        case 400:
                            showWarning("Please, check your data." + "\n\n" + "Seems like user or password is wrong!");
                            break;
                        default:
                            showWarning("Oops!" + "\n\n" + "Seem something goes wrong :S");
                            break;
                    }
                    return;
                }
                    else {
                    userVerified = true;
                    showWarning("Thanks for enjoy our game " + username + "\n\n" + "We hope see you again!");
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("JSON", t.toString());
            }
        });
    }

    //Callable Layout for warnings. Now is used for delete
    public void showWarning(String u){
        TextView closeWarning, warningMsg;
        ImageView warningImg;
        popDialog.setContentView(R.layout.popup_messages);
        closeWarning = popDialog.findViewById(R.id.closeTxt);
        warningMsg = popDialog.findViewById(R.id.warningtTxt1);
        warningImg = popDialog.findViewById(R.id.warningImage);

        if (userVerified) {
            warningMsg.setText(u);
            warningImg.setImageResource(R.drawable.bye_bye);
        }

        else
            warningMsg.setText(u);

        closeWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popDialog.dismiss();
                if (userVerified) {
                    finish();
                    /** Close all activities */
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    userVerified = false;
                }
            }
        });
        popDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        popDialog.show();
    }
}
