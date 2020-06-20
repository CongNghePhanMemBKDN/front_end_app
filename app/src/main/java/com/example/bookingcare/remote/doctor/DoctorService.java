package com.example.bookingcare.remote.doctor;


import com.example.bookingcare.remote.schedules.TimeSlot;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DoctorService {
    @Headers({"accept: */*", "Content-Type: application/json" })
    @POST("/doctor/login")
    Call<DoctorInfo> login(@Body String body);

    @POST("/timeslot/")
    @Headers({"accept: */*", "Content-Type: application/json" })
    Call<List<TimeSlot>> getTimeSlot(@Body String body);

}
