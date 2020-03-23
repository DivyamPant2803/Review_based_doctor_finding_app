package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CardViewHolder> {
    private Context mContext;
    private List<Specialities> specialitiesList;

    private String speciality;

    public RecyclerViewAdapter(Context mContext, List<Specialities> specialitiesList) {
        this.mContext = mContext;
        this.specialitiesList = specialitiesList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.card_view,null);
        CardViewHolder cardViewHolder = new CardViewHolder(view);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder cardViewHolder, int position) {
        final Specialities specialities = specialitiesList.get(position);
        cardViewHolder.textView.setText(specialities.getDocSpeciality());
        cardViewHolder.imageView.setImageDrawable(mContext.getResources().getDrawable(specialities.getImageId()));

        cardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speciality = specialities.getDocSpeciality();

               // Log.v("tmz",""+specialities);
                Intent intent = new Intent(mContext,DoctorActivity.class);
                intent.putExtra("Speciality",speciality);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return specialitiesList.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public CardViewHolder(View itemView){
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
