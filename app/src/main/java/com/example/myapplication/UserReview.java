package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class UserReview extends AppCompatActivity implements SmileRating.OnSmileySelectionListener{

    private TextView nameText;
    private EditText review;
    //private RatingBar ratingBar;
    private Button submitButton;
    private String name;
    private float userRating;
    private int i=1,sDoc,sCl,sSt,sMo,avg;
    private SmileRating smileRatingDoctor,smileRatingCleanliness,smileRatingStaff,smileRatingMoney,smileRatingAvg;


    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRef = mDatabase.getReference();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);

        nameText = findViewById(R.id.nameText);
        review = findViewById(R.id.reviewText);
        //ratingBar = findViewById(R.id.ratingBarDoctor);
        submitButton = findViewById(R.id.submitButton);
        smileRatingDoctor = findViewById(R.id.smile_ratingDoctor);
        smileRatingCleanliness = findViewById(R.id.smile_ratingCleanliness);
        smileRatingStaff = findViewById(R.id.smile_ratingStaff);
        smileRatingMoney = findViewById(R.id.smile_ratingMoney);
        //smileRatingAvg = findViewById(R.id.smile_ratingAvg);

        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        smileRatingDoctor.setOnSmileySelectionListener(this);
        smileRatingCleanliness.setOnSmileySelectionListener(this);
        smileRatingMoney.setOnSmileySelectionListener(this);
        smileRatingStaff.setOnSmileySelectionListener(this);



        /*nameText.setText(name);
        ratingBar.setNumStars(5);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(UserReview.this,"Your Rating "+rating,Toast.LENGTH_SHORT).show();
                userRating = rating;
            }
        });*/

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("tmz",""+i);
                Log.v("tmz",""+review.getText().toString());
                ReviewBase reviewBase = new ReviewBase(review.getText().toString(),userRating);
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                i = preferences.getInt("index",1);
                i++;
                Log.v("tmz",""+i);
                mRef = mDatabase.getReference().child("Cardiologist").child("1").child("Review "+i);
                mRef.setValue(reviewBase);
                Toast.makeText(UserReview.this,"Review Submited",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(UserReview.this,MainActivity.class);
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt("index",i);
        editor.apply();
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt("index",i);
        editor.apply();
        super.onPause();
    }

    @Override
    public void onSmileySelected(int smiley, boolean reselected) {
        switch (smiley) {
            case SmileRating.BAD:
                Log.i("Rating", "Bad");
                break;

            case SmileRating.GOOD:
                Log.i("Rating", "Good");
                break;

            case SmileRating.GREAT:
                Log.i("Rating", "Great");
                break;

            case SmileRating.OKAY:
                Log.i("Rating", "okay");
                break;

            case SmileRating.TERRIBLE:
                Log.i("Rating", "terrible");
                break;

            case SmileRating.NONE:
                break;
        }
        Log.i("SMILE",""+smileRatingDoctor.getRating());
        sDoc = smileRatingDoctor.getRating();
        sCl = smileRatingCleanliness.getRating();
        sMo = smileRatingMoney.getRating();
        sSt = smileRatingStaff.getRating();

        userRating = (sDoc+sCl+sMo+sSt)/4;
        //smileRatingAvg.setSelectedSmile(avg);
        //avgRating();
    }

}
