package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by archirayan on 9/1/18.
 */

public class DriverComplimetsRatingsResonse {
    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private ArrayList<DriverComplimetsRatings> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<DriverComplimetsRatings> getData() {
        return data;
    }

    public void setData(ArrayList<DriverComplimetsRatings> data) {
        this.data = data;
    }


}
