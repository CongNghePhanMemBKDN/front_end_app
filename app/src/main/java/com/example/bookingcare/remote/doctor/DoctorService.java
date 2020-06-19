package com.example.bookingcare.remote.doctor;


import com.example.bookingcare.remote.doctor.DoctorInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface DoctorService {
    @Headers({"accept: */*", "Content-Type: application/json" })
    @POST("/doctor/login")
    Call<DoctorInfo> login(@Body String body);

}
