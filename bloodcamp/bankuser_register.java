package com.example.bloodcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class bankuser_register extends AppCompatActivity {

    private Button  bankuser_registerbtn;
    private TextView backto_bankuserloginbtn;
    private EditText bankuser_name, bankuser_phone, bankuser_age, bankuser_password, bankuser_repassword;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bloodcamp-962c0-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankuser_register);

        backto_bankuserloginbtn = findViewById(R.id.backto_bankuserloginbtn);
        bankuser_registerbtn = findViewById(R.id.bankuser_registerbtn);
        bankuser_name = findViewById(R.id.bankuser_name);
        bankuser_phone = findViewById(R.id.bankuser_phone);
        bankuser_age = findViewById(R.id.bankuser_age);
        bankuser_password = findViewById(R.id.bankuser_password);
        bankuser_repassword = findViewById(R.id.bankuser_repassword);


        backto_bankuserloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), bankuser_login.class));
                finish();
            }
        });



        bankuser_registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeration();


            }
        });





    }

    public  void registeration(){



        String nametxt = bankuser_name.getText().toString();
        String phonetxt = bankuser_phone.getText().toString();
        String agetxt = bankuser_age.getText().toString();
        String passwordtxt = bankuser_password.getText().toString();
        String repasswordtxt = bankuser_repassword.getText().toString();


        if (nametxt.isEmpty() || phonetxt.isEmpty() || agetxt.isEmpty() || passwordtxt.isEmpty() || repasswordtxt.isEmpty() ) {
            Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show();

        }else if (!passwordtxt.equals(repasswordtxt)){
            bankuser_repassword.setError("Password not matching");


        }else if (!(phonetxt.length() == 10)){
            bankuser_phone.setError("Enter correct number");
        } else {databaseReference.child("bankUsers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(phonetxt)){
                    Toast.makeText(getApplicationContext(), "User already exist please login", Toast.LENGTH_SHORT).show();
                }else{
                    databaseReference.child("bankUsers").child(phonetxt).child("Name").setValue(nametxt);
                    databaseReference.child("bankUsers").child(phonetxt).child("Phone number").setValue(phonetxt);
                    databaseReference.child("bankUsers").child(phonetxt).child("Age").setValue(agetxt);

                    databaseReference.child("bankUsers").child(phonetxt).child("Password").setValue(passwordtxt);
                    databaseReference.child("bankUsers").child(phonetxt).child("Repass").setValue(repasswordtxt);


                    startActivity(new Intent(getApplicationContext(), login.class));
                    finish();

                    Toast.makeText(getApplicationContext(), "Registeration successfull", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }





    }
}