package com.example.bloodcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class bank_user extends AppCompatActivity {

    private EditText bloodbank_name, bloodbank_phone, bloodbank_age, bloodbank_address, bloodbank_date;
    private Button bankbloodsub_btn;
    private Spinner bloodbank_bldgroup;
    private String blooddonar_nametxt, blooddonar_phonetxt, blooddonar_agetxt, blooddonar_addresstxt, blooddonar_bldgrouptxt, blooddonar_datetxt;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bloodcamp-962c0-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_user);

        bloodbank_name = findViewById(R.id.bloodbank_name);
        bloodbank_phone = findViewById(R.id.bloodbank_phone);
        bloodbank_age = findViewById(R.id.bloodbank_age);
        bloodbank_address = findViewById(R.id.bloodbank_address);
        bloodbank_bldgroup = findViewById(R.id.bloodbank_bldgroup);
        bloodbank_date = findViewById(R.id.bloodbank_date);

        bankbloodsub_btn = findViewById(R.id.bankbloodsub_btn);


        String[] blooddonar_bldgrouptxt = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};

        ArrayAdapter<String> bloodgrpadapter = new ArrayAdapter<String>(bank_user.this,R.layout.item_file,blooddonar_bldgrouptxt);

        bloodgrpadapter.setDropDownViewResource(R.layout.item_file);
        bloodbank_bldgroup.setAdapter(bloodgrpadapter);
        bloodbank_bldgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        bankbloodsub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bloodbank_submit();
            }
        });




    }
    public void bloodbank_submit(){
        blooddonar_nametxt = bloodbank_name.getText().toString();
        blooddonar_agetxt = bloodbank_age.getText().toString();
        blooddonar_addresstxt = bloodbank_address.getText().toString();
        blooddonar_phonetxt = bloodbank_phone.getText().toString();
        blooddonar_datetxt = bloodbank_date.getText().toString();
        blooddonar_bldgrouptxt = bloodbank_bldgroup.getSelectedItem().toString();

        if (blooddonar_nametxt.isEmpty() || blooddonar_agetxt.isEmpty() || blooddonar_addresstxt.isEmpty() || blooddonar_phonetxt.isEmpty() || blooddonar_bldgrouptxt.isEmpty()) {
            Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show();
        }else if (!(blooddonar_phonetxt.length() == 10)){
            bloodbank_phone.setError("Enter correct number");

        } else {

            databaseReference.child("bloodbank").child(blooddonar_nametxt).child("Name").setValue(blooddonar_nametxt);
            databaseReference.child("bloodbank").child(blooddonar_nametxt).child("Age").setValue(blooddonar_agetxt);
            databaseReference.child("bloodbank").child(blooddonar_nametxt).child("Address").setValue(blooddonar_addresstxt);
            databaseReference.child("bloodbank").child(blooddonar_nametxt).child("Bloodgroup").setValue(blooddonar_bldgrouptxt);
            databaseReference.child("bloodbank").child(blooddonar_nametxt).child("Phone").setValue(blooddonar_phonetxt);
            databaseReference.child("bloodbank").child(blooddonar_nametxt).child("Date").setValue(blooddonar_datetxt);

            Toast.makeText(this, "Request submitted", Toast.LENGTH_SHORT).show();

        }


    }

}