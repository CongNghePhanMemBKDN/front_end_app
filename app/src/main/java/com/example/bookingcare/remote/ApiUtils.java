package com.example.bookingcare.remote;


public class ApiUtils {
    public static final String BASE_URL = "http://10.0.2.2:3069";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }
}