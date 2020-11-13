package com.example.foodon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class UpdatePassword extends AppCompatActivity {

    TextInputLayout UpdatePasswordEmail, UpdatePasswordPass, UpdatePasswordConPass;
    Button btn_ActUpdatePassword;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        UpdatePasswordEmail = (TextInputLayout) findViewById(R.id.UpdatePasswordEmail);
        UpdatePasswordPass = (TextInputLayout) findViewById(R.id.UpdatePasswordPass);
        UpdatePasswordConPass = (TextInputLayout) findViewById(R.id.UpdatePasswordConPass);

        btn_ActUpdatePassword = (Button) findViewById(R.id.btn_ActUpdatePassword);

        DB = new DBHelper(this);

        btn_ActUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foremail = UpdatePasswordEmail.getEditText().getText().toString();
                String forpass = UpdatePasswordPass.getEditText().getText().toString();
                String forconpass = UpdatePasswordConPass.getEditText().getText().toString();

                Toast.makeText(UpdatePassword.this, "password reset method", Toast.LENGTH_LONG).show();

                if (foremail.equals("") || forpass.equals("") || forconpass.equals("")) {
                    Toast.makeText(UpdatePassword.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                } else if (!forpass.equals(forconpass)) {
                    Toast.makeText(UpdatePassword.this, "Passwords not matching", Toast.LENGTH_LONG).show();
                } else {
                    Boolean checkemail = DB.checkEmailid(foremail);
                    if (checkemail == true) {
                        Boolean status = DB.updatePassword(forpass, foremail);
                        if (status == true) {
                            Toast.makeText(UpdatePassword.this, "Your password has been reset", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(intent);
                        } else if (status == false) {
                            Toast.makeText(UpdatePassword.this, "Password is not updated due to some error", Toast.LENGTH_LONG).show();

                        }

                    } else if (checkemail == false) {
                        Toast.makeText(UpdatePassword.this, "This Email Id does not exists. Please register yourself.", Toast.LENGTH_LONG).show();

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