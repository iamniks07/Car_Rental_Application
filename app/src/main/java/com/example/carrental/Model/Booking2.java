package com.example.carrental.Model;

public class Booking2 {
    private int bookingID;

    private String bookingStatus;
    private String customerID;
    private String customerName;

    private String pickupTime;
    private String returnTime;
    private int billingID;
    private int vehicleID;
    private String vehicleName;

    private String insuranceID;

    public Booking2() {
    }

    public Booking2(int bookingID, String bookingStatus, String customerID, String customerName, String pickupTime, String returnTime, int billingID, int vehicleID, String vehicleName, String insuranceID) {
        this.bookingID = bookingID;
        this.bookingStatus = bookingStatus;
        this.customerID = customerID;
        this.customerName = customerName;
        this.pickupTime = pickupTime;
        this.returnTime = returnTime;
        this.billingID = billingID;
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.insuranceID = insuranceID;
    }

    public int getBookingID() {
        return bookingID;
    }

    public void setBookingID(int bookingID) {
        this.bookingID = bookingID;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public int getBillingID() {
        return billingID;
    }

    public void setBillingID(int billingID) {
        this.billingID = billingID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }
}
