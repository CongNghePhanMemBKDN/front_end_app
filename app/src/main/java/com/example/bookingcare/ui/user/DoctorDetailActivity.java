package com.example.bookingcare.ui.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingcare.Common;
import com.example.bookingcare.R;
import com.example.bookingcare.framework.Event.EventCallback;
import com.example.bookingcare.framework.Event.EventDataType;
import com.example.bookingcare.framework.Event.EventManager;
import com.example.bookingcare.framework.Event.EventType;
import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.doctor.Expertise;
import com.example.bookingcare.remote.schedules.Calendar;
import com.example.bookingcare.remote.user.DoctorDetail;
import com.example.bookingcare.remote.user.UserController;
import com.example.bookingcare.remote.user.UserInfo;
import com.example.bookingcare.ui.LoginActivity;
import com.example.bookingcare.ui.common.DayWeeks;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDetailActivity extends AppCompatActivity {
    String doctorId;
    DoctorDetail detail;
    TextView name;
    Spinner mDayWeekSpinner;
    RecyclerView mRcvTimeSlot;
    List<DayWeeks> mListDays;
    TextView mScheduleMessage;
    String scheduleId;

    Map<String, List<Calendar>> schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        doctorId = getIntent().getStringExtra(Common.DOCTOR_ID_NAME);
        EventManager.removeEventListener(EventType.SELECT_TIME_SLOT);
        EventManager.addEventListener(EventType.SELECT_TIME_SLOT, new EventCallback() {
            @Override
            public void execute(Map e) {
                scheduleId = (String) e.get(EventDataType.SCHEDULE_ID);
                    new AlertDialog.Builder(DoctorDetailActivity.this)
                            .setTitle("Book schedule")
                            .setMessage("Are you sure you want to book this schedule?")
                            .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    bookSchedule();
                                }

                            })
                            .setPositiveButton("No", null)
                            .show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        name = findViewById(R.id.doctor_detail_name);
        getDetailInfo();

        mDayWeekSpinner = findViewById(R.id.spinner_day_week);
        mRcvTimeSlot = findViewById(R.id.rcv_time_slot);
        mScheduleMessage = findViewById(R.id.doctor_detail_schedule_message);
        getSchedule();


    }

    private void bookSchedule() {
        System.out.println("bookSchedule!");
        try {
            showWaitingCircle();

            JSONObject body = new JSONObject();
            body.put("scheduleId", scheduleId);
            Call call = UserController.getInstance().getService().bookSchedule("bearer " + Common.CONTROLLER.getInfo().getAccessToken(), body.toString());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    hideWaitingCircle();
                    if (response.isSuccessful()) {
                        Toast.makeText(DoctorDetailActivity.this, "Book schedule completed!", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(DoctorDetailActivity.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(DoctorDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            hideWaitingCircle();
            Toast.makeText(DoctorDetailActivity.this, "Book schedule exception!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void getDetailInfo(){
        showWaitingCircle();
        Call call = UserController.getInstance().getService().getDoctorDetail(this.doctorId);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                hideWaitingCircle();
                if (response.isSuccessful()){
                    detail = (DoctorDetail) response.body();
                    initDetail();
                } else{
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(DoctorDetailActivity.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    name.setText("Can't get doctor's information!\nCheck your connection!");

                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                hideWaitingCircle();
                Toast.makeText(DoctorDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                name.setText("Can't get doctor's information!\nCheck your connection!");
            }
        });
    }

    private void initDetail() {
        setTitle(detail.getFullName());

        name.setText(detail.getFullName());

        TextView expertise = findViewById(R.id.doctor_detail_expertise);
        expertise.setText(detail.getExpertise().getName());

        TextView address = findViewById(R.id.doctor_detail_clinic_address);
        address.setText(detail.getAddressDetail());

        TextView description = findViewById(R.id.doctor_detail_description);
        description.setText(detail.getDescription());

        TextView email = findViewById(R.id.doctor_detail_email);
        email.setText(("Email: " + detail.getEmail()));
    }

    public void getSchedule(){
        try {
            JSONObject body = new JSONObject();
            body.put("doctorId", this.doctorId);
            showWaitingCircle();
            Call call = UserController.getInstance().getService().getDoctorSchedule(body.toString());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    hideWaitingCircle();
                    if (response.isSuccessful()) {
                        schedule = ((Map<String, List<Calendar>>) response.body());
                        initScheduleView();

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(DoctorDetailActivity.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(DoctorDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            hideWaitingCircle();
            Toast.makeText(DoctorDetailActivity.this, "Get schedule exception", Toast.LENGTH_SHORT).show();
        }
    }

    private void initScheduleView() {
        mListDays = new ArrayList<>();
        ArrayList<String> items = new ArrayList();

        java.util.Calendar now = java.util.Calendar.getInstance();
        int startDay = now.get(java.util.Calendar.DAY_OF_WEEK) - 1;
        int currentHour = now.get(java.util.Calendar.HOUR_OF_DAY);
        int currentMinus = now.get(java.util.Calendar.MINUTE);
        List<Calendar> currentCalendars = schedule.get(DayWeeks.from(startDay).toString());

        boolean needOverDay = true;
        for (int i = 0; i < currentCalendars.size(); i++) {
            String timeSlot = currentCalendars.get(i).getTimeSlot();
            String[] startTime = timeSlot.replace(" ", "").split("-")[0].split(":");
            int startHour =  Integer.parseInt(startTime[0]);
            int startMinus = Integer.parseInt(startTime[1]);
            if (currentHour < startHour + Common.MIN_HOURS){
                needOverDay = false;
            } else if (currentHour == startHour + Common.MIN_HOURS && currentMinus < startMinus){
                needOverDay = false;
            }
        }

        if (needOverDay) startDay++;

        for (int i = 0; i < DayOfWeek.values().length; i++) {
            DayWeeks day = DayWeeks.values()[((startDay + i) % DayWeeks.size())];
            now.add(java.util.Calendar.DATE, 1);
            if (schedule.containsKey(day.toString()) && schedule.get(day.toString()).size() > 0) {
                items.add((day.getShortString() + " - " + now.get(java.util.Calendar.DAY_OF_MONTH) + "/" + now.get(java.util.Calendar.MONTH)));
                mListDays.add(day);
            }
        }

        if (items.size() <= 0){
            mScheduleMessage.setText("This doctor has no free time right now, Please choose another!");
            mScheduleMessage.setVisibility(View.VISIBLE);
            mRcvTimeSlot.setVisibility(View.GONE);
        } else {
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            mDayWeekSpinner.setAdapter(adapter);
            mScheduleMessage.setVisibility(View.GONE);
            mRcvTimeSlot.setVisibility(View.VISIBLE);

            mDayWeekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        TimeSlotAdapter adapter = new TimeSlotAdapter(getApplicationContext(), schedule.get(mListDays.get(position).toString()));
                        mRcvTimeSlot.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
                        mRcvTimeSlot.setAdapter(adapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    mDayWeekSpinner.setSelection(0);
                }
            });
            mDayWeekSpinner.setSelection(0);
        }
    }


    public void showWaitingCircle(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
    }

    public void hideWaitingCircle(){
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }
}
