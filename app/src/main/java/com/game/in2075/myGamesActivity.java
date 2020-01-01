package com.game.in2075;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.game.in2075.Retrofit.JsonClasses.DataAdapterGames;
import com.game.in2075.Retrofit.JsonClasses.Game;
import com.game.in2075.Retrofit.JsonClasses.SharedData;

import java.util.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class myGamesActivity extends AppCompatActivity {

    private SharedData sharedData = SharedData.getInstance();
    private TextView errorInfoTxt;
    private RecyclerView recyclerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_games);

        getSupportActionBar().hide();

        errorInfoTxt = findViewById(R.id.errorInfoText);
        recyclerList = findViewById(R.id.myRecycler);

        recyclerList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        getUserGames();
    }

    public void getUserGames(){
        Call<List<Game>> call = sharedData.useRetrofit().getUserGames(sharedData.getUser().getID());
        call.enqueue(new Callback<List<Game>>() {

            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {

                if(!response.isSuccessful()){
                    switch (response.code()) {
                        case 404:
                            errorInfoTxt.setText("Player ID not found.");
                            break;
                        default:
                            errorInfoTxt.setText("Oops!" + "\n\n" + "Seem something goes wrong :S");
                            break;
                    }
                    return;
                } else {
                    List<Game> myGames = response.body();
                    //errorInfoTxt.setText(myGames.get(0).getData());
                    recyclerList.setAdapter(new DataAdapterGames(myGames));
                }
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Log.d("JSON", t.toString());
            }
        });
    }
}
