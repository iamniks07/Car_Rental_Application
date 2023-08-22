package com.example.carrental;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.carrental.Session;

public class MainActivity extends AppCompatActivity {

    private TextView register;
    private TextView forgotPass;
    private Button login;

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Boolean isLoggedIn = Boolean.valueOf(Session.read(MainActivity.this,"isLoggedIn","false"));
        if(isLoggedIn){
            Intent homePage = new Intent(MainActivity.this,UserViewActivity.class);
            startActivity(homePage);
        }

        initComponents();
        clickListenHandler();

    }

    private void initComponents() {
        register = findViewById(R.id.register);

        login = findViewById(R.id.login);

        forgotPass = findViewById(R.id.forgot_password);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    private void clickListenHandler() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateemail() | !validatepass()){
                    return;
                }
                else{
                    isUser(view);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regPage = new Intent(MainActivity.this,RegistrationActivity.class);
                startActivity(regPage);
            }
        });
    }


    private void isUser(View view) {
        final String enterUsername = username.getText().toString().trim();
        final String enterPass = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");

        Query checkUser = reference.orderByChild("username").equalTo(enterUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    username.setError(null);

                    String passbyDB = snapshot.child(enterUsername).child("password").getValue().toString();
//                    Toast.makeText(MainActivity.this,passbyDB,Toast.LENGTH_LONG).show();
//                    Log.d("pass", passbyDB);

                    if(passbyDB.equals(enterPass)){

                        username.setError(null);

                        String userid = snapshot.child(enterUsername).child("username").getValue(String.class);
                        String name = snapshot.child(enterUsername).child("name").getValue(String.class);

                        Session.save(MainActivity.this,"name",name + "");
                        Session.save(MainActivity.this,"customerID",userid + "");
                        Session.save(MainActivity.this,"isLoggedIn","true");

                        Intent homepage = new Intent(MainActivity.this,UserViewActivity.class);
                        startActivity(homepage);

//                        String result = enterEmail+"\n"+enterPass ;
//                        Toast.makeText(MainActivity.this,name,Toast.LENGTH_LONG).show();

//                        intent.putExtra("name",namebyDB);

                    }
                    else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else {
                    username.setError("No such user exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private Boolean validateemail(){
        String val = username.getText().toString();
        if(val.isEmpty()){
            username.setError("field cannot be empty");
            return false;
        }
        else{
            username.setError(null);
            return true;
        }
    }

    private Boolean validatepass(){
        String val = password.getText().toString();
        if(val.isEmpty()){
            password.setError("field cannot be empty");
            return false;
        }
        else{
            password.setError(null);
            return true;
        }
    }
}