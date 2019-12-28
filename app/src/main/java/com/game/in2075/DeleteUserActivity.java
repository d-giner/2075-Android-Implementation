package com.game.in2075;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.game.in2075.Retrofit.Json2075API;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteUserActivity extends AppCompatActivity {

    private Button delBttn, cancelBttn;
    private TextView userTxt, passTxt, noseTxt;
    private Json2075API jsonAPI;
    private Dialog msgDialog;
    private Boolean userVerified = false;
    Dialog popDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        popDialog = new Dialog(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/2075App/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonAPI =  retrofit.create(Json2075API.class);

        delBttn = findViewById(R.id.deleteButton);
        cancelBttn = findViewById(R.id.cancelButton);
        userTxt = findViewById(R.id.userText);
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
                if (!a.equals(""))
                delUser();
            }
        });
    }

    public void cancelDeleteUser(){
        cancelBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userTxt.setText("");
                passTxt.setText("");
                finish();
            }
        });
    }

    public void delUser(){
        final String username = userTxt.getText().toString();
        Call<Void> call = jsonAPI.setUserDel(username);

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
        popDialog.setContentView(R.layout.popup_messages);
        closeWarning = (TextView) popDialog.findViewById(R.id.closeTxt);
        warningMsg = (TextView) popDialog.findViewById(R.id.warningtTxt);

        if (userVerified)
            warningMsg.setText(u);
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
        popDialog.show();
    }
}
