package com.example.bookingcare.remote;


import com.example.bookingcare.remote.admin.AdminService;
import com.example.bookingcare.remote.doctor.DoctorService;
import com.example.bookingcare.remote.user.UserService;

public class ApiUtils {
    public static String BASE_URL = "http://192.168.137.1:3069";
    private static ApiUtils _instance = new ApiUtils();
    private UserService userService = null;
    private DoctorService doctorService = null;
    private AdminService adminService = null;

    public static final int USER = 0;
    public static final int DOCTOR = 1;
    public static final int ADMIN = 2;
    private int role;


    private ApiUtils() {
    }

    public static ApiUtils getInstance(){
        return _instance;
    }

    public UserService getUserService(){
        if (userService == null) {
            userService = RetrofitClient.getClient(BASE_URL).create(UserService.class);
        }
        return userService;
    }

    public DoctorService getDoctorService(){
        if (doctorService == null) {
            doctorService = RetrofitClient.getClient(BASE_URL).create(DoctorService.class);
        }
        return doctorService;
    }

    public AdminService getAdminService(){
        if (adminService == null) {
            adminService = RetrofitClient.getClient(BASE_URL).create(AdminService.class);
        }
        return adminService;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isUser(){
        return this.role == USER;
    }
    public boolean isDoctor(){
        return this.role == DOCTOR;
    }
    public boolean isAdmin(){
        return this.role == ADMIN;
    }
}