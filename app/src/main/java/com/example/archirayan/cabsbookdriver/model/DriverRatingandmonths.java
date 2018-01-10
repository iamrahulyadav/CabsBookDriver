package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 9/1/18.
 */

public class DriverRatingandmonths {
    @SerializedName("driver_is")
    private String driver_is;

    @SerializedName("star_date")
    private String star_date;

    @SerializedName("language")
    private String language;

    @SerializedName("month_count")
    private String month_count;

    @SerializedName("5_star")
    private String five_star;

    public String getFive_star() {
        return five_star;
    }

    public void setFive_star(String five_star) {
        this.five_star = five_star;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMonth_count() {
        return month_count;
    }

    public void setMonth_count(String month_count) {
        this.month_count = month_count;
    }


    public String getDriver_is() {
        return driver_is;
    }

    public void setDriver_is(String driver_is) {
        this.driver_is = driver_is;
    }

    public String getStar_date() {
        return star_date;
    }

    public void setStar_date(String star_date) {
        this.star_date = star_date;
    }


}
