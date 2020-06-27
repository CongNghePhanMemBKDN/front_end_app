package com.example.bookingcare.remote.doctor;

import com.example.bookingcare.remote.ApiUtils;
import com.example.bookingcare.remote.Common.CommonInfo;
import com.example.bookingcare.remote.Common.IController;
import com.example.bookingcare.remote.schedules.Calendar;

import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class DoctorController implements IController {
    static DoctorController _instance;
    private DoctorService doctorService;
    private DoctorInfo info;
    private Map<String, List<Calendar>> schedule;

    private DoctorController() {
        doctorService = ApiUtils.getDoctorService();
    }

    public static DoctorController getInstance(){
        if(_instance == null){
            _instance = new DoctorController();
        }
        return _instance;
    }

    public DoctorService getService() {
        return doctorService;
    }

    @Override
    public Call login(String body) {
        return getService().login(body);
    }

    public DoctorInfo getInfo() {
        return info;
    }

    public void setInfo(CommonInfo info) {
        this.info = (DoctorInfo) info;
    }

    public Map<String, List<Calendar>> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, List<Calendar>> schedule) {
        this.schedule = schedule;
    }

}
