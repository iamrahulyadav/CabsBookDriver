package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 9/1/18.
 */

public class DriverComplimetsRatings {
    @SerializedName("id")
    private String id;

    @SerializedName("compliment_name")
    private String compliment_name;

    @SerializedName("count")
    private String count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompliment_name() {
        return compliment_name;
    }

    public void setCompliment_name(String compliment_name) {
        this.compliment_name = compliment_name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


}
