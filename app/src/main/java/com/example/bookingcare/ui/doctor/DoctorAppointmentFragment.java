package com.example.bookingcare.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;
import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.schedules.Calendar;
import com.example.bookingcare.remote.user.Appointment;
import com.example.bookingcare.remote.user.UserController;
import com.example.bookingcare.ui.common.DayWeeks;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorAppointmentFragment extends Fragment {
    ScrollView container;
    TextView msg;
    RecyclerView rcvAppointment;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_doctor_appointment, container, false);
        getSchedule();
        return root;
    }

    private void getSchedule() {
        try {
            showWaitingCircle();
            Call call = DoctorController.getInstance().getService().getSchedule(
                    "bearer " + DoctorController.getInstance().getInfo().getAccessToken());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    hideWaitingCircle();
                    if (response.isSuccessful()) {
                        DoctorController.getInstance().setSchedule((Map<String, List<Calendar>>) response.body());
                        initView();

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity().getBaseContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity().getBaseContext(), "Get appointments failed", Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity().getBaseContext(), "Get appointments failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    hideWaitingCircle();
                    Toast.makeText(getActivity().getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            hideWaitingCircle();
            Toast.makeText(getActivity().getBaseContext(), "Get schedule exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        rcvAppointment = root.findViewById(R.id.rcv_appointment);
        msg = root.findViewById(R.id.msg);
        container = root.findViewById(R.id.container);

        List<Calendar> appointments = new ArrayList<>();

        Map<String, List<Calendar>> schedule = DoctorController.getInstance().getSchedule();
        Iterator iterator = schedule.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            List<Calendar> calendars = (List<Calendar>) entry.getValue();
            for (Calendar calendar : calendars) {
                if (calendar.isBusy() && calendar.getBookingBy() != null){
                    calendar.setDay(DayWeeks.valueOf((String) entry.getKey()));
                    appointments.add(calendar);
                }
            }
        }

        if (appointments.size() > 0) {
            container.setVisibility(View.VISIBLE);
            msg.setVisibility(View.GONE);
            AppointmentAdaptor adaptor = new AppointmentAdaptor(getContext(), appointments);
            rcvAppointment.setLayoutManager(new LinearLayoutManager(getContext()));
            rcvAppointment.setAdapter(adaptor);
        } else{
            container.setVisibility(View.GONE);
            msg.setVisibility(View.VISIBLE);
        }
    }

    public void showWaitingCircle(){
        System.out.println("show");

        root.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        System.out.println("show ok");

    }

    public void hideWaitingCircle(){
        System.out.println("hide");
        root.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        System.out.println("hide ok ");

    }
}
