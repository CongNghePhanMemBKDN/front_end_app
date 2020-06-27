package com.example.bookingcare.ui.common;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;

import java.util.List;

public class FaceBookAdaptor extends RecyclerView.Adapter<FaceBookAdaptor.FaceBookViewHolder> {
    List<String> mListContact;
    Context context;

    public FaceBookAdaptor(Context context, List<String>  listContact) {
        this.context = context;
        this.mListContact = listContact;
    }

    @NonNull
    @Override
    public FaceBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new FaceBookViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull FaceBookViewHolder holder, final int position) {
        holder.contact.setText(mListContact.get(position));

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mListContact.get(position)));
                context.startActivity(intent);
            }
        });
    }

    public Intent newFacebookIntent(PackageManager pm, String url) {
        Uri uri = Uri.parse(url);
        try {
            ApplicationInfo applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0);
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=" + url);
            }
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return new Intent(Intent.ACTION_VIEW, uri);
    }

    @Override
    public int getItemCount() {
        return mListContact.size();
    }



    public class FaceBookViewHolder extends RecyclerView.ViewHolder {
        TextView contact;

        public FaceBookViewHolder(@NonNull View itemView) {
            super(itemView);
            contact = itemView.findViewById(R.id.contact);
        }
    }
}

