package com.example.bookingcare.remote.user;

import com.example.bookingcare.remote.Common.CommonInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

;

public class UserInfo extends CommonInfo {
    private String address;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthday")
    @Expose
    private String birthday;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

}

