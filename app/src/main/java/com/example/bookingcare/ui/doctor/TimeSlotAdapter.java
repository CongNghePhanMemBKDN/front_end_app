package com.example.bookingcare.ui.doctor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.Common;
import com.example.bookingcare.R;
import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.schedules.Calendar;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {
    String dayOfWeek;
    public List<Calendar> mListCalendar;
    Context context;

    public TimeSlotAdapter(Context context, List<Calendar> listCalendar, String dayOfWeek) {
        this.context = context;
        this.dayOfWeek = dayOfWeek;
        this.mListCalendar = listCalendar;
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
        int index = DoctorController.getInstance().getSchedule().get(dayOfWeek).indexOf(mListCalendar.get(position));
        boolean isBusy = false;
        if (index >= 0) {
            holder.timeSlotButton.setChecked(true);
            isBusy = DoctorController.getInstance().getSchedule().get(dayOfWeek).get(index).isBusy();
        } else {
            holder.timeSlotButton.setChecked(false);
        }

        if (!isBusy) {
            holder.timeSlotButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    List<Calendar> calendars = DoctorController.getInstance().getSchedule().get(dayOfWeek);
                    if (isChecked) {
                        calendars.add(mListCalendar.get(position));
                    } else {
                        if (calendars.contains(mListCalendar.get(position)))
                            calendars.remove(mListCalendar.get(position));
                    }
                }
            });
        } else{
            holder.timeSlotButton.setTextColor(Color.GRAY);
            holder.timeSlotButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    holder.timeSlotButton.setChecked(true);
                    Toast.makeText(context, "This time slot has been booked!", Toast.LENGTH_SHORT).show();
                }
            });
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
