package com.example.bloodcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class donate_blood_submit extends AppCompatActivity {
    private Button donatebloodsub_btn, listof_bldrequestbtn;
    private EditText blooddonar_name, blooddonar_phone, blooddonar_age, blooddonar_address, blooddonar_date;
    private String blooddonar_nametxt, blooddonar_phonetxt, blooddonar_agetxt, blooddonar_addresstxt, blooddonar_bldgrouptxt, blooddonar_datetxt;
    private Spinner blooddonar_bldgroup;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bloodcamp-962c0-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_blood_submit);


        blooddonar_name = findViewById(R.id.blooddonar_name);
        blooddonar_phone = findViewById(R.id.blooddonar_phone);
        blooddonar_age = findViewById(R.id.blooddonar_age);
        blooddonar_address = findViewById(R.id.blooddonar_address);
        blooddonar_bldgroup = findViewById(R.id.blooddonar_bldgroup);
        blooddonar_date = findViewById(R.id.blooddonar_date);


//        marquee




//        ##############

        donatebloodsub_btn = findViewById(R.id.donatebloodsub_btn);

        //        intent to list of blood requests

        listof_bldrequestbtn = findViewById(R.id.listof_bldrequestbtn);
        listof_bldrequestbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), listof_blood_requests.class));
            }
        });



// select blood group

        String[] blooddonar_bldgrouptxt = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};

        ArrayAdapter<String> bloodgrpadapter = new ArrayAdapter<String>(donate_blood_submit.this,R.layout.item_file,blooddonar_bldgrouptxt);

        bloodgrpadapter.setDropDownViewResource(R.layout.item_file);
        blooddonar_bldgroup.setAdapter(bloodgrpadapter);
        blooddonar_bldgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(donate_blood_submit.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        donatebloodsub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                donateblood();
            }
        });


    }

    public void donateblood(){
        blooddonar_nametxt = blooddonar_name.getText().toString();
        blooddonar_agetxt = blooddonar_age.getText().toString();
        blooddonar_addresstxt = blooddonar_address.getText().toString();
        blooddonar_phonetxt = blooddonar_phone.getText().toString();
        blooddonar_datetxt = blooddonar_date.getText().toString();
        blooddonar_bldgrouptxt = blooddonar_bldgroup.getSelectedItem().toString();

        if (blooddonar_nametxt.isEmpty() || blooddonar_agetxt.isEmpty() || blooddonar_addresstxt.isEmpty() || blooddonar_phonetxt.isEmpty() || blooddonar_bldgrouptxt.isEmpty()) {
            Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show();
        }else if (!(blooddonar_phonetxt.length() == 10)){
            blooddonar_phone.setError("Enter correct number");

        } else {

            databaseReference.child("donar").child(blooddonar_nametxt).child("Name").setValue(blooddonar_nametxt);
            databaseReference.child("donar").child(blooddonar_nametxt).child("Age").setValue(blooddonar_agetxt);
            databaseReference.child("donar").child(blooddonar_nametxt).child("Address").setValue(blooddonar_addresstxt);
            databaseReference.child("donar").child(blooddonar_nametxt).child("Bloodgroup").setValue(blooddonar_bldgrouptxt);
            databaseReference.child("donar").child(blooddonar_nametxt).child("Phone").setValue(blooddonar_phonetxt);
            databaseReference.child("donar").child(blooddonar_nametxt).child("Date").setValue(blooddonar_datetxt);

            Toast.makeText(this, "Request submitted", Toast.LENGTH_SHORT).show();

        }


    }
}