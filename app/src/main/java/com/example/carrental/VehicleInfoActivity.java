package com.example.carrental;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrental.Model.Vehicle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class VehicleInfoActivity extends AppCompatActivity {

    int VehicleID;
    String purl;
    String ID;
    String CID;
    String title, image, Manufacturer, Model, category;
    int Seats, Mileage, Available, Year, price ;
    public Vehicle vehicle;
    //VEHICLE TITLE
    public TextView vehicleTitle;


    //VEHICLE IMAGE OBJECT
    private ImageView vehicleImage;
    //VEHICLE PRICE
    private TextView vehiclePrice;

    //VEHICLE AVAILABILITY FIELD
    private ConstraintLayout available;
    private ConstraintLayout notAvailable;

    //GOING BACK BUTTON
    private Button back;
    private Button book;

    //VEHICLE INFO FIELD
    private TextView year, manufacturer, model, mileage, seats, type;

    //INSURANCE OPTION
    private RadioGroup insuranceOption;
    private String chosenInsurance = "";
    DatabaseReference reference;
    DatabaseReference reference2;




//    public VehicleInfoActivity(int VehicleID, String title)
//    {
//        this.VehicleID = VehicleID;
//        this.title = title;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);

//        vehicleTitle = findViewById(R.id.vehicleTitle);

//        vehicle = (Vehicle)getIntent().getSerializableExtra("title");
//        vehicleTitle.setText(t);
//        Toast.makeText(VehicleInfoActivity.this,t,Toast.LENGTH_LONG).show();

        initComponents();
        listenHandler();
        displayVehicleInfo();
    }

    private void initComponents() {
        back = findViewById(R.id.back);
        vehicleTitle = findViewById(R.id.vehicleTitle);
        vehicleImage = findViewById(R.id.vehicleImage);

        available = findViewById(R.id.available);
        notAvailable = findViewById(R.id.notAvailable);

        year = findViewById(R.id.year);
        manufacturer = findViewById(R.id.manufacturer);
        model = findViewById(R.id.model);
        mileage = findViewById(R.id.mileage);
        seats = findViewById(R.id.seats);
        type = findViewById(R.id.type);
        vehiclePrice = findViewById(R.id.vehiclePrice);

        //INSURANCE OPTION
        insuranceOption = findViewById(R.id.insuranceOption);
        book = findViewById(R.id.book_this_car);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        image = intent.getStringExtra("url");
        Manufacturer = intent.getStringExtra("manufacturer");
        Model = intent.getStringExtra("model");
        category = intent.getStringExtra("category");
        Available = intent.getIntExtra("available",0);
        Year = intent.getIntExtra("year",0);
        price = intent.getIntExtra("price",0);
        Seats = intent.getIntExtra("seats",0);
        Mileage = intent.getIntExtra("mileage",0);
        VehicleID = intent.getIntExtra("id",0);
        CID = intent.getStringExtra("customerID");

        reference2 = FirebaseDatabase.getInstance().getReference("Vehicle");
        Query check = reference2.orderByChild("model").equalTo(Model);
        check.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
//                    purl = snapshot.child(Model).child("purl").getValue().toString();
//                    Toast.makeText(VehicleInfoActivity.this, "Vehicle Found" + VehicleID, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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

                getInsuranceID(chosenInsurance);
//                Intent informationPage = new Intent(VehicleInfoActivity.this, BookingCarActivity.class);
//                informationPage.putExtra("INSURANCEID",getInsuranceID(chosenInsurance));
//                informationPage.putExtra("VEHICLEID",VehicleID + "");
//                informationPage.putExtra("CUSTOMERID",CID);
//                startActivity(informationPage);
//                Toast.makeText(VehicleInfoActivity.this, getInsuranceID(chosenInsurance), Toast.LENGTH_SHORT).show();
            }
        });

        insuranceOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton option = findViewById(checkedId);
                chosenInsurance = option.getText().toString().toLowerCase();
//                Toast.makeText(VehicleInfoActivity.this, chosenInsurance, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getInsuranceID(String chosenInsurance){
        reference = FirebaseDatabase.getInstance().getReference("Insurance");
        Query insurance = reference.orderByChild("type").equalTo(chosenInsurance);
        insurance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    ID = snapshot.child(chosenInsurance).child("id").getValue().toString();
//                int cost = (int) snapshot.child(chosenInsurance).child("cost").getValue();
//                    Toast.makeText(VehicleInfoActivity.this,String.valueOf(VehicleID),Toast.LENGTH_LONG).show();
                    Intent informationPage = new Intent(VehicleInfoActivity.this, BookingCarActivity.class);
                    informationPage.putExtra("INSURANCEID",ID);
                    informationPage.putExtra("VEHICLEID",VehicleID);
                    informationPage.putExtra("CUSTOMERID",CID);
                    informationPage.putExtra("Title",title);
                    informationPage.putExtra("INSUR",chosenInsurance);
                    startActivity(informationPage);
                }
                else {
                    Toast.makeText(VehicleInfoActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return chosenInsurance;
    }

//    private int getVehicleID(String category)
//    {
//        reference = FirebaseDatabase.getInstance().getReference("Vehicle");
//        Query vehicle = reference.child("category").child(category);
//    }

    private void displayVehicleInfo() {
//        FirebaseRecyclerOptions<Vehicle> options =
//                new FirebaseRecyclerOptions.Builder<Vehicle>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference(), Vehicle.class)
//                        .build();
        vehicleTitle.setText(title);
        Picasso.get().load(image).into(vehicleImage);

        year.setText(Year + "");
        manufacturer.setText(Manufacturer);
        model.setText(Model);
        mileage.setText(Mileage+"");
        seats.setText(Seats + "");
        type.setText(category);

        vehiclePrice.setText("Rs."+ price +"/Day");

        if(Available == 0)
        {
            available.setVisibility(ConstraintLayout.INVISIBLE);
            notAvailable.setVisibility(ConstraintLayout.VISIBLE);
            book.setEnabled(false);
            book.setBackground(ContextCompat.getDrawable(VehicleInfoActivity.this,R.drawable.disable_button));
            book.setText("Vehicle Not Available");
        } else {
            available.setVisibility(ConstraintLayout.VISIBLE);
            notAvailable.setVisibility(ConstraintLayout.INVISIBLE);
            book.setEnabled(true);
            book.setBackground(ContextCompat.getDrawable(VehicleInfoActivity.this,R.drawable.round_button));
            book.setText("Book This Car");
        }


    }
}