package com.example.bloodcamp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listof_blood_requests extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter2 myAdapter2;
    ArrayList<bloodrequests> list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listof_blood_requests);


        recyclerView = findViewById(R.id.list_of_bloodrequestsview);
        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://bloodcamp-962c0-default-rtdb.firebaseio.com/");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list2 = new ArrayList<>();
        myAdapter2 = new MyAdapter2(this,list2);
        recyclerView.setAdapter(myAdapter2);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.child("bloodrequest").getChildren()){

                    bloodrequests user = dataSnapshot.getValue(bloodrequests.class);
                    list2.add(user);


                }
                myAdapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(listof_blood_requests.this, "error", Toast.LENGTH_SHORT).show();

            }
        });


    }
}
