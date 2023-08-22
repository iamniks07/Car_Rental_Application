package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class BookingCompleteActivity extends AppCompatActivity {

    private Button back;
    String[] arrOfStr;
    int dateDifference;
    String Vcost, Icost;
    static int vcost, icost;
    static int days;
    //DRIVER DETAILS
    private int bid;
    private TextView name, email, phoneNumber;

    //BOOKING SUMMARY
    private TextView bookingID, vehicleName, rate, totalDays, _pickup, _return, insurance, insuranceRate, totalCost;

    private String _name, _email, _phone, _title, title, _pick, _ret;

    int VehicleID;
    String InsuranceID;
    String type;
    static int Sum=0;
    static int Sum2=0;
    SimpleDateFormat Format = new SimpleDateFormat("dd/MM/yyyy");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_complete);

        initComponents();
        listenHandler();
        displayCustomerInformation();
        displaySummary();
    }

    private void initComponents() {
        back = findViewById(R.id.back);

        bookingID = findViewById(R.id.bookingID);
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

        bid = getIntent().getIntExtra("Booking",0);
//        Toast.makeText(this, String.valueOf(bid), Toast.LENGTH_SHORT).show();
        bookingID.setText(String.valueOf(bid));

        title = getIntent().getStringExtra("NameTitle");
        _name = getIntent().getStringExtra("Name");
        _email = getIntent().getStringExtra("Email");
        _phone = getIntent().getStringExtra("Phone");
        _title = getIntent().getStringExtra("TITLE");
        _pick = getIntent().getStringExtra("pick");
        _ret = getIntent().getStringExtra("ret");

        VehicleID = getIntent().getIntExtra("VEHICLEID",0);
        InsuranceID = getIntent().getStringExtra("INSURANCE");
        type = getIntent().getStringExtra("INSUR");
    }

    private void listenHandler() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UserViewActivity.class);
                startActivity(intent);
//                finish();
            }
        });
    }

    private void displayCustomerInformation() {
        name.setText(title+". "+_name);
        email.setText(_email);
        phoneNumber.setText(_phone);
    }

    private void displaySummary() {
//        bookingID.setText(bid);
        vehicleName.setText(_title);

        _pickup.setText(_pick);
        String date1 = getDate(_pick);

        _return.setText(_ret);
        String date2 = getDate(_ret);

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

//                Toast.makeText(BookingCompleteActivity.this, String.valueOf(Sum), Toast.LENGTH_SHORT).show();
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