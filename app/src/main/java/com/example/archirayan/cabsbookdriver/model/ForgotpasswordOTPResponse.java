package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 18/12/17.
 */

public class ForgotpasswordOTPResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("verified_code")
    private String verified_code;

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

    public String getVerified_code() {
        return verified_code;
    }

    public void setVerified_code(String verified_code) {
        this.verified_code = verified_code;
    }


}
