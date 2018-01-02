package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 3/11/17.
 */

public class DriverLoginResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String msg;

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

    public DriverLogin getData() {
        return data;
    }

    public void setData(DriverLogin data) {
        this.data = data;
    }

    @SerializedName("data")
    private DriverLogin data;
}
