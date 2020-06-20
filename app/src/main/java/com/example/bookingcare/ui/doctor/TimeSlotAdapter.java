package com.example.bookingcare.ui.doctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;
import com.example.bookingcare.remote.schedules.TimeSlot;

import java.util.List;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.TimeSlotViewHolder> {
    public List<TimeSlot> mListTimeSlot;

    public TimeSlotAdapter(List<TimeSlot> listTimeSlot) {
        this.mListTimeSlot = listTimeSlot;
    }

    @Override
    public TimeSlotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.time_slot_item, parent, false);
        return new TimeSlotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TimeSlotViewHolder holder, int position) {
        holder.timeSlotButton.setTextOn(mListTimeSlot.get(position).getName());
        holder.timeSlotButton.setTextOff(mListTimeSlot.get(position).getName());
        holder.timeSlotButton.setText(mListTimeSlot.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mListTimeSlot.size();
    }

    public class TimeSlotViewHolder extends RecyclerView.ViewHolder {
        ToggleButton timeSlotButton;
        public TimeSlotViewHolder(View itemView) {
            super(itemView);
            timeSlotButton = itemView.findViewById(R.id.time_slot_button);
        }
    }


}
