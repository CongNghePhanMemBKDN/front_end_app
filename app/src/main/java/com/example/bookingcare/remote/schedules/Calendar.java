package com.example.bookingcare.remote.schedules;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Calendar {

    @SerializedName("calenderId")
    @Expose
    private String calenderId;
    @SerializedName("timeSlot")
    @Expose
    private String timeSlot;
    @SerializedName("scheduleId")
    @Expose
    private String scheduleId;
    @SerializedName("busy")
    @Expose
    private Boolean busy;

    public String getCalenderId() {
        return calenderId;
    }

    public void setCalenderId(String calenderId) {
        this.calenderId = calenderId;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Boolean isBusy() {
        return busy;
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    @Override
    public boolean equals(@Nullable Object object) {
        try{
            return this.calenderId.equals(((Calendar)(object)).calenderId);
        } catch (Exception e){

        }
        return false;
    }
}