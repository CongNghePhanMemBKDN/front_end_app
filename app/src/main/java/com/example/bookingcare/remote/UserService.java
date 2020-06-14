package com.example.bookingcare.remote;


import com.example.bookingcare.remote.login.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface UserService {
    @Headers("accept: */*")
    @POST("/doctor/login")
    Call<UserInfo> login(@Query("email") String email, @Query("password") String password);

    @GET()
    Call<String> getHelloWord(@Url String url);
}
