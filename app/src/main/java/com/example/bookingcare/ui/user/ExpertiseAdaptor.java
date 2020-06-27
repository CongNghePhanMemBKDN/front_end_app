package com.example.bookingcare.ui.user;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.Common;
import com.example.bookingcare.R;
import com.example.bookingcare.remote.doctor.Expertise;

import java.util.List;

public class ExpertiseAdaptor extends RecyclerView.Adapter<ExpertiseAdaptor.DoctorViewHolder> {
    List<Expertise> mListExpertise;
    Context context;

    public ExpertiseAdaptor(Context context, List<Expertise> mListExpertise) {
        this.context = context;
        this.mListExpertise = mListExpertise;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expertise_item, parent, false);
        return new DoctorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, final int position) {
        holder.expertiseName.setText(mListExpertise.get(position).getName());

        holder.expertiseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectExpertise(mListExpertise.get(position).getId());
            }
        });
        holder.expertiseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectExpertise(mListExpertise.get(position).getId());
            }
        });

    }

    public void selectExpertise(String expertiseId) {

    }

    @Override
    public int getItemCount() {
        return mListExpertise.size();
    }



    public class DoctorViewHolder extends RecyclerView.ViewHolder {
        ImageView expertiseImage;
        TextView expertiseName;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            expertiseImage = itemView.findViewById(R.id.expertise_img);
            expertiseName = itemView.findViewById(R.id.expertise_name);
        }
    }
}
