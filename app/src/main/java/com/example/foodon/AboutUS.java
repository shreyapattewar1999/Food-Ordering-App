package com.example.foodon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class AboutUS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
    }


    @Override
    public void onBackPressed(){

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);

//        super.onBackPressed();
    }
}