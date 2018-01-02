package com.example.archirayan.cabsbookdriver.activity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by archirayan on 5/12/17.
 */

public class GetInvitecode {
    @SerializedName("id")
    private String id;

    @SerializedName("invite_code")
    private String invite_code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }


}
