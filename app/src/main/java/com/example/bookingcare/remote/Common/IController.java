package com.example.bookingcare.remote.Common;

import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.doctor.DoctorInfo;
import com.example.bookingcare.remote.doctor.DoctorService;

import retrofit2.Call;

public interface IController {

    public Call login(String body);

    public CommonInfo getInfo();

    public void setInfo(CommonInfo info);
}
