package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 30/11/17.
 */

public class Earningsblance {
    @SerializedName("week_fare")
    private String week_fare;

    @SerializedName("total_balance")
    private String total_balance;

    public String getTotal_trip_count() {
        return total_trip_count;
    }

    public void setTotal_trip_count(String total_trip_count) {
        this.total_trip_count = total_trip_count;
    }

    @SerializedName("total_trip_count")
    private String total_trip_count;

    public String getWeek_fare() {
        return week_fare;
    }

    public void setWeek_fare(String week_fare) {
        this.week_fare = week_fare;
    }

    public String getTotal_balance() {
        return total_balance;
    }

    public void setTotal_balance(String total_balance) {
        this.total_balance = total_balance;
    }




}
