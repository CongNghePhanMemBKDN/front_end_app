package com.example.bookingcare.ui.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_contact, container, false);

        RecyclerView lvPhone = root.findViewById(R.id.lv_phone);
        RecyclerView lvMail = root.findViewById(R.id.lv_mail);
        RecyclerView lvFacebook = root.findViewById(R.id.lv_facebook);

        List<String> phones = new ArrayList<>();
        phones.add("(+84) 986421400");
        phones.add("(+84) 796295776");
        phones.add("(+84) 562591633");

        List<String> mails = new ArrayList<>();
        mails.add("hoquochuy1998@gmail.com");
        mails.add("haitan28102408@gmail.com");
        mails.add("thevinh021098@gmail.com");

        List<String> faceBooks = new ArrayList<>();

        faceBooks.add("https://www.facebook.com/huyho1712");
        faceBooks.add("https://www.facebook.com/haitan2408");
        faceBooks.add("https://www.facebook.com/vinh5051");


        lvPhone.setAdapter(new PhoneAdaptor(getContext(), phones));
        lvPhone.setLayoutManager(new LinearLayoutManager(getContext()));

        lvMail.setAdapter(new MailAdaptor(getContext(), mails));
        lvMail.setLayoutManager(new LinearLayoutManager(getContext()));

        lvFacebook.setAdapter(new FaceBookAdaptor(getContext(), faceBooks));
        lvFacebook.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }
}
