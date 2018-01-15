package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 15/1/18.
 */

public class HelpOneModule {
    @SerializedName("id")
    private String id;

    @SerializedName("driver_help_catagory_id")
    private String driver_help_catagory_id;

    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDriver_help_catagory_id() {
        return driver_help_catagory_id;
    }

    public void setDriver_help_catagory_id(String driver_help_catagory_id) {
        this.driver_help_catagory_id = driver_help_catagory_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
