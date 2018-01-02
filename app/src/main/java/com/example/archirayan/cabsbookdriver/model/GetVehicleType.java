package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 2/1/18.
 */

public class GetVehicleType {
    @SerializedName("id")
    private String id;

    @SerializedName("type")
    private String type;

    @SerializedName("price_per_km")
    private String price_per_km;

    @SerializedName("price_per_min")
    private String price_per_min;

    @SerializedName("base_fire")
    private String base_fire;

    @SerializedName("min_fare")
    private String min_fare;

    @SerializedName("commision")
    private String commision;

    @SerializedName("person_size")
    private String person_size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice_per_km() {
        return price_per_km;
    }

    public void setPrice_per_km(String price_per_km) {
        this.price_per_km = price_per_km;
    }

    public String getPrice_per_min() {
        return price_per_min;
    }

    public void setPrice_per_min(String price_per_min) {
        this.price_per_min = price_per_min;
    }

    public String getBase_fire() {
        return base_fire;
    }

    public void setBase_fire(String base_fire) {
        this.base_fire = base_fire;
    }

    public String getMin_fare() {
        return min_fare;
    }

    public void setMin_fare(String min_fare) {
        this.min_fare = min_fare;
    }

    public String getCommision() {
        return commision;
    }

    public void setCommision(String commision) {
        this.commision = commision;
    }

    public String getPerson_size() {
        return person_size;
    }

    public void setPerson_size(String person_size) {
        this.person_size = person_size;
    }


}
