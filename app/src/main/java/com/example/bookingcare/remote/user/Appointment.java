package com.example.bookingcare.remote.user;

import com.example.bookingcare.remote.doctor.DoctorInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Appointment {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("busy")
    @Expose
    private Boolean busy;
    @SerializedName("createAt")
    @Expose
    private String createAt;
    @SerializedName("updateAt")
    @Expose
    private String updateAt;
    @SerializedName("doctor")
    @Expose
    private DoctorInfo doctor;
    @SerializedName("calender")
    @Expose
    private Map calender;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getBusy() {
        return busy;
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public DoctorInfo getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorInfo doctor) {
        this.doctor = doctor;
    }

    public Map getCalender() {
        return calender;
    }

    public void setCalender(Map calender) {
        this.calender = calender;
    }

}