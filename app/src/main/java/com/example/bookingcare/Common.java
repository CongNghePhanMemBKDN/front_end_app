package com.example.bookingcare;

import android.content.Intent;

import com.example.bookingcare.remote.Common.CommonService;
import com.example.bookingcare.remote.Common.IController;
import com.example.bookingcare.remote.user.UserController;

import java.util.Calendar;

public class Common {
    public static final String ROLE_NAME = "Role";
    public static final String EMAIL = "EMAIL";
    public static final int ROLE_USER = 0;
    public static final int ROLE_DOCTOR = 1;
    public static int ROLE = 0;

    public static IController CONTROLLER = UserController.getInstance();

    public static final String DOCTOR_ID_NAME = "doctorId";


    /**
     * minimum hours to book
     * eg: if current time = 12:00, users can only book calendar which is before 10:00
     * */
    public static final int MIN_HOURS = 2;

    public static boolean isUser(){ return ROLE == ROLE_USER; }
    public static boolean isDoctor(){
        return ROLE == ROLE_DOCTOR;
    }

    public static String dateToString(int year, int month, int day){
        return year + "-" + (month+1) +"-" + day;
    }

    public static java.util.Calendar stringToDay(String date){
        try {
            String[] fields = date.split("-");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(fields[0]));
            calendar.set(Calendar.MONTH, Integer.parseInt(fields[1]) - 1);
            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(fields[2]));
            return calendar;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
