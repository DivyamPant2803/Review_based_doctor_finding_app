package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity {

    private String docName,docAddress,docContact;
    private double rating;
    private String speciality,speciality1;
    RecyclerView recyclerView;
    RecyclerViewAdapterDoctor recyclerViewAdapterDoctor;

    ProgressDialog progressDialog;

    List<Doctors> list = new ArrayList<>();
    List<String>list1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        Intent intent = getIntent();
        speciality = intent.getStringExtra("Speciality");
        speciality1 = speciality.replace(" ","");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(speciality1);

        //list = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewDoctor);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(DoctorActivity.this);
        recyclerView.setLayoutManager(manager);

        progressDialog = new ProgressDialog(DoctorActivity.this);
        progressDialog.setMessage("Finding doctors near you");
        progressDialog.show();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    /*docName = ds.child("Name").getValue().toString();
                    docAddress = ds.child("Address").getValue().toString();
                    docContact = ds.child("Phone").getValue().toString();
                    rating = Double.parseDouble(ds.child("Rating").getValue().toString());
                    Log.v("tmz",""+docName);
                    Log.v("tmz",""+docAddress);
                    Log.v("tmz",""+docContact);
                    Log.v("tmz",""+rating);*/

                    Doctors doctors = ds.getValue(Doctors.class);
                    list.add(doctors);

                    /*Doctors doctors = new Doctors(docName,docAddress,docContact,rating);
                    list1.add(doctors.getDocName());
                    list1.add(doctors.getDocAddress());
                    list1.add(doctors.getDocContact());
                    list1.add(String.valueOf(doctors.getRating()));*/
                }
                recyclerViewAdapterDoctor = new RecyclerViewAdapterDoctor(DoctorActivity.this,list,speciality);
                recyclerView.setAdapter(recyclerViewAdapterDoctor);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
}
