package com.example.bookingcare.ui.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.Common;
import com.example.bookingcare.R;
import com.example.bookingcare.remote.doctor.DoctorInfo;

import java.util.List;

public class DoctorAdaptor extends RecyclerView.Adapter<DoctorAdaptor.DoctorViewHolder> {
    List<DoctorInfo> mListDoctor;
    Context context;

    public DoctorAdaptor(Context context, List<DoctorInfo> mListDoctor) {
        this.context = context;
        this.mListDoctor = mListDoctor;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_item, parent, false);
        return new DoctorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, final int position) {
        holder.doctorName.setText(mListDoctor.get(position).getFullName());
        holder.doctorClinicAddress.setText(mListDoctor.get(position).getAddressDetail());

        holder.doctorName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDoctor(mListDoctor.get(position).getId());
            }
        });
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDoctor(mListDoctor.get(position).getId());
            }
        });

    }

    public void selectDoctor(String doctorId) {
        Intent intent = new Intent(context, DoctorDetailActivity.class);
        intent.putExtra(Common.DOCTOR_ID_NAME, doctorId);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mListDoctor.size();
    }



    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView doctorName;
        TextView doctorDepartment;
        ListView doctorPositions;
        TextView doctorClinicAddress;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.doctor_avatar);
            doctorName = itemView.findViewById(R.id.doctor_name);
            doctorDepartment = itemView.findViewById(R.id.doctor_department);
            doctorPositions = itemView.findViewById(R.id.doctor_positions);
            doctorClinicAddress = itemView.findViewById(R.id.doctor_clinic_address);
        }
    }
}
