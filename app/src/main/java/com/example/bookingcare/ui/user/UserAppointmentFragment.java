package com.example.bookingcare.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;
import com.example.bookingcare.remote.user.Appointment;
import com.example.bookingcare.remote.user.UserController;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UserAppointmentFragment extends Fragment {
    ScrollView container;
    TextView msg;
    RecyclerView rcvAppointment;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user_appointment, container, false);
        rcvAppointment = root.findViewById(R.id.rcv_appointment);
        msg = root.findViewById(R.id.msg);
        container = root.findViewById(R.id.container);

        List<Appointment> appointments = new ArrayList<Appointment>();
        if (UserController.getInstance().getInfo().getAppointment() != null)
            appointments.add(UserController.getInstance().getInfo().getAppointment());

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
        return root;
    }
}
