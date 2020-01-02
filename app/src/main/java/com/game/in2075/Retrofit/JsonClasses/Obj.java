package com.game.in2075.Retrofit.JsonClasses;

public class Obj {
    private int id;//The id relative of the object
    private String idUser;//The id of the user
    private String objName;
    private int objAttack;
    private int objDefense;
    private int price;
    private int healthPoints;

    public Obj (String idUser, String objName, int objAttack, int  objDefense, int price, int healthPoints){
        this.idUser = idUser;
        this.objName = objName;
        this.objAttack = objAttack;
        this.objDefense = objDefense;
        this.price = price;
        this.healthPoints = healthPoints;
    }

    public Obj(){
    }

    public String getIdUser() {return idUser;}
    public void setIdUser(String id){this.idUser = id;}
    public String getObjName() {
        return objName;
    }
    public void setObjName(String objName) {
        this.objName = objName;
    }

    public int getObjAttack() {
        return objAttack;
    }
    public void setObjAttack(int objAttack) {
        this.objAttack = objAttack;
    }

    public int getObjDefense() {
        return objDefense;
    }
    public void setObjDefense(int objDefense) {
        this.objDefense = objDefense;
    }

    public int getPrice() { return price; }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }
}
