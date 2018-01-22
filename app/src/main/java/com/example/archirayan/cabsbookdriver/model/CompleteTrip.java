package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 19/1/18.
 */

public class CompleteTrip {
    @SerializedName("trip_id")
    private String trip_id;

    @SerializedName("address")
    private String address;

    @SerializedName("address2")
    private String address2;

    @SerializedName("trip_date")
    private String trip_date;

    @SerializedName("book_date")
    private String book_date;

    @SerializedName("driver_id")
    private String driver_id;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("car_type")
    private String car_type;

    @SerializedName("texi")
    private String texi;

    @SerializedName("start_time")
    private String start_time;

    @SerializedName("edn_time")
    private String edn_time;

    @SerializedName("km")
    private String km;

    @SerializedName("fare")
    private String fare;

    @SerializedName("payment_method")
    private String payment_method;

    @SerializedName("trip_status")
    private String trip_status;

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getTrip_date() {
        return trip_date;
    }

    public void setTrip_date(String trip_date) {
        this.trip_date = trip_date;
    }

    public String getBook_date() {
        return book_date;
    }

    public void setBook_date(String book_date) {
        this.book_date = book_date;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getTexi() {
        return texi;
    }

    public void setTexi(String texi) {
        this.texi = texi;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEdn_time() {
        return edn_time;
    }

    public void setEdn_time(String edn_time) {
        this.edn_time = edn_time;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getTrip_status() {
        return trip_status;
    }

    public void setTrip_status(String trip_status) {
        this.trip_status = trip_status;
    }


}
