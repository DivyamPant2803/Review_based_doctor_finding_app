package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Specialities> specialitiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean finish = getIntent().getBooleanExtra("finish",false);
        if(finish){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);



        specialitiesList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager mGrid = new GridLayoutManager(MainActivity.this,2);
        recyclerView.setLayoutManager(mGrid);

        specialitiesList.add(new Specialities("Cardiologist",R.drawable.cardiologist));
        specialitiesList.add(new Specialities("Child Specialist",R.drawable.child));
        specialitiesList.add(new Specialities("Dentist",R.drawable.dentist));
        specialitiesList.add(new Specialities("ENT",R.drawable.ent));
        specialitiesList.add(new Specialities("Eye Specialist",R.drawable.eye));
        specialitiesList.add(new Specialities("Physician",R.drawable.physician));

        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this,specialitiesList);
        recyclerView.setAdapter(recyclerViewAdapter);






    }
}
