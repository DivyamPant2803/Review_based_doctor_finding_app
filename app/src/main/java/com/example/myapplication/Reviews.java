package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Reviews extends AppCompatActivity {

    private TextView nameText,addressText,hoursText,reviewText;
    private Button rateDoctor;
    private RecyclerView recyclerView;
    private FragmentManager fragmentManager;
    //private RecyclerViewAdapterReview recyclerViewAdapterReview;
    private String name,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        fragmentManager = getSupportFragmentManager();
        nameText = findViewById(R.id.nameText);
        addressText = findViewById(R.id.addressText);
        hoursText = findViewById(R.id.hoursText);
        rateDoctor = findViewById(R.id.rateDoctor);
        reviewText = findViewById(R.id.review);

        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        address = intent.getStringExtra("Address");

        Log.v("tmz",""+name);
        Log.v("tmz",""+address);

        nameText.setText(name);
        addressText.setText(address);

        rateDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Reviews.this,UserReview.class);
                intent1.putExtra("Name",name);
                startActivity(intent1);
            }
        });
        /*reviewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Reviews.this,MapActivity.class);
                intent1.putExtra("Address",address);
                startActivity(intent1);
            }
        });*/
        Bundle bundle = new Bundle();
        bundle.putString("Address",addressText.getText().toString());
        bundle.putString("Name",nameText.getText().toString());
        FragmentMap  fragmentMap = new FragmentMap();
        fragmentMap.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.frameLayout,fragmentMap).commit();
    }

}
