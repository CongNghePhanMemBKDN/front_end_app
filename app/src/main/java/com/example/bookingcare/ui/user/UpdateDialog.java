package com.example.bookingcare.ui.user;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bookingcare.Common;
import com.example.bookingcare.R;
import com.example.bookingcare.remote.user.UserController;
import com.example.bookingcare.remote.user.UserInfo;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDialog extends BottomSheetDialog {
    private View sheetView ;
    private Button btnUpdate;
    private Button btnCancel;
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private RadioGroup rgGender;
    private Button btnBirthday;
    private TextInputEditText address;
    private DatePickerDialog datePicker;
    private TextView birthView;
    private java.util.Calendar birthday;
    private Runnable showCircleWaiting;
    private Runnable hideCircleWaiting;
    public UpdateDialog(@NonNull final Context context, Runnable showCircleWaiting, Runnable hideCircleWaiting) {
        super(context);
        sheetView = getLayoutInflater().inflate(R.layout.fragment_account, null);
        btnUpdate = sheetView.findViewById(R.id.btn_update);
        btnCancel = sheetView.findViewById(R.id.btn_cancel);
        firstName = sheetView.findViewById(R.id.edt_first_name);
        lastName = sheetView.findViewById(R.id.edt_last_name);
        birthView = sheetView.findViewById(R.id.birthday);
        rgGender = sheetView.findViewById(R.id.rg_gender);
        btnBirthday = sheetView.findViewById(R.id.btn_birthday);
        address = sheetView.findViewById(R.id.edt_address);

        this.setContentView(sheetView);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndUpdate();
            }
        });
        birthday = Calendar.getInstance();

        btnBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });

        datePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                birthday.set(Calendar.YEAR, year);
                birthday.set(Calendar.MONTH, month);
                birthday.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                birthView.setText(Common.dateToString(year, month, dayOfMonth));

            }
        }, birthday.get(Calendar.YEAR), birthday.get(Calendar.MONTH), birthday.get(Calendar.DAY_OF_MONTH));

        this.showCircleWaiting = showCircleWaiting;
        this.hideCircleWaiting = hideCircleWaiting;
    }

    @Override
    public void show(){
        UserInfo info = UserController.getInstance().getInfo();
        if (info != null) {
            firstName.setText(info.getFistName());
            lastName.setText(info.getLastName());
            address.setText(info.getAddress());
            birthday = info.getBirthdayCalendar();

            int year = birthday.get(Calendar.YEAR);
            int month = birthday.get(Calendar.MONTH);
            int day = birthday.get(Calendar.DAY_OF_MONTH);

            datePicker.updateDate(year, month, day);
            birthView.setText(Common.dateToString(year, month, day));
            String gender = info.getGender();
            for (int i = 0; i < rgGender.getChildCount(); i++) {
                View view = rgGender.getChildAt(i);
                if (view instanceof RadioButton) {
                    if (((RadioButton) view).getText().equals(gender)){
                        ((RadioButton) view).setChecked(true);
                    } else{
                        ((RadioButton) view).setChecked(false);
                    }
                }

            }
        }
        super.show();
    }

    private void validateAndUpdate() {
        String firstName = this.firstName.getText().toString();
        String lastName = this.lastName.getText().toString();
        String address = this.address.getText().toString();
        RadioButton checked = sheetView.findViewById(this.rgGender.getCheckedRadioButtonId());
        String gender = checked == null? null: checked.getText().toString();


        if(firstName == null || firstName.trim().length() == 0){
            Toast.makeText(getContext(), "First name is required!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(lastName == null || lastName.trim().length() == 0){
            Toast.makeText(getContext(), "Last name is required!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(gender == null || gender.trim().length() == 0){
            Toast.makeText(getContext(), "Gender is required!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(address == null || address.trim().length() == 0){
            Toast.makeText(getContext(), "Address is required!", Toast.LENGTH_SHORT).show();
            return ;
        }
        Calendar now = Calendar.getInstance();
        if(now.compareTo(birthday) < 0){
            Toast.makeText(getContext(), "Birth is invalid!", Toast.LENGTH_SHORT).show();
            return ;
        }

        updateInfo(firstName, lastName, birthView.getText().toString(),address, gender);

    }

    private void updateInfo(String firstName, String lastName, String birthday, String address, String gender) {
        try{
            JSONObject body = new JSONObject();
            body.put("fistName", firstName);
            body.put("lastName", lastName);
            body.put("birthday", birthday);
            body.put("address", address);
            body.put("gender", gender);

            showCircleWaiting.run();
            Call call = UserController.getInstance().getService().updateInfo("bearer " + Common.CONTROLLER.getInfo().getAccessToken(), body.toString());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    hideCircleWaiting.run();
                    if (response.isSuccessful()){
                        UserInfo info = (UserInfo) response.body();
                        UserInfo myInfo = UserController.getInstance().getInfo();
                        myInfo.setFistName(info.getFistName());
                        myInfo.setLastName(info.getLastName());
                        myInfo.setAddress(info.getAddress());
                        myInfo.setBirthday(info.getBirthday());
                        myInfo.setAppointment(info.getAppointment());
                        myInfo.setGender(info.getGender());
                        myInfo.setCreateAt(info.getCreateAt());
                        myInfo.setUpdateAt(info.getUpdateAt());

                        Toast.makeText(getContext(), "Update info completed!", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), "Update info failed!", Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    hideCircleWaiting.run();
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        } catch (Exception e){
            Toast.makeText(getContext(), "Update info exception!", Toast.LENGTH_SHORT).show();
            hideCircleWaiting.run();
            dismiss();
        }
    }
}
