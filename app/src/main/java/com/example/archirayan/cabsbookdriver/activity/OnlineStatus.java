package com.example.archirayan.cabsbookdriver.activity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 6/12/17.
 */

public class OnlineStatus {
    @SerializedName("id")
    private String id;

    @SerializedName("active_atatus")
    private String active_atatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActive_atatus() {
        return active_atatus;
    }

    public void setActive_atatus(String active_atatus) {
        this.active_atatus = active_atatus;
    }



}
