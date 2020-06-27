package com.example.bookingcare.ui.doctor;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;
import com.example.bookingcare.remote.user.Appointment;
import com.example.bookingcare.ui.common.DayWeeks;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class AppointmentAdaptor extends RecyclerView.Adapter<AppointmentAdaptor.AppointmentViewHolder> {
    List<com.example.bookingcare.remote.schedules.Calendar> mAppointments;
    Context context;

    public AppointmentAdaptor(Context context, List<com.example.bookingcare.remote.schedules.Calendar> mAppointments) {
        this.context = context;
        this.mAppointments = mAppointments;
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
        holder.agent.setText("Booked by:");
        com.example.bookingcare.remote.schedules.Calendar appointment = mAppointments.get(position);

        DayWeeks dayWeeks = appointment.getDay();
        Calendar calendar = Calendar.getInstance();
        while(calendar.get(Calendar.DAY_OF_WEEK) - 1 != dayWeeks.getId()){
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        holder.day.setText((dayWeeks.toString() +" - " + calendar.get(Calendar.DAY_OF_MONTH) +"/"+calendar.get(Calendar.MONTH)));
        holder.timeSlot.setText(appointment.getTimeSlot());
        holder.name.setText(appointment.getBookingBy().getFullName());
        holder.email.setText(appointment.getBookingBy().getEmail());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.avatar.setBackground(context.getDrawable(R.drawable.avatar_patient));
        }
    }


    @Override
    public int getItemCount() {
        return mAppointments.size();
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
