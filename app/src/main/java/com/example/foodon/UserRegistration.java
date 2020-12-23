package com.example.foodon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.foodon.R;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class UserRegistration extends AppCompatActivity {
    TextInputLayout Fname, Lname, Emailid, Password, confirmpass, Mobilenumber;
    TextView Location, Area;
    Button getlocation, already_registered, register;
    String fname, lname, emailid, password, confpassword, mobile, longitude, latitude, area, location;
    DBHelper DB;

    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        Fname = (TextInputLayout) findViewById(R.id.Fname);
        Lname = (TextInputLayout) findViewById(R.id.Lname);
        Emailid = (TextInputLayout) findViewById(R.id.Emailid);
        Password = (TextInputLayout) findViewById(R.id.Password);
        confirmpass = (TextInputLayout) findViewById(R.id.confirmpass);
        Mobilenumber = (TextInputLayout) findViewById(R.id.Mobilenumber);
        Location =(TextView) findViewById(R.id.Location);
        Area = (TextView) findViewById(R.id.Area);
        getlocation = (Button) findViewById(R.id.getlocation);
        already_registered = (Button) findViewById(R.id.already_registered);
        register = (Button) findViewById(R.id.register);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

//        Location.setVisibility(View.INVISIBLE);

        DB = new DBHelper(this);

        getlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(UserRegistration.this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
                    getlocation();
                } else {
                    ActivityCompat.requestPermissions(UserRegistration.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }

            private void getlocation() {
                if (ActivityCompat.checkSelfPermission(UserRegistration.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UserRegistration.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<android.location.Location>() {
                    @Override
                    public void onComplete(@NonNull Task<android.location.Location> task) {
                        Location location = task.getResult();


                            try {
                                if (location != null) {
//                                    Geocoder  is a class for handling geocoding and reverse geocoding.
//                                    Geocoding is the process of transforming a street address or other description of a location into a (latitude, longitude) coordinate.
//                                    Reverse geocoding is the process of transforming a (latitude, longitude) coordinate into a (partial) address.
                                            Geocoder geocoder = new Geocoder(UserRegistration.this, Locale.getDefault());
//                                    A Locale object represents a specific geographical, political, or cultural region
                                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
//                                    Returns an array of Addresses that are known to describe the area immediately surrounding the given latitude and longitude. The returned addresses will be localized for the locale provided to this class's constructor.
                                Location.setText(Html.fromHtml("<font color='#000000'><b>Location:</b><br> "+addresses.get(0).getCountryName()+", "+addresses.get(0).getLocality()));
                                Area.setText(Html.fromHtml("<font color='#000000'><b> Address:</b> <br>"+addresses.get(0).getAddressLine(0)));
//                                Area.setText();
                            }


                        } catch (IOException e) {
                                e.printStackTrace();
                            }
//                        else{
//                            Toast.makeText()
//                        }

                    }
                });
            }

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = Fname.getEditText().getText().toString();
                String lname = Lname.getEditText().getText().toString();
                String emailid = Emailid.getEditText().getText().toString();
                String password = Password.getEditText().getText().toString();
                String confpassword = confirmpass.getEditText().getText().toString();
                String mobile = Mobilenumber.getEditText().getText().toString();
                String location_1 = Location.getText().toString();
                String area = Area.getText().toString();

                if(fname.equals("") || lname.equals("") || emailid.equals("") || password.equals("") || confpassword.equals("") || mobile.equals("")  || area.equals("")) {
                    Toast.makeText(UserRegistration.this, "Please enter all the fields", Toast.LENGTH_LONG).show();
                }
                else
                    {
                    if(password.equals(confpassword)){
                        Boolean checkuser = DB.checkEmailid(emailid);
                        if(checkuser==false){
                            Boolean insert = DB.insertData(emailid, fname, lname, password, mobile, location_1, area);
                            if(insert==true){
                                Toast.makeText(UserRegistration.this, "Registered successfully, Please Sign In to continue", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText( UserRegistration.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(UserRegistration.this, "User already exists! please sign in", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText( UserRegistration.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        already_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed(){

        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(intent);

//        super.onBackPressed();
    }
}