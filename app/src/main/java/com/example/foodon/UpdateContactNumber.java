package com.example.foodon;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import static android.widget.Toast.LENGTH_LONG;

public class UpdateContactNumber extends AppCompatActivity {

    TextInputLayout ActUpdateContactEmail, ActUpdateContact;
    Button update_btn;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact_number);

        ActUpdateContactEmail = (TextInputLayout) findViewById(R.id.ActUpdateContactEmail);
        ActUpdateContact = (TextInputLayout) findViewById(R.id.ActUpdateContact);

        update_btn = (Button) findViewById(R.id.btn_ActUpdateContactUpdate);


        DB = new DBHelper(this);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailid = ActUpdateContactEmail.getEditText().getText().toString();
                String contact = ActUpdateContact.getEditText().getText().toString();

                if (emailid.equals("") || contact.equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateContactNumber.this);

                    builder.setMessage("Please enter all fields").setPositiveButton("Ok", null);

                    AlertDialog alert = builder.create();
                    alert.show();
                }
                else{
                    Boolean checkemail = DB.checkEmailid(emailid);

                    if (checkemail==true){
                        Boolean status = DB.updateContactNumber(contact, emailid);
                        if (status == true){
                            Toast.makeText(UpdateContactNumber.this, "Your contact number is updated.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else if (status==false){
                            Toast.makeText(UpdateContactNumber.this, "Your contact number is not updated due to some error", Toast.LENGTH_LONG).show();

                        }

                    }
                    else{
                        Toast.makeText(UpdateContactNumber.this, "Please enter valid email address.", Toast.LENGTH_LONG).show();


                    }

                }

            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }
}