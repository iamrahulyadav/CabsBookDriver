package com.example.archirayan.cabsbookdriver.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 11/1/18.
 */

public class PromotionsList {

    @SerializedName("id")
    private String id;

    @SerializedName("promotions_name")
    private String promotions_name;

    @SerializedName("promotions_discription")
    private String promotions_discription;

    @SerializedName("earning_price")
    private String earning_price;

    @SerializedName("start_date")
    private String start_date;

    @SerializedName("end_date")
    private String end_date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPromotions_name() {
        return promotions_name;
    }

    public void setPromotions_name(String promotions_name) {
        this.promotions_name = promotions_name;
    }

    public String getPromotions_discription() {
        return promotions_discription;
    }

    public void setPromotions_discription(String promotions_discription) {
        this.promotions_discription = promotions_discription;
    }

    public String getEarning_price() {
        return earning_price;
    }

    public void setEarning_price(String earning_price) {
        this.earning_price = earning_price;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }



}
