package com.example.bookingcare;

import com.example.bookingcare.remote.Common.CommonService;
import com.example.bookingcare.remote.Common.IController;
import com.example.bookingcare.remote.user.UserController;

public class Common {
    public static final String ROLE_NAME = "Role";
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

}
