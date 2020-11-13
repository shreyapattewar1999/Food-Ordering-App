package com.example.foodon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.widget.Toast.LENGTH_LONG;

public class HomeActivity extends AppCompatActivity {

    CheckBox pizza, burger, pavbhaji, chicken, biryani, daltadka;
    FloatingActionButton orderFood;
    Integer price=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pizza = (CheckBox) findViewById(R.id.pizza_check);
        burger = (CheckBox) findViewById(R.id.burger_check);
        biryani = (CheckBox) findViewById(R.id.biryani_check);
        daltadka = (CheckBox) findViewById(R.id.daltadka_check);
        chicken = (CheckBox) findViewById(R.id.chickentikka_check);
        pavbhaji = (CheckBox) findViewById(R.id.pavbhaji_check);

        orderFood = (FloatingActionButton) findViewById(R.id.orderFood);

        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pizza.isChecked()){
                    price=price+300;
                }
                else if (! pizza.isChecked()){
                    price=price-300;
                }
            }
        });

        burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (burger.isChecked()){
                    price=price+180;
                }
                else if (! burger.isChecked()){
                    price=price-180;
                }
            }
        });

        biryani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (biryani.isChecked()){
                    price=price+200;
                }
                else if (! biryani.isChecked()){
                    price=price-200;
                }
            }
        });

        chicken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chicken.isChecked()){
                    price=price+600;
                }
                else if (! chicken.isChecked()){
                    price=price-600;
                }
            }
        });

        daltadka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daltadka.isChecked()){
                    price=price+60;
                }
                else if (! daltadka.isChecked()){
                    price=price-60;
                }
            }
        });

        pavbhaji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pavbhaji.isChecked()){
                    price=price+150;
                }
                else if (! pavbhaji.isChecked()){
                    price=price+150;
                }
            }
        });

//

//        final String result = Integer.toString(price);

        orderFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

                builder.setTitle("Place Order").setMessage("Your cart amount is "+Integer.toString(price)+". Press Ok to place your order").setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(HomeActivity.this, "Your order has been placed. Food will be delivered to you in 30 minutes" , LENGTH_LONG).show();

                        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                        startActivity(intent);

                    }
                }).setNegativeButton("Cancel", null);

                AlertDialog alert = builder.create();
                alert.show();


            }
        });


    }
    @Override
        public void onBackPressed(){

            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);

            builder.setTitle("Really Exit ?").setMessage("Are you sure ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(intent);
                }
            }).setNegativeButton("No", null).setCancelable(false);

            AlertDialog alert = builder.create();
            alert.show();

            super.onBackPressed();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            switch(item.getItemId())
            {



                case R.id.UpdateContact:
                    Toast.makeText(this, "Update Contact selected", LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), UpdateContactNumber.class);
                    startActivity(intent);
                    break;

                case R.id.UpdatePassword:
                    Toast.makeText(this, "Update Password selected", LENGTH_LONG).show();
                    Intent intent_1 = new Intent(getApplicationContext(), UpdatePassword.class);
                    startActivity(intent_1);
                    break;

                case R.id.AddressUpdate:
                    Toast.makeText(this, "Update address selected", LENGTH_LONG).show();
                    Intent intent_2 = new Intent(getApplicationContext(), UpdateAddress.class);
                    startActivity(intent_2);
                    break;


                case R.id.AboutUs:
                    Intent intent_aboutus = new Intent(getApplicationContext(), AboutUS.class);
                    startActivity(intent_aboutus);
                    break;

                case R.id.Logout: {
                    Toast.makeText(this, "You have been logged out", LENGTH_LONG).show();
                    Intent intent_logout = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(intent_logout);
                    break;
                }
            }



        return super.onOptionsItemSelected(item);
        }

}