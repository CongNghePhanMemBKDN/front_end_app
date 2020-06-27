package com.example.bookingcare;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingcare.remote.user.UserController;
import com.example.bookingcare.remote.user.UserInfo;
import com.example.bookingcare.ui.LoginActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private Button btnCancel;
    private TextInputEditText email;
    private TextInputEditText firstName;
    private TextInputEditText lastName;
    private RadioGroup rgGender;
    private Button btnBirthday;
    private TextInputEditText address;
    private DatePickerDialog datePicker;
    private TextView birthView;
    private java.util.Calendar birthday;
    private TextInputEditText password;
    private TextInputEditText reenterPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        LinearLayout userLayout = findViewById(R.id.layout_user_register);
        LinearLayout doctorLayout = findViewById(R.id.layout_doctor_register);

        if (Common.isDoctor()){
            userLayout.setVisibility(View.GONE);
            doctorLayout.setVisibility(View.VISIBLE);
        } else if (Common.isUser()){
            userLayout.setVisibility(View.VISIBLE);
            doctorLayout.setVisibility(View.GONE);


            btnRegister = findViewById(R.id.btn_register);
            btnCancel = findViewById(R.id.btn_cancel);
            email = findViewById(R.id.edt_email);
            firstName = findViewById(R.id.edt_first_name);
            lastName = findViewById(R.id.edt_last_name);
            birthView = findViewById(R.id.birthday);
            rgGender = findViewById(R.id.rg_gender);
            btnBirthday = findViewById(R.id.btn_birthday);
            address = findViewById(R.id.edt_address);
            password = findViewById(R.id.edt_password);
            reenterPassword = findViewById(R.id.edt_reenter_password);

            birthday = Calendar.getInstance();
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateAndRegister();
                }
            });

            btnBirthday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePicker.show();
                }
            });
            datePicker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    birthday.set(Calendar.YEAR, year);
                    birthday.set(Calendar.MONTH, month);
                    birthday.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    birthView.setText(Common.dateToString(year, month, dayOfMonth));

                }
            }, birthday.get(Calendar.YEAR), birthday.get(Calendar.MONTH), birthday.get(Calendar.DAY_OF_MONTH));


            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }


    }

    private void validateAndRegister() {
        String email = this.email.getText().toString();
        String firstName = this.firstName.getText().toString();
        String lastName = this.lastName.getText().toString();
        String address = this.address.getText().toString();
        RadioButton checked = findViewById(this.rgGender.getCheckedRadioButtonId());
        String gender = checked == null? null: checked.getText().toString();
        String password = this.password.getText().toString();
        String reenterPassword = this.reenterPassword.getText().toString();

        if(firstName == null || firstName.trim().length() == 0){
            Toast.makeText(RegisterActivity.this, "First name is required!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(lastName == null || lastName.trim().length() == 0){
            Toast.makeText(RegisterActivity.this, "Last name is required!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(address == null || address.trim().length() == 0){
            Toast.makeText(RegisterActivity.this, "Address is required!", Toast.LENGTH_SHORT).show();
            return ;
        }
        Calendar now = Calendar.getInstance();
        if(now.compareTo(birthday) < 0){
            Toast.makeText(RegisterActivity.this, "Birth is invalid!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(gender == null || gender.trim().length() == 0){
            Toast.makeText(RegisterActivity.this, "Gender is required!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(password == null || password.trim().length() == 0){
            Toast.makeText(RegisterActivity.this, "Password is required!", Toast.LENGTH_SHORT).show();
            return ;
        }

        if(reenterPassword == null || reenterPassword.trim().length() == 0){
            Toast.makeText(RegisterActivity.this, "ReenterPassword is required!", Toast.LENGTH_SHORT).show();
            return ;
        }


        if(!reenterPassword.equals(password)){
            Toast.makeText(RegisterActivity.this, "ReenterPassword does not match password!", Toast.LENGTH_SHORT).show();
            return ;
        }


        register(email, firstName, lastName, birthView.getText().toString(),address, gender, password);
    }

    private void register(String email, String firstName, String lastName, String birthday, String address, String gender, String password) {
        try{
            JSONObject body = new JSONObject();
            body.put("email", email);
            body.put("fistName", firstName);
            body.put("lastName", lastName);
            body.put("birthday", birthday);
            body.put("address", address);
            body.put("gender", gender);
            body.put("password", password);

            showWaitingCircle();
            Call call = UserController.getInstance().getService().register(body.toString());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    hideWaitingCircle();
                    if (response.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Register completed!", Toast.LENGTH_SHORT).show();
                        UserInfo info = (UserInfo) response.body();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra(Common.EMAIL, info.getEmail());
                        startActivity(intent);
                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(RegisterActivity.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    hideWaitingCircle();
                    Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Toast.makeText(RegisterActivity.this, "Register exception!", Toast.LENGTH_SHORT).show();
            hideWaitingCircle();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public void showWaitingCircle(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
    }

    public void hideWaitingCircle(){
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }
}
