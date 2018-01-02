package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 5/12/17.
 */

public class TotalOnlinetimeandtrip {
    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private Totaltimeandtrip data;

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

    public Totaltimeandtrip getData() {
        return data;
    }

    public void setData(Totaltimeandtrip data) {
        this.data = data;
    }


}
