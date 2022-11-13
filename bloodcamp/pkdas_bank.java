package com.example.bloodcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class pkdas_bank extends AppCompatActivity {

    RelativeLayout pkdasblood_bank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pkdas_bank);

        pkdasblood_bank = findViewById(R.id.pkdasblood_bank);
        pkdasblood_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), blood_bank_activity.class));

            }
        });
    }
}