package com.example.archirayan.cabsbookdriver.model;

import com.example.archirayan.cabsbookdriver.model.Driver;
import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 1/11/17.
 */

public class DriverRegisterMainResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("data")
    private Driver data;


    public Driver getData() { return data; }

    public void setData(Driver data) { this.data = data; }
    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
