package com.example.myapplication;

public class Doctors {
    String Name,Address,Phone;
    float Rating;

    public Doctors(){}  // For Firebase Database

    public Doctors(String Name, String Address, String Phone, float Rating) {
        this.Name = Name;
        this.Address = Address;
        this.Phone = Phone;
        this.Rating = Rating;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }

}
