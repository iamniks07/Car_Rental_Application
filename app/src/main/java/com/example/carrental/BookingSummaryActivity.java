package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.Model.Booking;
import com.example.carrental.Model.Insurance;
import com.example.carrental.Model.Vehicle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BookingSummaryActivity extends AppCompatActivity {

    private Button back, book, payNow;
    String[] arrOfStr;
    int VehicleID;
    String Vcost, Icost;
    static int vcost, icost;
    static int days;
    String type;
    public String fname;
    static int Sum=0;
    static int Sum2=0;
    int dateDifference;
    //DRIVER DETAILS
    private TextView name, email, phoneNumber;

    //BOOKING SUMMARY
    private TextView vehicleName, rate, totalDays, _pickup, _return, insurance, insuranceRate, totalCost;

    //VEHICLE IMAGE
    private ImageView vehicleImage;

    private int bid;
    private String _name, _email, _phone, title;
    private String pickup, ret;
    public String _title;
    private Booking booking;
    //INSURANCE
    String InsuranceID;
    FirebaseDatabase rootNode;
    //VEHICLE
//    private ProgressBar paidLoading;
    SimpleDateFormat Format = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_summary);

        initComponents();
        listenHandler();
        displayCustomerInformation();
        displaySummary();
    }

    private void initComponents() {
        back = findViewById(R.id.back);
        book = findViewById(R.id.book);
        payNow = findViewById(R.id.payNow);

        //DRIVER DETAILS
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);

        //BOOKING SUMMARY
        vehicleName = findViewById(R.id.vehicleName);
        rate = findViewById(R.id.rate);
        totalDays = findViewById(R.id.totalDays);
        _pickup = findViewById(R.id.pickup);
        _return = findViewById(R.id.dropoff);

        //INSURANCE TYPE
        insurance = findViewById(R.id.insurance);
        insuranceRate = findViewById(R.id.insuranceRate);

        //TOTAL COST
        totalCost = findViewById(R.id.totalCost);

        //VEHICLE IMAGE
        vehicleImage = findViewById(R.id.vehicleImage);

        bid = getIntent().getIntExtra("BOOKING",0);
        Toast.makeText(this, String.valueOf(bid), Toast.LENGTH_SHORT).show();

        booking = (Booking) getIntent().getSerializableExtra("BOOKING2");

        title = getIntent().getStringExtra("NameTitle");
        _name = getIntent().getStringExtra("Name");
        _email = getIntent().getStringExtra("Email");
        _phone = getIntent().getStringExtra("Phone");
        _title = getIntent().getStringExtra("Title");

        VehicleID = getIntent().getIntExtra("VEHICLEID",0);
        InsuranceID = getIntent().getStringExtra("INSURANCE");
        type = getIntent().getStringExtra("INSUR");
    }

    private void listenHandler() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                DatabaseReference reference1 = rootNode.getReference("Booking");
                reference1.child(String.valueOf(bid)).setValue(booking);
                Intent intent = new Intent(getApplicationContext(),BookingCompleteActivity.class);
                intent.putExtra("Booking",bid);
                intent.putExtra("NameTitle",title);
                intent.putExtra("Name",_name);
                intent.putExtra("Email",_email);
                intent.putExtra("Phone",_phone);
                intent.putExtra("Title",_title);
                intent.putExtra("pick",pickup);
                intent.putExtra("ret",ret);
                intent.putExtra("VEHICLEID",VehicleID);
                intent.putExtra("INSURANCE",InsuranceID);
                intent.putExtra("INSUR",type);
                startActivity(intent);
            }
        });
    }

    private void displayCustomerInformation() {
        fname = title+". "+_name;
        name.setText(fname);
        email.setText(_email);
        phoneNumber.setText(_phone);

    }

    private void displaySummary() {
        vehicleName.setText(_title);

        pickup = booking.getPickupTime();
        _pickup.setText(pickup);
        String date1 = getDate(pickup);

        ret = booking.getReturnTime();
        _return.setText(ret);
        String date2 = getDate(ret);

        dateDifference = (int) getDayDifference(Format,date1,date2);
        totalDays.setText(dateDifference+" Days");
        Sum = dateDifference;

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Insurance");
        Query check = reference.orderByChild("type").equalTo(type);
        check.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(type).child("type").getValue().toString();
                Icost = snapshot.child(type).child("cost").getValue().toString();
                icost = Integer.parseInt(Icost);
                insurance.setText(name);
                insuranceRate.setText("Rs."+Icost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Vehicle");
        Query check2 = reference2.orderByChild("id").equalTo(VehicleID);
        check2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Vcost = snapshot.child(String.valueOf(VehicleID)).child("price").getValue().toString();
                rate.setText("Rs."+Vcost+"/Day");
                vcost = Integer.parseInt(Vcost);
                Sum = Sum * vcost + icost;
                Sum2 = Sum;
                totalCost.setText("Rs."+Sum);
//                Toast.makeText(BookingSummaryActivity.this, String.valueOf(Sum), Toast.LENGTH_SHORT).show();
                String vimage = snapshot.child(String.valueOf(VehicleID)).child("purl").getValue().toString();
                Picasso.get().load(vimage).into(vehicleImage);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    public static long getDayDifference(SimpleDateFormat format, String date1, String date2) {
        try {
            return TimeUnit.DAYS.convert(format.parse(date2).getTime() - format.parse(date1).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String getDate(String pickup) {
        int a = 0;
        arrOfStr = pickup.split(" ", 5);
        for (String j : arrOfStr)
            a++;
        String year = arrOfStr[a-1];
        String m = arrOfStr[a-3];
        String[] arr = m.split(",",2);
        String month = getMonth(arr[0]);
//        String month = arr[0];
        String day = arrOfStr[a-2];
        String date = day + "/" + month + "/" + year ;
        return date;
    }

    private String getMonth(String s) {
        if(s.equals("January")){
            return String.valueOf(1);
        }
        else if(s.equals("February")){
            return String.valueOf(2);
        }
        else if(s.equals("March")){
            return String.valueOf(3);
        }
        else if(s.equals("April")){
            return String.valueOf(4);
        }
        else if(s.equals("May")){
            return String.valueOf(5);
        }
        else if(s.equals("June")){
            return String.valueOf(6);
        }
        else if(s.equals("July")){
            return String.valueOf(7);
        }
        else if(s.equals("August")){
            return String.valueOf(8);
        }
        else if(s.equals("September")){
            return String.valueOf(9);
        }
        else if(s.equals("October")){
            return String.valueOf(10);
        }
        else if(s.equals("November")){
            return String.valueOf(11);
        }
        return String.valueOf(12);
    }

}