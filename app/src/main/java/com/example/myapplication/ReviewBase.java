package com.example.myapplication;

public class ReviewBase {

    private String review;
    private float userRating;

    public ReviewBase(){
    }

    public ReviewBase(String review, float userRating) {
        this.review = review;
        this.userRating = userRating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }
}
