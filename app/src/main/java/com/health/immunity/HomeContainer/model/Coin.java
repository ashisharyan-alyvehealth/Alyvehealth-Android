package com.health.immunity.HomeContainer.model;

public class Coin {
    public Coin(String gold, String silver){
        this.gold=gold;
        this.silver=silver;

    }
    public Coin(){}
    String gold="";
    String silver="";

    String getGold(){
        return this.gold;
    }
    String getSilver(){
        return this.silver;
    }
}
