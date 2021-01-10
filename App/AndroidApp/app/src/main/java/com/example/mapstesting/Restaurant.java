package com.example.mapstesting;

/**
 * Restaurant parent class
 */
public class Restaurant {
    private String name;
    private String address;
    private String phoneNum;
    private int pop;

    public Restaurant(String name, String address, String phoneNum, int population){
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
        this.pop = population;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public String getPhoneNum(){
        return phoneNum;
    }

    public int getPop(){
        return pop;
    }
}
