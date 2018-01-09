package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 9/1/18.
 */

public class FiveStarComents {
    @SerializedName("driver_id")
    private String driver_id;

    @SerializedName("fiver_star_comment")
    private String fiver_star_comment;

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public String getFiver_star_comment() {
        return fiver_star_comment;
    }

    public void setFiver_star_comment(String fiver_star_comment) {
        this.fiver_star_comment = fiver_star_comment;
    }


}
