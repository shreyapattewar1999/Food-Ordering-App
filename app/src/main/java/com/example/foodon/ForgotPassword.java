package com.example.foodon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class ForgotPassword extends AppCompatActivity {

    TextInputLayout ForgotEmail, Forgotpass, ForgotConPass;
    Button ResetPassword;
    TextView JumpToRegisterationActivity;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ForgotEmail = (TextInputLayout) findViewById(R.id.ForgotEmail);
        Forgotpass = (TextInputLayout) findViewById(R.id.Forgotpass);
        ForgotConPass = (TextInputLayout) findViewById(R.id.ForgotConPass);

        ResetPassword = (Button) findViewById(R.id.ResetPassword);
        JumpToRegisterationActivity = (TextView) findViewById(R.id.JumpToRegisterationActivity);

        DB = new DBHelper(this);

        JumpToRegisterationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), UserRegistration.class);
                startActivity(intent);
            }
        });

        ResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foremail = ForgotEmail.getEditText().getText().toString();
                String forpass = Forgotpass.getEditText().getText().toString();
                String forconpass = ForgotConPass.getEditText().getText().toString();

                Toast.makeText(ForgotPassword.this, "password reset method", Toast.LENGTH_LONG).show();

                if (foremail.equals("") || forpass.equals("") || forconpass.equals("")){
                    Toast.makeText(ForgotPassword.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                }
                else if (! forpass.equals(forconpass)){
                    Toast.makeText(ForgotPassword.this, "Passwords not matching", Toast.LENGTH_LONG).show();
                }
                else{
                    Boolean checkemail = DB.checkEmailid(foremail);
                    if (checkemail == true){
                        Boolean status = DB.updatePassword(forpass, foremail);
                        if (status == true){
                            Toast.makeText(ForgotPassword.this, "Your password has been reset, Please sign in to continue", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), SignIn.class);
                            startActivity(intent);
                        }
                        else if (status==false){
                            Toast.makeText(ForgotPassword.this, "Password is not updated due to some error", Toast.LENGTH_LONG).show();

                        }

                    }
                    else if ( checkemail == false){
                        Toast.makeText(ForgotPassword.this, "This Email Id does not exists. Please register yourself.", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });


    }


    @Override
    public void onBackPressed(){

        Intent intent = new Intent(getApplicationContext(), SignIn.class);
        startActivity(intent);

//        super.onBackPressed();
    }
}