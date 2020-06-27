package com.example.bookingcare.remote.user;

import com.example.bookingcare.remote.Common.CommonInfo;
import com.example.bookingcare.remote.doctor.DoctorInfo;
import com.example.bookingcare.remote.schedules.Calendar;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

;import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UserInfo extends CommonInfo {
    private String address;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("schedule")
    @Expose
    private Appointment schedule;


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

    public java.util.Calendar getBirthday() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar birthday = java.util.Calendar.getInstance();
        try {
            birthday.setTime(format.parse(this.birthday.substring(0, this.birthday.length()>=10?10: this.birthday.length())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Appointment getAppointment() {
        return schedule;
    }

    public void setAppointment(Appointment schedule) {
        this.schedule = schedule;
    }
}

