package com.example.myapplication;

import android.util.Log;

public class Specialities {
    private String docSpeciality;
    private int imageId;

    public Specialities(String docSpeciality, int imageId) {
        this.docSpeciality = docSpeciality;
        this.imageId = imageId;
    }

    public String getDocSpeciality() {
        //docSpeciality = docSpeciality.replace(" ","");
        //Log.v("tmz",""+docSpeciality);
        return docSpeciality;
    }

    public void setDocSpeciality(String docSpeciality) {
        this.docSpeciality = docSpeciality;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
