package com.game.in2075.Retrofit.JsonClasses;

import com.game.in2075.Retrofit.Json2075API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Class with singletone to share some data between activities
public class SharedData {
    private static SharedData instance = new SharedData();

    private UserTO user;
    private Json2075API jsonAPI;

    public static SharedData getInstance(){
         return instance;
     }

     private SharedData(){

     }

     public void setUser(UserTO u){
        this.user = u;
     }

     public UserTO getUser(){
        return this.user;
     }

     public Json2075API useRetrofit(){
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl("http://10.0.2.2:8080/2075App/")
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

         jsonAPI =  retrofit.create(Json2075API.class);

         return jsonAPI;
     }
}
