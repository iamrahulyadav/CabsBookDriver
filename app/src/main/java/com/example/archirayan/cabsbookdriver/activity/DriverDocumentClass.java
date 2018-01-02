package com.example.archirayan.cabsbookdriver.activity;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by archirayan on 21/12/17.
 */

public class DriverDocumentClass
{
    public ImageView getDriver_Imgtype() {
        return driver_Imgtype;
    }

    public void setDriver_Imgtype(ImageView driver_Imgtype) {
        this.driver_Imgtype = driver_Imgtype;
    }

    public TextView getDriver_Title() {
        return driver_Title;
    }

    public void setDriver_Title(TextView driver_Title) {
        this.driver_Title = driver_Title;
    }

    public TextView getDriver_doc_Type() {
        return driver_doc_Type;
    }

    public void setDriver_doc_Type(TextView driver_doc_Type) {
        this.driver_doc_Type = driver_doc_Type;
    }

    public ImageView driver_Imgtype;
    public TextView  driver_Title,driver_doc_Type;
}
