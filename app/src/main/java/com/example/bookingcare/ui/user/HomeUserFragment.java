package com.example.bookingcare.ui.user;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bookingcare.R;

import java.util.Calendar;

public class HomeUserFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_user, container, false);


        Button btnCheckOurServices = root.findViewById(R.id.btn_check_our_services);
        btnCheckOurServices.setTransformationMethod(null);

        Button btnLearnMore = root.findViewById(R.id.btn_learn_more);
        btnLearnMore.setPaintFlags(btnLearnMore.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnLearnMore.setTransformationMethod(null);

        LinearLayout layoutEyeCare = root.findViewById(R.id.layout_eye_care);
        layoutEyeCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Onclick in department: EyeCare");
            }
        });

        LinearLayout layoutPhysicalTherapy = root.findViewById(R.id.layout_physical_therapy);
        layoutPhysicalTherapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Onclick in department: PhysicalTherapy");
            }
        });

        LinearLayout layoutDentalCare = root.findViewById(R.id.layout_dental_care);
        layoutDentalCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Onclick in department: DentalCare");
            }
        });

        LinearLayout layoutDiagnosticTest = root.findViewById(R.id.layout_diagnostic_test);
        layoutDiagnosticTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Onclick in department: Diagnostic Test");
            }
        });

        LinearLayout layoutSkinSurgery = root.findViewById(R.id.layout_skin_surgery);
        layoutSkinSurgery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Onclick in department: SkinSurgery");
            }
        });

        LinearLayout layoutSurgeryService = root.findViewById(R.id.layout_surgery_service);
        layoutSurgeryService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Onclick in department: SurgeryService");
            }
        });

        TextView txt_copy_right = root.findViewById(R.id.txt_copy_right);
        String year = String.valueOf(Calendar.getInstance().get( Calendar.YEAR));
        String text = getString(R.string.txt_copyright).replace("@year", year).replace("@name", getString(R.string.app_name));
        txt_copy_right.setText(text);
        
        return root;
    }
}
