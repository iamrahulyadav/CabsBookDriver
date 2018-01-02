package com.example.archirayan.cabsbookdriver.activity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 5/12/17.
 */

public class GetInvitecodeResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private GetInvitecode data;


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

    public GetInvitecode getData() {
        return data;
    }

    public void setData(GetInvitecode data) {
        this.data = data;
    }


}
