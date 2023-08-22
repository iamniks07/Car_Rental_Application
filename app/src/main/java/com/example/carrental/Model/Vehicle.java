package com.example.carrental.Model;

public class Vehicle {

   private int id;

   private int price;
   private int seats;
   private int mileage;
   private String manufacturer;
   private String model;
   private int year;
   private String category;
   private int availability;
   private String purl;

   public Vehicle() {
   }

   public Vehicle(int id, int price, int seats, int mileage, String manufacturer, String model, int year, String category, int availability, String purl) {
      this.id = id;
      this.price = price;
      this.seats = seats;
      this.mileage = mileage;
      this.manufacturer = manufacturer;
      this.model = model;
      this.year = year;
      this.category = category;
      this.availability = availability;
      this.purl = purl;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getPrice() {
      return price;
   }

   public void setPrice(int price) {
      this.price = price;
   }

   public int getSeats() {
      return seats;
   }

   public void setSeats(int seats) {
      this.seats = seats;
   }

   public int getMileage() {
      return mileage;
   }

   public void setMileage(int mileage) {
      this.mileage = mileage;
   }

   public String getManufacturer() {
      return manufacturer;
   }

   public void setManufacturer(String manufacturer) {
      this.manufacturer = manufacturer;
   }

   public String getModel() {
      return model;
   }

   public void setModel(String model) {
      this.model = model;
   }

   public int getYear() {
      return year;
   }

   public void setYear(int year) {
      this.year = year;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public int getAvailability() {
      return availability;
   }

   public void setAvailability(int availability) {
      this.availability = availability;
   }

   public String getPurl() {
      return purl;
   }

   public void setPurl(String purl) {
      this.purl = purl;
   }

   public String fullTitle(){
      return getYear() + " " + getManufacturer() + " " + getModel();
   }
}
