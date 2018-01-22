package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 19/1/18.
 */

public class CompleteTripResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private CompleteTrip data;

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

    public CompleteTrip getData() {
        return data;
    }

    public void setData(CompleteTrip data) {
        this.data = data;
    }
}
