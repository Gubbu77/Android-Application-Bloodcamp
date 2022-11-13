package com.example.bloodcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register extends AppCompatActivity {

    private TextView backto_loginbtn;
    private Button register;
    private EditText name, phone, age, password, repassword;





    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bloodcamp-962c0-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        backto_loginbtn = findViewById(R.id.backto_loginbtn);
        register = findViewById(R.id.register);


//        login intent
        backto_loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
                finish();

            }
        });

//        register intent
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeration();


            }
        });





    }

    public  void registeration(){

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);


        String nametxt = name.getText().toString();
        String phonetxt = phone.getText().toString();
        String agetxt = age.getText().toString();
        String passwordtxt = password.getText().toString();
        String repasswordtxt = repassword.getText().toString();


        if (nametxt.isEmpty() || phonetxt.isEmpty() || agetxt.isEmpty() || passwordtxt.isEmpty() || repasswordtxt.isEmpty() ) {
            Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show();

        }else if (!passwordtxt.equals(repasswordtxt)){
            repassword.setError("Password not matching");


        }else if (!(phonetxt.length() == 10)){
            phone.setError("Enter correct number");
        } else {databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(phonetxt)){
                        Toast.makeText(register.this, "User already exist please login", Toast.LENGTH_SHORT).show();
                    }else{
                        databaseReference.child("Users").child(phonetxt).child("Name").setValue(nametxt);
                        databaseReference.child("Users").child(phonetxt).child("Phone number").setValue(phonetxt);
                        databaseReference.child("Users").child(phonetxt).child("Age").setValue(agetxt);

                        databaseReference.child("Users").child(phonetxt).child("Password").setValue(passwordtxt);
                        databaseReference.child("Users").child(phonetxt).child("Repass").setValue(repasswordtxt);


                        startActivity(new Intent(getApplicationContext(), login.class));
                        finish();

                        Toast.makeText(register.this, "Registeration successfull", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}
