package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by archirayan on 3/1/18.
 */

public class GetModelNameResponse {
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

    public ArrayList<ModelName> getData() {
        return data;
    }

    public void setData(ArrayList<ModelName> data) {
        this.data = data;
    }

    @SerializedName("data")
    private ArrayList<ModelName> data;
}
