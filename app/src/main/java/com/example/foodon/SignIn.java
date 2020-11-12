package com.example.foodon;

import androidx.appcompat.app.AppCompatActivity;
import com.example.foodon.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

import com.google.android.material.textfield.TextInputLayout;

public class SignIn extends AppCompatActivity {

    TextInputLayout Lemail, Lpassword;
    Button Login_btn;
    TextView JumpToRegisteration, ForgotPassword;
    DBHelper DB;
    Boolean checkuserpass;

    String login_email, login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        DB = new DBHelper(this);
        Lemail = (TextInputLayout)findViewById(R.id.Lemail);
        Lpassword = (TextInputLayout)findViewById(R.id.Lpassword);

        Login_btn = (Button)findViewById(R.id.Login_btn);
        JumpToRegisteration =(TextView) findViewById(R.id.JumpToRegisteration);
        ForgotPassword = (TextView) findViewById(R.id.ForgotPassword);

        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(i);

            }
        });

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_email = Lemail.getEditText().getText().toString();
                login_password = Lpassword.getEditText().getText().toString();

//                Toast.makeText(SignIn.this, login_email + login_password, Toast.LENGTH_SHORT).show();


                if(login_email.equals("") || login_password.equals("")){
                    Toast.makeText(SignIn.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();}
                else{
//                    int status =Integer.parseInt( DB.getLoginData(login_email, login_password));
                    Boolean status = DB.checkEmailpassword(login_email, login_password);
                    if (status==true) {
                        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_LONG).show();

                    }
                }}

        });

        JumpToRegisteration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                login_email = Lemail.getEditText().getText().toString();
//                login_password = Lpassword.getEditText().getText().toString();
//
//                Toast.makeText(SignIn.this, login_email + login_password, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), UserRegistration.class);
                startActivity(intent);
            }
        });

    }
}