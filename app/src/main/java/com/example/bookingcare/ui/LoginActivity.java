package com.example.bookingcare.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bookingcare.Common;
import com.example.bookingcare.R;
import com.example.bookingcare.remote.ApiUtils;
import com.example.bookingcare.remote.Common.CommonService;
import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.doctor.DoctorInfo;
import com.example.bookingcare.remote.user.DoctorDetail;
import com.example.bookingcare.remote.user.UserController;
import com.example.bookingcare.remote.user.UserInfo;
import com.example.bookingcare.remote.user.UserService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail;
    EditText edtPassword;
    ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);
        loadingProgressBar = findViewById(R.id.loading);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeBaseUrl();
            }
        });


        Button btnLogin = findViewById(R.id.login_button);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                //validate form
                if(validateLogin(username, password)){
                    //do login
                    doLogin(username, password);
                }
            }
        });
        Button btnRegister = findViewById(R.id.register_button);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRegister();
            }
        });

        Button btnSwitchRole = findViewById(R.id.switch_role_button);
        btnSwitchRole.setText(getString(Common.isUser()?R.string.action_switch_role_doctor:R.string.action_switch_role_user));
        btnSwitchRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSwitchRole();
            }
        });
    }

    private void doSwitchRole() {
        Button btnSwitchRole = findViewById(R.id.switch_role_button);
        String msg;
        if (Common.isUser()){
            Common.ROLE = Common.ROLE_DOCTOR;
            Common.CONTROLLER = DoctorController.getInstance();
            btnSwitchRole.setText(getString(R.string.action_switch_role_user));
            msg = "Switch to doctor completed";
        } else {
            Common.ROLE = Common.ROLE_USER;
            Common.CONTROLLER = UserController.getInstance();
            btnSwitchRole.setText(getString(R.string.action_switch_role_doctor));
            msg = "Switch to patient completed";

        }
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void changeBaseUrl(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change base url");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(ApiUtils.BASE_URL);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ApiUtils.BASE_URL = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private boolean validateLogin(String email, String password){
        if(email == null || email.trim().length() == 0){
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(final String email, final String password){
        try {
            JSONObject body = new JSONObject();
            body.put("email", email);
            body.put("password", password);
            showWaitingCircle();
            Call call = Common.CONTROLLER.login(body.toString());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    hideWaitingCircle();
                    if (response.isSuccessful()) {
                        if(Common.isUser()) {
                            UserController.getInstance().setInfo((UserInfo) response.body());
                        } else if(Common.isDoctor()) {
                            DoctorController.getInstance().setInfo((DoctorInfo) response.body());

                        }
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(Common.ROLE_NAME, Common.ROLE);
                        startActivity(intent);

                    } else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            Toast.makeText(LoginActivity.this, jObjError.getString("message"), Toast.LENGTH_SHORT).show();

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
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            hideWaitingCircle();
            Toast.makeText(LoginActivity.this, "Login exception!", Toast.LENGTH_SHORT).show();
        }
    }

    private void doRegister(){

    }

    public void showWaitingCircle(){
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
    }

    public void hideWaitingCircle(){
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }
}
