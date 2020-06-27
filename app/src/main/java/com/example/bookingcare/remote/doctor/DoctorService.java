package com.example.bookingcare.remote.doctor;


import com.example.bookingcare.remote.Common.CommonService;
import com.example.bookingcare.remote.schedules.Calendar;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface DoctorService {
    @Headers({"accept: */*", "Content-Type: application/json" })
    @POST("/doctor/login")
    Call<DoctorInfo> login(@Body String body);

    @Headers({"accept: */*"})
    @GET("/schedule/get-schedule-for-doctor")
    Call<Map<String, List<Calendar>>> getSchedule(@Header("x-access-token") String accessToken);

    @Headers({"accept: */*", "Content-Type: application/json" })
    @POST("/calender")
    Call<Map<String, List<Calendar>>> getCalendars(@Body String body);

    @Headers({"accept: */*", "Content-Type: application/json" })
    @PUT("/schedule/update/{doctorId}/{calenderIds}")
    Call<Object> updateSchedule(@Header("x-access-token") String accessToken, @Body String body);

    @Headers({"accept: */*", "Content-Type: application/json" })
    @POST("/expertise")
    Call<List> getExpertise();

}
