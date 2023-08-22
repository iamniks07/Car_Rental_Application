package com.example.carrental;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    EditText regname, regemail, reguser, regphone, regstreet, regcity, regpin, regPass, confpass;
    Button register;
    TextView login, expiryDate, dob;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        regname = (EditText) findViewById(R.id.fullName);
        regemail = (EditText) findViewById(R.id.email);
        reguser = (EditText) findViewById(R.id.username);

        regphone = (EditText) findViewById(R.id.phoneNumber);
        regstreet = (EditText) findViewById(R.id.street);
        regcity = (EditText) findViewById(R.id.city);
        regpin = (EditText) findViewById(R.id.pinCode);
        regPass = (EditText) findViewById(R.id.password);
        confpass = (EditText) findViewById(R.id.confirmPassword);

        String confirm_password = ((EditText)findViewById(R.id.confirmPassword)).getText().toString();

        initComponents();
        clickListenHandler();
    }

    private void initComponents() {

        register = findViewById(R.id.register);

        login = findViewById(R.id.login);

        expiryDate = findViewById(R.id.expiryDate);

        dob = findViewById(R.id.dob);
    }

    private void clickListenHandler() {

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage = new Intent(RegistrationActivity.this,MainActivity.class);
                startActivity(loginPage);
            }
        });

        expiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar(expiryDate);
            }
        });

        //Date of Birth Listener
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar(dob);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("User");

                String password = regPass.getText().toString();
                String Cpass = confpass.getText().toString();
                if(!validateName() | !validateEmail() | !validateUsername() | !validateExpiry() | !validateDob() | !validatePhone() | !validateStreet() | !validateCity() |
                        !validatePin() | !validatePass()){
                    return;
                }
                else if(password.equals(Cpass)) {
                    String name = regname.getText().toString();
                    String email = regemail.getText().toString();
                    String username = reguser.getText().toString();
                    String expiry = expiryDate.getText().toString();
                    String DOB = dob.getText().toString();
                    String phoneNumber = regphone.getText().toString();
                    String street = regstreet.getText().toString();
                    String city = regcity.getText().toString();
                    String pincode = regpin.getText().toString();

                    UserHelperClass helperClass = new UserHelperClass(name,email,username,expiry,DOB,phoneNumber,street,city,pincode,password);

                    reference.child(username).setValue(helperClass);
                    Toast.makeText(RegistrationActivity.this, "Registered successfully", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    regPass.setError("Password not matches");
                    confpass.setError("Password not matches");
                }
            }
        });
    }

    private Boolean validateName(){
        String val = regname.getText().toString();
        if(val.isEmpty()){
            regname.setError("field cannot be empty");
            return false;
        }
        else{
            regname.setError(null);
            return true;
        }
    }

    private Boolean validateEmail(){
        String val = regemail.getText().toString();
        String epattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            regemail.setError("field cannot be empty");
            return false;
        }
        else if(!val.matches(epattern)){
            regemail.setError("Invalid email address");
            return false;
        }
        else{
            regemail.setError(null);
            return true;
        }
    }

    private Boolean validateUsername(){
        String val = reguser.getText().toString();
        if(val.isEmpty()){
            reguser.setError("field cannot be empty");
            return false;
        }
        else{
            reguser.setError(null);
            return true;
        }
    }

    private Boolean validateExpiry(){
        String val = expiryDate.getText().toString();
        if(val.isEmpty()){
            expiryDate.setError("field cannot be empty");
            return false;
        }
        else{
            expiryDate.setError(null);
            return true;
        }
    }

    private Boolean validateDob(){
        String val = dob.getText().toString();
        if(val.isEmpty()){
            dob.setError("field cannot be empty");
            return false;
        }
        else{
            dob.setError(null);
            return true;
        }
    }

    private Boolean validatePhone(){
        String val = regphone.getText().toString();
        if(val.isEmpty()){
            regphone.setError("field cannot be empty");
            return false;
        }
        else if(!((val.length()) > 9 && (val.length()) < 11)){
            regphone.setError("Invalid password");
            return false;
        }
        else{
            regphone.setError(null);
            return true;
        }
    }

    private Boolean validateStreet(){
        String val = regstreet.getText().toString();
        if(val.isEmpty()){
            regstreet.setError("field cannot be empty");
            return false;
        }
        else{
            regstreet.setError(null);
            return true;
        }
    }

    private Boolean validateCity(){
        String val = regcity.getText().toString();
        if(val.isEmpty()){
            regcity.setError("field cannot be empty");
            return false;
        }
        else{
            regcity.setError(null);
            return true;
        }
    }

    private Boolean validatePin(){
        String val = regpin.getText().toString();
        if(val.isEmpty()){
            regpin.setError("field cannot be empty");
            return false;
        }
        else if(!((val.length()) > 5)){
            regpin.setError("Pincode too short");
            return false;
        }
        else{
            regpin.setError(null);
            return true;
        }
    }

    private Boolean validatePass(){
        String val = regPass.getText().toString();
        String Passval = confpass.getText().toString();
        if(val.isEmpty()){
            regname.setError("field cannot be empty");
            return false;
        }
        else if(Passval.isEmpty()){
            confpass.setError("field cannot be empty");
            return false;
        }
        else{
            regname.setError(null);
            return true;
        }
    }

    //Opening a Calendar Dialog
//    @RequiresApi(api = Build.VERSION_CODES.N)
    private void openCalendar(final TextView dateFieldButton) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);

        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = year + "-" + (month+1) + "-" + dayOfMonth;
                dateFieldButton.setText(date);
            }
        });

        datePickerDialog.show();
    }
}