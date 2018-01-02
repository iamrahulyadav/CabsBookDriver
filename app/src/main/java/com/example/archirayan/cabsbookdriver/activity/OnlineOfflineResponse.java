package com.example.archirayan.cabsbookdriver.activity;

import com.example.archirayan.cabsbookdriver.model.DriverLogin;
import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 6/12/17.
 */

public class OnlineOfflineResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private OnlineStatus data;

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

    public OnlineStatus getData() {
        return data;
    }

    public void setData(OnlineStatus data) {
        this.data = data;
    }
}
