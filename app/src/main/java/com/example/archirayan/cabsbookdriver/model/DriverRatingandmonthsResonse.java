package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 9/1/18.
 */

public class DriverRatingandmonthsResonse {
    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private DriverRatingandmonths data;

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

    public DriverRatingandmonths getData() {
        return data;
    }

    public void setData(DriverRatingandmonths data) {
        this.data = data;
    }


}
