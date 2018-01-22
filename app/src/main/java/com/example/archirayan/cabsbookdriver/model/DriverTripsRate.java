package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 19/1/18.
 */

public class DriverTripsRate {
    @SerializedName("cancel")
    private String cancel;

    @SerializedName("accept")
    private String accept;

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

}
