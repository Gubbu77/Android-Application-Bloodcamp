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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    private Button loginbutton, registerbtnto_registerpage, bankuserbtn;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bloodcamp-962c0-default-rtdb.firebaseio.com/");
    EditText loginphone, loginpass;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //        loginbutton
        loginphone = findViewById(R.id.loginphone);
        loginpass = findViewById(R.id.loginpass);
        loginbutton = findViewById(R.id.loginbutton);
        registerbtnto_registerpage = findViewById(R.id.registerbtnto_registerpage);

//        intent to register page
        registerbtnto_registerpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), register.class));
            }
        });

//         to bank user
        bankuserbtn = findViewById(R.id.bankuserbtn);
        bankuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), bankuser_login.class));
            }
        });


//        login

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phonetxt = loginphone.getText().toString();
                String loginpasswordtxt = loginpass.getText().toString();


                if (phonetxt.isEmpty() || loginpasswordtxt.isEmpty()) {
                    Toast.makeText(login.this, "Enter phonenumber or password", Toast.LENGTH_SHORT).show();

                } else if (!(phonetxt.length() == 10)) {
                    loginphone.setError("Enter correct number");
                } else {
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(phonetxt)) {

                                String getPassword = snapshot.child(phonetxt).child("Password").getValue(String.class);

                                if (getPassword.equals(loginpasswordtxt)) {



//get user data
                                    String nametxt = snapshot.child(phonetxt).child("Name").getValue(String.class);
                                    String agetxt = snapshot.child(phonetxt).child("Age").getValue(String.class);
                                    String phonenotxt = snapshot.child(phonetxt).child("Phone number").getValue(String.class);
                                    String passtxt = snapshot.child(phonetxt).child("Password").getValue(String.class);

                                    Intent i = new Intent(getApplicationContext(), profile_activity.class);

                                    i.putExtra("name", nametxt);
                                    i.putExtra("age", agetxt);
                                    i.putExtra("phone", phonenotxt);
                                    i.putExtra("pass", passtxt);

                                    startActivity(i);
                                    finish();
                                    Toast.makeText(login.this, "Login successfull", Toast.LENGTH_SHORT).show();

                                } else {
                                    loginpass.setError("Incorrect password");

                                }

                            } else {
                                loginphone.setError("User not found");

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

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        if(authProfile.getCurrentUser() != null){
//
//            Toast.makeText(this, "Already loged in", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
//        }else{
//
//            Toast.makeText(this, "You can login now", Toast.LENGTH_SHORT).show();
//        }
//    }
}