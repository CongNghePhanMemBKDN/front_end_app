package com.example.bookingcare.remote.user;

import com.example.bookingcare.remote.Common.CommonService;
import com.example.bookingcare.remote.doctor.DoctorInfo;
import com.example.bookingcare.remote.schedules.Calendar;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @Headers({"accept: */*", "Content-Type: application/json" })
    @POST("/user/login")
    Call<UserInfo> login(@Body String body);

    @Headers({"accept: */*", "Content-Type: application/json" })
    @POST("/user/create")
    Call<UserInfo> register(@Body String body);

    @Headers({"accept: */*"})
    @GET("/user/get-user-by-id")
    Call<UserInfo> getDetailInfo(@Header("x-access-token") String accessToken);

    @POST("/doctor/")
    @Headers({"accept: */*", "Content-Type: application/json" })
    Call<List<DoctorInfo>> getDoctors(@Body String body);

    @GET("/doctor/public/{id}")
    @Headers({"accept: */*"})
    Call<DoctorDetail> getDoctorDetail(@Path("id") String doctorId);

    @Headers({"accept: */*", "Content-Type: application/json" })
    @POST("/schedule/show-schedule")
    Call<Map<String, List<Calendar>>> getDoctorSchedule(@Body String body);

    @Headers({"accept: */*", "Content-Type: application/json" })
    @POST("/user/booking-schedule")
    Call<Object> bookSchedule(@Header("x-access-token") String accessToken, @Body String body);

    @Headers({"accept: */*", "Content-Type: application/json" })
    @PUT("/user/{id}/update")
    Call<UserInfo> updateInfo(@Header("x-access-token") String accessToken, @Body String body);



}
