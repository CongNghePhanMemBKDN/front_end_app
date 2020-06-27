package com.example.bookingcare.remote.user;

import com.example.bookingcare.remote.ApiUtils;
import com.example.bookingcare.remote.Common.CommonInfo;
import com.example.bookingcare.remote.Common.IController;
import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.doctor.Expertise;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserController implements IController {
    private static UserController _instance;

    private UserService userService;
    private UserInfo info;

    private UserController() {
        userService = ApiUtils.getUserService();
    }

    public static UserController getInstance(){
        if(_instance == null){
            _instance = new UserController();
        }
        return _instance;
    }

    public UserService getService() {
        return userService;
    }

    @Override
    public Call login(String body) {
        return getService().login(body);
    }

    public UserInfo getInfo() {
        return info;
    }

    public void setInfo(CommonInfo info) {
        this.info = (UserInfo) info;
        System.out.println("");
    }
}
