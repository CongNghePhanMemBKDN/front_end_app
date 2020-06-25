package com.example.bookingcare.remote.doctor; ;

import com.example.bookingcare.remote.Common.CommonInfo;
import com.example.bookingcare.remote.schedules.Calendar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class DoctorInfo extends CommonInfo {

    @SerializedName("addressDetail")
    @Expose
    private String addressDetail;
    @SerializedName("description")
    @Expose
    private String description;

    private Map<String, List<Calendar>> schedule;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DoctorInfo withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public DoctorInfo withAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
        return this;
    }


}

