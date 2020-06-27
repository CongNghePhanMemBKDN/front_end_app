package com.example.bookingcare.ui.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.Common;
import com.example.bookingcare.R;
import com.example.bookingcare.remote.doctor.DoctorInfo;
import com.example.bookingcare.remote.user.Appointment;
import com.example.bookingcare.ui.common.DayWeeks;
import com.google.gson.internal.LinkedTreeMap;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class AppointmentAdaptor extends RecyclerView.Adapter<AppointmentAdaptor.AppointmentViewHolder> {
    List<Appointment> mAppointment;
    Context context;

    public AppointmentAdaptor(Context context, List<Appointment> mAppointment) {
        this.context = context;
        this.mAppointment = mAppointment;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointment_item, parent, false);
        return new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, final int position) {
        holder.agent.setText("Doctor:");
        Appointment appointment = mAppointment.get(position);

        String day = (String) appointment.getCalender().get("day");
        DayWeeks dayWeeks = DayWeeks.valueOf(day);
        Calendar calendar = Calendar.getInstance();
        while(calendar.get(Calendar.DAY_OF_WEEK) - 1 != dayWeeks.getId()){
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        holder.day.setText((day +" - " + calendar.get(Calendar.DAY_OF_MONTH) +"/"+calendar.get(Calendar.MONTH)));
        holder.timeSlot.setText((String)(((Map)appointment.getCalender().get("timeslot")).get("name")));
        holder.name.setText(appointment.getDoctor().getFullName());
        holder.email.setText(appointment.getDoctor().getEmail());
    }


    @Override
    public int getItemCount() {
        return mAppointment.size();
    }



    public class AppointmentViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView day;
        TextView timeSlot;
        TextView name;
        TextView email;
        TextView agent;

        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            timeSlot = itemView.findViewById(R.id.time_slot);
            agent = itemView.findViewById(R.id.agent);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            day = itemView.findViewById(R.id.day);
        }
    }
}
