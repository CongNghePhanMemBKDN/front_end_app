package com.example.bookingcare.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingcare.R;
import com.example.bookingcare.remote.ApiUtils;
import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.doctor.DoctorInfo;
import com.example.bookingcare.remote.doctor.Expertise;
import com.example.bookingcare.remote.user.UserController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDoctorFragment extends Fragment {

    View root;
    List<DoctorInfo> mListDoctor;
    RecyclerView mDoctorRcv;
    List<Expertise> mListExpertise;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_list_doctor, container, false);
        getListDoctor();
        return root;
    }

    private void getListDoctor() {
        showWaitingCircle();
        Call call = UserController.getInstance().getService().getDoctors("");
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                hideWaitingCircle();
                if (response.isSuccessful()) {
                    mListDoctor = (List<DoctorInfo>) response.body();
                    initView();

                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity().getBaseContext(), jObjError.getString("message"), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                hideWaitingCircle();
                Toast.makeText(getActivity().getBaseContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        DoctorAdaptor adaptor = new DoctorAdaptor(getContext(), mListDoctor);
        mDoctorRcv = root.findViewById(R.id.doctor_rcv);
        mDoctorRcv.setAdapter(adaptor);
        mDoctorRcv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void showWaitingCircle(){
      root.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
    }

    public void hideWaitingCircle(){
        root.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }
}
