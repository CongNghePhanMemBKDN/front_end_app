package com.example.bookingcare.ui.common;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.Common;
import com.example.bookingcare.R;
import com.example.bookingcare.remote.doctor.DoctorInfo;
import com.example.bookingcare.ui.user.DoctorDetailActivity;

import java.util.List;

public class PhoneAdaptor extends RecyclerView.Adapter<PhoneAdaptor.PhoneViewHolder> {
    List<String> mListContact;
    Context context;

    public PhoneAdaptor(Context context, List<String>  listContact) {
        this.context = context;
        this.mListContact = listContact;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new PhoneViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, final int position) {
        holder.contact.setText(mListContact.get(position));

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mListContact.get(position), null));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mListContact.size();
    }



    public class PhoneViewHolder extends RecyclerView.ViewHolder {
        TextView contact;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            contact = itemView.findViewById(R.id.contact);
        }
    }
}

