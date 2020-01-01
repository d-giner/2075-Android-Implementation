package com.game.in2075.Retrofit.JsonClasses;

import com.game.in2075.Retrofit.Json2075API;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Class with singletone to share some data between activities
public class SharedData {
    private static SharedData instance = null;

    private UserTO user;
    private Json2075API jsonAPI;
    private LinkedList<Obj> userInventory = new LinkedList<Obj>();

    public static SharedData getInstance(){
        if (instance == null){
            instance = new SharedData();
        }
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

     public void setUserInventory(LinkedList<Obj> l){
        this.userInventory.addAll(l);
     }

     public LinkedList<Obj> getUserInventory(){
        return this.userInventory;
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
