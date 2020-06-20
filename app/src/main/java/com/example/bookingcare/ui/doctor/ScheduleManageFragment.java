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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;
import com.example.bookingcare.remote.ApiUtils;
import com.example.bookingcare.remote.schedules.TimeSlot;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleManageFragment extends Fragment {
    View root;
    private List<TimeSlot> mListTimeSlot;
    private List<String> mDayWeeks;
    private RecyclerView mDayWeekRcv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_schedule_manage, container, false);
        mDayWeekRcv = root.findViewById(R.id.day_week_rcv);
        mDayWeeks= new ArrayList<>();
        getTimeSlot();
        return root;
    }

    private void initRecycleViews() {
        mDayWeeks.add(getString(R.string.monday));
        mDayWeeks.add(getString(R.string.tuesday));
        mDayWeeks.add(getString(R.string.wednesday));
        mDayWeeks.add(getString(R.string.thursday));
        mDayWeeks.add(getString(R.string.friday));
        mDayWeeks.add(getString(R.string.saturday));
        mDayWeeks.add(getString(R.string.sunday));
        DayWeekAdaptor dayWeekAdaptor = new DayWeekAdaptor(
                getActivity().getApplicationContext(),
                mDayWeeks,
                mListTimeSlot);

        mDayWeekRcv.setLayoutManager(new LinearLayoutManager(getContext()));
        mDayWeekRcv.setAdapter(dayWeekAdaptor);

        Button buttonOk = root.findViewById(R.id.btn_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Change schedule")
                        .setMessage("Are you sure you want to change your schedule?")
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                changeSchedule();
                            }

                        })
                        .setPositiveButton("No", null)
                        .show();
            }
        });
    }

    private void changeSchedule() {

    }

    private void getTimeSlot() {
        Call call = ApiUtils.getInstance().getDoctorService().getTimeSlot("");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    mListTimeSlot = (List<TimeSlot>) response.body();
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
                Toast.makeText(getActivity().getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
