package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 2/11/17.
 */

public class Driverdocuments {

    @SerializedName("image_driver_license")
    private String image_driver_license;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("mobile_no")
    private String mobile_no;
}
