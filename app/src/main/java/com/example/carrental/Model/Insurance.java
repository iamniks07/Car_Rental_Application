package com.example.carrental.Model;

public class Insurance {
    int insuranceID;
    int cost;
    String type;
    public Insurance() {
    }

    public Insurance(int insuranceID, int cost, String type) {
        this.insuranceID = insuranceID;
        this.cost = cost;
        this.type = type;
    }

    public int getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
