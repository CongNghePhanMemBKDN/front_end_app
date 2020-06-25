package com.example.bookingcare.remote.user;

import com.example.bookingcare.remote.ApiUtils;
import com.example.bookingcare.remote.Common.CommonInfo;
import com.example.bookingcare.remote.Common.IController;

import retrofit2.Call;

public class UserController implements IController {
    private static UserController _instance;

    private UserService userService;
    private UserInfo info;
    private Object schedule;

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
        System.out.println("user service");
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
    }

    public Object getSchedule() {
        return schedule;
    }

    public void setSchedule(Object schedule) {
        this.schedule = schedule;
    }
}
