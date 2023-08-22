package com.example.carrental.Model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Booking implements Serializable {

   private int bookingID;

   private Calendar pickupDate;
   private Calendar returnDate;

   private String bookingStatus;
   private String customerID;
   private String customerName;

   private int billingID;
   private int vehicleID;
   private String vehicleName;

   private String insuranceID;

   public Booking() {
   }

   public Booking(int bookingID, Calendar pickupDate, Calendar returnDate, String bookingStatus, String customerID, String customerName, int billingID, int vehicleID, String vehicleName, String insuranceID) {
      this.bookingID = bookingID;
      this.pickupDate = pickupDate;
      this.returnDate = returnDate;
      this.bookingStatus = bookingStatus;
      this.customerID = customerID;
      this.customerName = customerName;
      this.billingID = billingID;
      this.vehicleID = vehicleID;
      this.vehicleName = vehicleName;
      this.insuranceID = insuranceID;
   }

   public String toString(){
      SimpleDateFormat format = new SimpleDateFormat("MMMM, d yyyy hh:mm a");
      return  "\n" +
              "BookingID:         " + bookingID + "\n" +
              "Pickup Date:       " + format.format(pickupDate.getTime()) + "\n" +
              "Return Date:       " + format.format(returnDate.getTime()) + "\n" +
              "Status:            " + bookingStatus + "\n" +
              "CustomerID:        " + customerID + "\n" +
              "BillingID:         " + billingID + "\n";
   }

   public int getBookingID() {
      return bookingID;
   }

   public void setBookingID(int bookingID) {
      this.bookingID = bookingID;
   }

   public Calendar getPickupDate() {
      return pickupDate;
   }

   public void setPickupDate(Calendar pickupDate) {
      this.pickupDate = pickupDate;
   }

   public Calendar getReturnDate() {
      return returnDate;
   }

   public void setReturnDate(Calendar returnDate) {
      this.returnDate = returnDate;
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

   public String getPickupTime(){
      SimpleDateFormat format = new SimpleDateFormat("hh:mm a MMMM, d yyyy");
      return format.format(pickupDate.getTime());
   }

   public String getReturnTime(){
      SimpleDateFormat format = new SimpleDateFormat("hh:mm a MMMM, d yyyy");
      return format.format(returnDate.getTime());
   }
}
