package com.example.bloodcamp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class request_forblood extends AppCompatActivity {

    private Button submitreq_btn, listof_blddonordbtn;
    private Spinner rqblood_bldgroup;
    private EditText rqblood_name, rqblood_age, rqblood_address, rqblood_phone, rqblood_date;
    private String rqblood_nametxt;
    private String rqblood_agetxt;
    private String rqblood_addresstxt;
    private String rqblood_phonetxt;
    private String rqblood_bldgrouptxt;
    private String rqblood_datetxt;

    private TextView txtmarquee;


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bloodcamp-962c0-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_forblood);


        ActivityCompat.requestPermissions(request_forblood.this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);

        rqblood_bldgroup = findViewById(R.id.rqblood_bldgroup);
        rqblood_name = findViewById(R.id.rqblood_name);
        rqblood_age = findViewById(R.id.rqblood_age);
        rqblood_address = findViewById(R.id.rqblood_address);
        rqblood_phone = findViewById(R.id.rqblood_phone);
        rqblood_date = findViewById(R.id.rqblood_date);
        listof_blddonordbtn = findViewById(R.id.listof_blddonordbtn);
        txtmarquee = findViewById(R.id.txtmarquee);



        listof_blddonordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), listof_blood_donors.class));

            }
        });

        submitreq_btn = findViewById(R.id.submitreq_btn);

        submitreq_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitrequest();


            }
        });

// select blood group

        String[] rqblood_bldgrouptxt = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};

        ArrayAdapter<String> bloodgrpadapter = new ArrayAdapter<String>(request_forblood.this, R.layout.item_file, rqblood_bldgrouptxt);

        bloodgrpadapter.setDropDownViewResource(R.layout.item_file);
        rqblood_bldgroup.setAdapter(bloodgrpadapter);
        rqblood_bldgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(request_forblood.this, value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }



    public void submitrequest() {

        rqblood_nametxt = rqblood_name.getText().toString();
        rqblood_agetxt = rqblood_age.getText().toString();
        rqblood_addresstxt = rqblood_address.getText().toString();
        rqblood_phonetxt = rqblood_phone.getText().toString();
        rqblood_datetxt = rqblood_date.getText().toString();
        rqblood_bldgrouptxt = rqblood_bldgroup.getSelectedItem().toString();

        if (rqblood_nametxt.isEmpty() || rqblood_agetxt.isEmpty() || rqblood_addresstxt.isEmpty() || rqblood_phonetxt.isEmpty() || rqblood_bldgrouptxt.isEmpty() || rqblood_datetxt.isEmpty()) {
            Toast.makeText(this, "Please fill the form", Toast.LENGTH_SHORT).show();
        } else if (!(rqblood_phonetxt.length() == 10)) {
            rqblood_phone.setError("Enter correct number");

        } else {

            databaseReference.child("bloodrequest").child(rqblood_nametxt).child("Name").setValue(rqblood_nametxt);
            databaseReference.child("bloodrequest").child(rqblood_nametxt).child("Age").setValue(rqblood_agetxt);
            databaseReference.child("bloodrequest").child(rqblood_nametxt).child("Address").setValue(rqblood_addresstxt);
            databaseReference.child("bloodrequest").child(rqblood_nametxt).child("Bloodgroup").setValue(rqblood_bldgrouptxt);
            databaseReference.child("bloodrequest").child(rqblood_nametxt).child("Phone").setValue(rqblood_phonetxt);
            databaseReference.child("bloodrequest").child(rqblood_nametxt).child("Date").setValue(rqblood_datetxt);

            Toast.makeText(this, "Request submitted", Toast.LENGTH_SHORT).show();

            functionon_datachange();


        }
    }


    //////////////////////////////// noification
    public void notification() {

        rqblood_nametxt = rqblood_name.getText().toString();
        rqblood_bldgrouptxt = rqblood_bldgroup.getSelectedItem().toString();
        String message = rqblood_nametxt + " " + rqblood_bldgrouptxt;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "n")

                .setContentTitle("New blood request")
                .setSmallIcon(R.drawable.ic_appiconnoti)
                .setColor(Color.RED)

                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))

                .setContentText(message)
                .setTicker(message)
                .setSound(defaultSoundUri)
                .setVibrate(new long[]{1000, 1000, 1000});


        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(999, builder.build());


    }

    public void functionon_datachange() {

//////////////////////////////////////////  on data change notification
        databaseReference.child("bloodrequest").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                notification();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }








}