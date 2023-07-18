package com.github.aoxter.ThatWasGreat.model;

public enum RatingForm {
    TIER("Tier list"),
    STARS("Stars"),
    OneToTen("Rating from 1 to 10");

    private String name;

    private RatingForm(String name){
        this.name = name;
    }
    public static RatingForm getDefault(){
        return STARS;
    }

    @Override
    public String toString() {
        return name;
    }
}
