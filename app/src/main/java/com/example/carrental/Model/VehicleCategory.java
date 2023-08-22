package com.example.carrental.Model;

public class VehicleCategory {
    int id;
    String category;
    int colorCard;
    int quantity;
    String url;

    public VehicleCategory() {
    }

    public VehicleCategory(int id,String category, int colorCard, int quantity, String url) {
        this.id = id;
        this.category = category;
        this.colorCard = colorCard;
        this.quantity = quantity;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getColorCard() {
        return colorCard;
    }

    public void setColorCard(int colorCard) {
        this.colorCard = colorCard;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
