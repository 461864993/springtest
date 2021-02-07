package com.example.demo.entity;

public class City {
    private int id;
    private String city;
    private String number;

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "{id:" + this.id + ",city:" + this.city + ",number:" + this.number +  "}";
    }
}
