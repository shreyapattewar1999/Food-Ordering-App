package com.example.foodon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainMenu extends AppCompatActivity {
    Button new_user, place_order, post_review;
    Intent intent;
    String type;
    ConstraintLayout bgimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


//        AnimationDrawable animationDrawable = new AnimationDrawable();
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.bg2),3000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img2),3000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img3),3000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img4),3000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img5),3000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img6),3000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img7),3000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img8),3000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img9),3000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img10),3000);
//        animationDrawable.addFrame(getResources().getDrawable(R.drawable.img11),3000);
//
//        animationDrawable.setOneShot(false);
//        animationDrawable.setEnterFadeDuration(850);
//        animationDrawable.setEnterFadeDuration(1600);
//
//        bgimage = findViewById(R.id.back3);
//        bgimage.setBackgroundDrawable(animationDrawable);
//        animationDrawable.start();
//
//        intent = getIntent();
//        type = intent.getStringExtra("Home").toString().trim();

        new_user = (Button)findViewById(R.id.newuser);
        place_order = (Button)findViewById(R.id.placeorder);
        post_review = (Button)findViewById(R.id.postreview);

        new_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, UserRegistration.class);
                startActivity(intent);
                finish();



            }
        });

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });
        post_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenu.this, GiveReview.class);
                startActivity(intent);
                finish();
            }
        });





    }
}
