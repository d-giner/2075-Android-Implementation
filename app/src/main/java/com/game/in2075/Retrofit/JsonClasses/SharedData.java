package com.game.in2075.Retrofit.JsonClasses;

import com.game.in2075.Retrofit.Json2075API;

import java.util.LinkedList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Class with singletone to share some data between activities
public class SharedData {
    private static SharedData instance = null;

    private UserTO user;
    private Json2075API jsonAPI;
    private LinkedList<Obj> userInventory = null;
    private LinkedList<Obj> shopItems = null;
    private Boolean  lightsaber, helmet, greatShield, o2, wingman;

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

     public void initializeItemBools(){
         this.lightsaber = false;
         this.greatShield = false;
         this.helmet = false;
         this.o2 = false;
         this.wingman = false;
     }

     public void setUserInventory(LinkedList<Obj> li){
         this.userInventory = new LinkedList<>();
        this.userInventory.addAll(li);

        this.lightsaber = false;
        this.greatShield = false;
        this.helmet = false;
         this.o2 = false;
         this.wingman = false;

        for (Obj object : li){
            switch (object.getObjName()) {
                case "Lightsaber":
                    this.lightsaber = true;
                    break;
                case "Great Shield":
                    this.greatShield = true;
                    break;
                case "Helmet":
                    this.helmet = true;
                    break;
                case "Oxygen Bottle":
                    this.o2 = true;
                    break;
                case "Wingman":
                    this.wingman = true;
                    break;
            }
         }
     }

     public LinkedList<Obj> getUserInventory(){
        return this.userInventory;
     }

    public LinkedList<Obj> getShopItems() {
        return shopItems;
    }

    public void setShopItems(LinkedList<Obj> sI) {
        this.shopItems = new LinkedList<>();
        this.shopItems.addAll(sI);
    }

    public Boolean getLightsaber() {
        return lightsaber;
    }

    public void setLightsaber(Boolean lightsaber) {
        this.lightsaber = lightsaber;
    }

    public Boolean getGreatShield() {
        return greatShield;
    }

    public void setGreatShield(Boolean greatShield) {
        this.greatShield = greatShield;
    }

    public Boolean getHelmet() {
        return helmet;
    }

    public void setHelmet(Boolean helmet) {
        this.helmet = helmet;
    }

    public Boolean getO2() {
        return o2;
    }

    public void setO2(Boolean o) {
        this.o2 = o;
    }

    public Boolean getWingman() {
        return wingman;
    }

    public void setWingman(Boolean w) {
        this.wingman = w;
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
