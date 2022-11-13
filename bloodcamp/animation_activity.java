package com.example.bloodcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class animation_activity extends AppCompatActivity {

    private static int SPLASH_SCREEN=4000;

    Animation text_animation, image_animation;
    ImageView animimage;
    TextView animtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        text_animation = AnimationUtils.loadAnimation(this,R.anim.text_animation);
        image_animation = AnimationUtils.loadAnimation(this,R.anim.image_animation);

        animimage = findViewById(R.id.animimage);
        animtext = findViewById(R.id.animtext);

        animtext.setAnimation(text_animation);
        animimage.setAnimation(image_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent= new Intent(animation_activity.this, login.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_SCREEN);


    }
}