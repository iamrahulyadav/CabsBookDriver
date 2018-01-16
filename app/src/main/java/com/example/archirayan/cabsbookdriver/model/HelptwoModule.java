package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 15/1/18.
 */

public class HelptwoModule {
    @SerializedName("id")
    private String id;

    @SerializedName("driver_help_sub_catagorry_id")
    private String driver_help_sub_catagorry_id;

    @SerializedName("name")
    private String name;

    @SerializedName("discription")
    private String discription;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriver_help_sub_catagorry_id() {
        return driver_help_sub_catagorry_id;
    }

    public void setDriver_help_sub_catagorry_id(String driver_help_sub_catagorry_id) {
        this.driver_help_sub_catagorry_id = driver_help_sub_catagorry_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }


}
