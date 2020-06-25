package com.example.bookingcare.ui.doctor;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;
import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.schedules.Calendar;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleManageFragment extends Fragment {
    View root;
    private Map<String, List<Calendar>> mListCalendar;
    private RecyclerView mDayWeekRcv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_schedule_manage, container, false);
        mDayWeekRcv = root.findViewById(R.id.day_week_rcv);
        getSchedule();
        return root;
    }

    private void initRecycleViews() {

        DayWeekAdaptor dayWeekAdaptor = new DayWeekAdaptor(
                getActivity().getApplicationContext(), mListCalendar);

        mDayWeekRcv.setLayoutManager(new LinearLayoutManager(getContext()));
        mDayWeekRcv.setAdapter(dayWeekAdaptor);

        Button buttonOk = root.findViewById(R.id.btn_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Change schedule")
                        .setMessage("Are you sure you want to change your schedule?")
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateSchedule();
                            }

                        })
                        .setPositiveButton("No", null)
                        .show();
            }
        });
    }

    private void updateSchedule() {
        try {
            showWaitingCircle();
            Map<String, List<Calendar>> schedule = (DoctorController.getInstance().getSchedule());
            JSONObject body = new JSONObject();
            JSONArray calendarIds = new JSONArray();

            Iterator iterator = schedule.values().iterator();
            while (iterator.hasNext()) {
                List<Calendar> calendars = ((List<Calendar>) iterator.next());
                for (Calendar calendar : calendars) {
                    calendarIds.put(calendar.getCalenderId());
                }
            }
            body.put("calenderIds", calendarIds);
            Call call = DoctorController.getInstance().getService().updateSchedule("bearer " + DoctorController.getInstance().getInfo().getAccessToken(), body.toString());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    hideWaitingCircle();
                    if (response.isSuccessful()) {
                        try {
                            Gson gson = new Gson();
                            JSONObject body = new JSONObject(gson.toJson(response.body()));
                            DoctorController.getInstance().setSchedule(gson.fromJson(body.getString("data"), Map.class));
                            JSONArray errors = body.getJSONArray("errors");
                            if (errors.length() <= 0) {
                                Toast.makeText(getActivity().getBaseContext(), "Update schedule completed", Toast.LENGTH_SHORT).show();
                                reload();
                            } else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                String message = "Some time slot cannot be changed: ";
                                for (int i = 0; i < errors.length(); i++) {
                                    message += "\n" + errors.getString(i);
                                }
                                builder.setTitle("Oop! Something wrongs!")
                                        .setMessage(message)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                reload();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        } catch (Exception e) {
                            getSchedule();
                            Toast.makeText(getActivity().getBaseContext(), "Update schedule get response exception", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        getSchedule();
                        Toast.makeText(getActivity().getBaseContext(), "Update schedule failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    hideWaitingCircle();
                    getSchedule();
                    Toast.makeText(getActivity().getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            hideWaitingCircle();
            getSchedule();
            Toast.makeText(getActivity().getBaseContext(), "Update schedule exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void reload() {
       getSchedule();
    }

    private void getTimeSlot() {
        showWaitingCircle();
        Call call = DoctorController.getInstance().getService().getCalendars("");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                hideWaitingCircle();
                if (response.isSuccessful()) {
                    mListCalendar = (Map<String, List<Calendar>>) response.body();
                    initRecycleViews();

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity().getBaseContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                hideWaitingCircle();
                Toast.makeText(getActivity().getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                        getTimeSlot();

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(getActivity().getBaseContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
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
