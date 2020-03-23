package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RecyclerViewAdapterDoctor extends RecyclerView.Adapter<RecyclerViewAdapterDoctor.CardViewHolder>{

    private Context mContext;
    private List<Doctors> list;
    private String speciality;

    public RecyclerViewAdapterDoctor(Context mContext, List<Doctors> list,String speciality) {
        this.mContext = mContext;
        this.list = list;
        this.speciality = speciality;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_view_doctor,null);
        CardViewHolder cardViewHolder = new CardViewHolder(view);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int position) {
        final Doctors doctors = list.get(position);
        float rating = doctors.getRating();
        //Log.v("tmz",""+list);
        cardViewHolder.doctorName.setText(doctors.getName());
        cardViewHolder.doctorAddress.setText(doctors.getAddress());
        cardViewHolder.doctorContact.setText(doctors.getPhone());
        cardViewHolder.rating.setText(String.valueOf(doctors.getRating()));
        cardViewHolder.doctorSpeciality.setText(speciality);
        cardViewHolder.ratingBar.setRating(rating);

        cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,Reviews.class);
                intent.putExtra("Name",doctors.getName());
                intent.putExtra("Address",doctors.getAddress());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class CardViewHolder extends RecyclerView.ViewHolder{
        TextView doctorName,doctorSpeciality,rating,doctorAddress,doctorContact;
        RatingBar ratingBar;

        public CardViewHolder(View itemView){
            super(itemView);

            doctorSpeciality = itemView.findViewById(R.id.doctorSpeciality);
            doctorName = itemView.findViewById(R.id.doctorName);
            doctorAddress = itemView.findViewById(R.id.doctorAddress);
            doctorContact = itemView.findViewById(R.id.doctorContact);
            rating = itemView.findViewById(R.id.rating);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            ratingBar.setIsIndicator(true);

        }

    }
}
