package com.example.bookingcare.ui.common;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;

import java.util.List;

public class MailAdaptor extends RecyclerView.Adapter<MailAdaptor.MailViewHolder> {
    List<String> mListContact;
    Context context;

    public MailAdaptor(Context context, List<String>  listContact) {
        this.context = context;
        this.mListContact = listContact;
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new MailViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MailViewHolder holder, final int position) {
        holder.contact.setText(mListContact.get(position));

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] {mListContact.get(position)});
                intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                intent.putExtra(android.content.Intent.EXTRA_TEXT,"");
                context.startActivity(Intent.createChooser(intent, "Send mail..."));
            }
        });
    }


    @Override
    public int getItemCount() {
        return mListContact.size();
    }



    public class MailViewHolder extends RecyclerView.ViewHolder {
        TextView contact;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            contact = itemView.findViewById(R.id.contact);
        }
    }
}

