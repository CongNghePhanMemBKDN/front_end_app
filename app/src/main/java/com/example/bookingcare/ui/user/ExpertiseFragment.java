package com.example.bookingcare.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;
import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.doctor.Expertise;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertiseFragment extends Fragment {
    View root;
    RecyclerView mExpertiseRcv;
    List<Expertise> mListExpertise;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_expertise, container, false);
        mExpertiseRcv = root.findViewById(R.id.expertise_rcv);
        getExpertise();
        return root;
    }
    private void getExpertise() {

            Call call = DoctorController.getInstance().getService().getExpertise();
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()){
                        mListExpertise = new ArrayList<>();
                        List<Map> body = (List) ((List) response.body()).get(0);
                        for (int i = 0; i < body.size(); i++) {
                            Expertise expertise = new Expertise();
                            expertise.setId((String) body.get(i).get("id"));
                            expertise.setName((String) body.get(i).get("name"));
                            mListExpertise.add(expertise);

                        }
                        initExpertise();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });

    }

    private void initExpertise() {
        ExpertiseAdaptor adaptor = new ExpertiseAdaptor(getContext(), mListExpertise);
        mExpertiseRcv.setAdapter(adaptor);
        mExpertiseRcv.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
