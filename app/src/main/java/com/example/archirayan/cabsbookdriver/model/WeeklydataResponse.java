package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by archirayan on 4/12/17.
 */

public class WeeklydataResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private ArrayList<Weeklydata> data;

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

    public ArrayList<Weeklydata> getData() {
        return data;
    }

    public void setData(ArrayList<Weeklydata> data) {
        this.data = data;
    }


}
