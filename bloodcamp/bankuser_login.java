package com.example.bloodcamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class bankuser_login extends AppCompatActivity {

    private EditText bankuser_loginphone, bankuser_loginpass;

    private Button bankuser_loginbtn, registerbtnto_bankuserregisterpage;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bloodcamp-962c0-default-rtdb.firebaseio.com/");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankuser_login);

        registerbtnto_bankuserregisterpage = findViewById(R.id.registerbtnto_bankuserregisterpage);
        bankuser_loginbtn = findViewById(R.id.bankuser_loginbtn);
        bankuser_loginphone = findViewById(R.id.bankuser_loginphone);
        bankuser_loginpass = findViewById(R.id.bankuser_loginpass);

        registerbtnto_bankuserregisterpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), bankuser_register.class));
                finish();
            }
        });




//        login

        bankuser_loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phonetxt = bankuser_loginphone.getText().toString();
                String loginpasswordtxt = bankuser_loginpass.getText().toString();


                if (phonetxt.isEmpty() || loginpasswordtxt.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter phonenumber or password", Toast.LENGTH_SHORT).show();

                } else if (!(phonetxt.length() == 10)) {
                    bankuser_loginphone.setError("Enter correct number");
                } else {
                    databaseReference.child("bankUsers").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(phonetxt)) {

                                String getPassword = snapshot.child(phonetxt).child("Password").getValue(String.class);

                                if (getPassword.equals(loginpasswordtxt)) {

                                    startActivity(new Intent(getApplicationContext(), bank_user.class));
                                    finish();

//get user data
                                    Toast.makeText(getApplicationContext(), "Login successfull", Toast.LENGTH_SHORT).show();

                                } else {
                                    bankuser_loginpass.setError("Incorrect password");

                                }

                            } else {
                                bankuser_loginphone.setError("User not found");

                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            }
        });




    }
}