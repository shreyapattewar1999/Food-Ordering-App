package com.example.foodon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class UpdateAddress extends AppCompatActivity {

    TextInputLayout ActUpdateAddressEmail, ActUpdateAddress;
    Button btn_ActUpdateAddressUpdate;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);

        ActUpdateAddressEmail = (TextInputLayout) findViewById(R.id.ActUpdateAddressEmail);
        ActUpdateAddress = (TextInputLayout) findViewById(R.id.ActUpdateAddress);


        btn_ActUpdateAddressUpdate = (Button) findViewById(R.id.btn_ActUpdateAddressUpdate);

        DB = new DBHelper(this);

        btn_ActUpdateAddressUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid = ActUpdateAddressEmail.getEditText().getText().toString();
                String address = ActUpdateAddress.getEditText().getText().toString();

                if (emailid.equals("") || address.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateAddress.this);

                    builder.setMessage("Please enter all fields").setPositiveButton("Ok", null);

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else{
                    Boolean checkemail = DB.checkEmailid(emailid);

                    if (checkemail==true){
                        Boolean status = DB.updateAddress(address, emailid);
                        if (status == true){
                            Toast.makeText(UpdateAddress.this, "Your address is updated.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else if (status==false){
                            Toast.makeText(UpdateAddress.this, "Your address is not updated due to some error", Toast.LENGTH_LONG).show();

                        }

                    }
                    else{
                        Toast.makeText(UpdateAddress.this, "Please enter valid email address.", Toast.LENGTH_LONG).show();


                    }

                }
            }
        });



    }

    @Override
    public void onBackPressed(){

        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);

        super.onBackPressed();
    }
}