package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.carrental.Model.Booking;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class BookingCarActivity extends AppCompatActivity {

    private TextView pickupDate, returnDate;

    private TextView pickupTime, returnTime;

    private Calendar _pickup;

    private Calendar _return;

    private EditText fullName, email, phoneNumber;
    private RadioGroup customerTitle;

    public static String name;
    private String mrMs = "Mr";
    String CID;
    int VehicleID;
    String InsuranceID;
    String Title;
    String type;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM, d yyyy", Locale.CANADA);
    private SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.CANADA);

    private Button back, continueBooking;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    DatabaseReference reference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_car);

        initComponents();
        listenHandler();
    }

    private void initComponents() {

        back = findViewById(R.id.back);

        continueBooking = findViewById(R.id.continueBooking);

        pickupDate = findViewById(R.id.pickupDate);
        pickupTime = findViewById(R.id.pickupTime);

        returnDate = findViewById(R.id.returnDate);
        returnTime = findViewById(R.id.returnTime);

        customerTitle = findViewById(R.id.mrMsTitle);
        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phoneNumber);

        _pickup = Calendar.getInstance();
        _return = Calendar.getInstance();

        pickupDate.setText(dateFormat.format(_pickup.getTime()));
        pickupTime.setText(timeFormat.format(_pickup.getTime()));

        returnDate.setText(dateFormat.format(_return.getTime()));
        returnTime.setText(timeFormat.format(_return.getTime()));

        Intent intent = getIntent();
        VehicleID = intent.getIntExtra("VEHICLEID",0);
        InsuranceID = intent.getStringExtra("INSURANCEID");
        CID = intent.getStringExtra("CUSTOMERID");
        Title = intent.getStringExtra("Title");
        type = intent.getStringExtra("INSUR");
    }

    private void listenHandler() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        continueBooking.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent bookingSummaryPage = new Intent(BookingCarActivity.this, BookingSummaryActivity.class);
//                startActivity(bookingSummaryPage);
//            }
//        });

        pickupDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar(_pickup,pickupDate);
            }
        });
        pickupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePicker(_pickup, pickupTime);
            }
        });

        //RETURN DATE AND TIME LISTENER
        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar(_return,returnDate);
            }
        });
        returnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { openTimePicker(_return, returnTime);
            }
        });

        continueBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        customerTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton title = findViewById(checkedId);
                mrMs = title.getText().toString();
//                Toast.makeText(BookingCarActivity.this, mrMs, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void validate() {

        String _fullName = fullName.getText().toString();
        String _email= email.getText().toString();
        String _phoneNumber = phoneNumber.getText().toString();

        String fname = mrMs +". "+_fullName;

        int bookingID = generateID(400,499);

        if(!fieldCheck(_fullName,_email,_phoneNumber)) {
            Toast.makeText(this, "Incomplete Form", Toast.LENGTH_SHORT).show();
            return;
        }

        reference = FirebaseDatabase.getInstance().getReference("User");
        Query CheckUser = reference.orderByChild("username").equalTo(CID);
        CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    String e = snapshot.child(CID).child("email").getValue().toString();
                    name = snapshot.child(CID).child("name").getValue().toString();
                    String phone = snapshot.child(CID).child("phoneno").getValue().toString();

                    if(e.equals(_email) && name.equals(_fullName) && phone.equals(_phoneNumber))
                    {
                        rootNode = FirebaseDatabase.getInstance();
                        reference2 = rootNode.getReference("Booking");
                        Booking newBooking = new Booking(bookingID,_pickup,_return,"Waiting",CID,fname,-1,VehicleID,Title,InsuranceID);
//                        reference2.child(String.valueOf(bookingID)).setValue(newBooking);
                        Intent bookingSummary = new Intent(BookingCarActivity.this,BookingSummaryActivity.class);
                        bookingSummary.putExtra("BOOKING",bookingID);
                        bookingSummary.putExtra("NameTitle",mrMs);
                        bookingSummary.putExtra("Name",_fullName);
                        bookingSummary.putExtra("Email",_email);
                        bookingSummary.putExtra("Phone",_phoneNumber);
                        bookingSummary.putExtra("Title",Title);
                        bookingSummary.putExtra("VEHICLEID",VehicleID);
                        bookingSummary.putExtra("INSURANCE",InsuranceID);
                        bookingSummary.putExtra("BOOKING2",newBooking);
//                        bookingSummary.putExtra("pick",_pickup);
//                        bookingSummary.putExtra("ret",_return);
                        bookingSummary.putExtra("INSUR",type);
                        startActivity(bookingSummary);
//                        Toast.makeText(BookingCarActivity.this, e +"\n"+name, Toast.LENGTH_SHORT).show();
//                        Toast.makeText(BookingCarActivity.this, InsuranceID, Toast.LENGTH_SHORT).show();
                    }
                    else if(!name.equals(_fullName)){
                        Toast.makeText(BookingCarActivity.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
//                    fullName.setError("Enter Valid Details");
                        fullName.setError("Enter Valid Details");
                    }
                    else {
                        phoneNumber.setError("Enter Valid Details");
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private boolean fieldCheck(String _fullName, String _email, String _phoneNumber) {
        return  !_fullName.equals("") &&
                !_email.equals("") && !_phoneNumber.equals("") && !(_phoneNumber.length() < 10) ;
    }

    private void openCalendar(final Calendar rentalDate, final TextView rentalDateText) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);

        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                rentalDate.set(year,month,dayOfMonth);
                rentalDateText.setText(dateFormat.format(rentalDate.getTime()));
            }
        });

        datePickerDialog.show();
    }

    private Date openTimePicker(final Calendar rentalTime, final TextView rentalTimeText){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                rentalTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
                rentalTime.set(Calendar.MINUTE,minute);

                rentalTimeText.setText(timeFormat.format(rentalTime.getTime()));
            }
        },hour,min,false);

        timePickerDialog.show();

        return calendar.getTime();
    }

    private int generateID(int start, int end){
        Random rnd = new Random();
        int bound = end%100;
        int id = rnd.nextInt(bound)+start;
        return id;
    }
}