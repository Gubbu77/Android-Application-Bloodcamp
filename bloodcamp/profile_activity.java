package com.example.bloodcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class profile_activity extends AppCompatActivity {

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


    private EditText db_age, db_phone, db_pass;
    private String user_name, user_age, user_phone, user_pass;
    private Button logout_btn;
    private EditText db_name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        bottomNavigationView = findViewById(R.id.bottom_navigator);

        bottomNavigationView.setSelectedItemId(R.id.profile_page);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.profile_page:
                        finish();
                        return true;

                    case R.id.home_page:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        finish();



                }
                return false;
            }
        });

//        intent
        logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), login.class));
                finish();
            }
        });


        db_name = findViewById(R.id.db_name);
        db_age = findViewById(R.id.db_age);
        db_phone = findViewById(R.id.db_phone);
        db_pass = findViewById(R.id.db_pass);

        showUserProfile();





    }
    public void showUserProfile(){
        Intent i = getIntent();
         user_name= i.getStringExtra("name");
         user_age= i.getStringExtra("age");
         user_phone= i.getStringExtra("phone");
         user_pass= i.getStringExtra("pass");

         db_name.setText(user_name);
         db_age.setText(user_age);
         db_phone.setText(user_phone);
         db_pass.setText(user_pass);
    }
}