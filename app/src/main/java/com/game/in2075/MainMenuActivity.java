package com.game.in2075;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.game.in2075.Retrofit.JsonClasses.UserTO;

public class MainMenuActivity extends AppCompatActivity {

    private Button settingsBttn;
    private TextView userDataTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        settingsBttn = findViewById(R.id.settingsButton);
        userDataTxt = findViewById(R.id.userDataText);

        UserTO dataUser = (UserTO) getIntent().getSerializableExtra("userLog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent1 = new Intent (this, SettingsActivity.class); /** Go to Register Activity */
                startActivityForResult(intent1, 0);
                return true;
            case R.id.action_logout:
                Toast.makeText(this, "See you soon soldier!", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(),LoginActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent2);
        }
        return super.onOptionsItemSelected(item);
    }

}
