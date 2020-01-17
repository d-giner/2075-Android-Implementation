package com.game.in2075;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide(); //Hide the action bar.

        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent (getApplicationContext(), LoginActivity.class); // Go to Login Activity
                startActivityForResult(intent, 0);
                finish();
            }
        }.start();
    }
}
