package com.example.bloodcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //    backpresss ###################################################################################
    private long backpress;
    Toast backtoast;

    @Override
    public void onBackPressed() {
        if (backpress + 2000 > System.currentTimeMillis()){
            backtoast.cancel();
            super.onBackPressed();
            return;
        }else {
            backtoast = Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT);
            backtoast.show();
        }
        backpress = System.currentTimeMillis();
    }


//    ##############################################################################################

    BottomNavigationView bottomNavigationView;
    RelativeLayout donate_blood, blood_bank, blood_request, notification_lay, instructions_lay;

    ImageButton about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigator);

        bottomNavigationView.setSelectedItemId(R.id.home_page);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.profile_page:
                        startActivity(new Intent(getApplicationContext(), profile_activity.class));
                        overridePendingTransition(0,0);


                    case R.id.home_page:
                        return true;


                }
                return false;
            }
        });

//        donateblood

        donate_blood=findViewById(R.id.donate_blood);
        donate_blood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,donate_blood_submit.class);
                startActivity(intent);
            }
        });

        //        blood bank

        blood_bank=findViewById(R.id.blood_bank);
        blood_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,pkdas_bank.class);
                startActivity(intent);
            }
        });


        //        blood request

        blood_request=findViewById(R.id.blood_request);
        blood_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,request_forblood.class);
                startActivity(intent);
            }
        });


        //     notification

        notification_lay=findViewById(R.id.notification_lay);
        notification_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,notification_page.class);
                startActivity(intent);
            }
        });


        //        instructions

        instructions_lay=findViewById(R.id.instructions_lay);
        instructions_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,instructions.class);
                startActivity(intent);
            }
        });

//        about
        about=findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,about_page.class);
                startActivity(intent);
            }
        });

    }
}