package com.example.bookingcare.ui.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;
import com.example.bookingcare.framework.Event.EventCallback;
import com.example.bookingcare.framework.Event.EventDataType;
import com.example.bookingcare.framework.Event.EventManager;
import com.example.bookingcare.framework.Event.EventType;
import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.schedules.Calendar;
import com.example.bookingcare.remote.user.UserController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {
    public List<Calendar> mListCalendar;
//    public String doctorId;
    Context context;

    public TimeSlotAdapter(Context context, List<Calendar> listCalendar) {
        this.context = context;
        this.mListCalendar = listCalendar;
//        doctorId = doctorId;
    }

    @Override
    public TimeSlotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_slot_item, parent, false);
        return new TimeSlotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TimeSlotViewHolder holder, final int position) {
        holder.timeSlotButton.setTextOn(mListCalendar.get(position).getTimeSlot());
        holder.timeSlotButton.setTextOff(mListCalendar.get(position).getTimeSlot());
        holder.timeSlotButton.setText(mListCalendar.get(position).getTimeSlot());
        holder.timeSlotButton.setChecked(false);
        holder.timeSlotButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                holder.timeSlotButton.setChecked(false);
                selectTimeSlot(mListCalendar.get(position).getScheduleId());
            }
        });
    }

    private void selectTimeSlot(String scheduleId) {
        if (UserController.getInstance().getInfo().getAppointment() != null){
            Toast.makeText(context, "You already have a schedule, Please finish it first!", Toast.LENGTH_LONG).show();
        } else{
            HashMap data = new HashMap();
            data.put(EventDataType.SCHEDULE_ID, scheduleId);
            EventManager.disPatchEvent(EventType.SELECT_TIME_SLOT, data);
        }
    }

    @Override
    public int getItemCount() {
        return mListCalendar.size();
    }

    public class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        ToggleButton timeSlotButton;
        public TimeSlotViewHolder(View itemView) {
            super(itemView);
            timeSlotButton = itemView.findViewById(R.id.time_slot_button);
        }
    }


}
