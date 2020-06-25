package com.example.bookingcare.remote;

import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.doctor.DoctorService;
import com.example.bookingcare.remote.user.UserService;

public class ApiUtils {
    public static String BASE_URL = "http://demo2-booking-care.herokuapp.com/";


    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static DoctorService getDoctorService(){
        return RetrofitClient.getClient(BASE_URL).create(DoctorService.class);
    }
}