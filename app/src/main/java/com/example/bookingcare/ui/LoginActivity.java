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

import com.example.bookingcare.R;
import com.example.bookingcare.remote.ApiUtils;
import com.example.bookingcare.remote.doctor.DoctorService;
import com.example.bookingcare.remote.doctor.DoctorInfo;
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


        Button btnLoginAsUser = findViewById(R.id.login_as_user);
        btnLoginAsUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                //validate form
                if(validateLogin(username, password)){
                    //do login
                    doLoginAsUser(username, password);
                }
            }
        });
        Button btnLoginAsDoctor = findViewById(R.id.login_as_doctor);
        btnLoginAsDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                //validate form
                if(validateLogin(username, password)){
                    //do login
                    doLoginAsDoctor(username, password);
                }
            }
        });
        Button btnLoginAsAdmin = findViewById(R.id.login_as_admin);
        btnLoginAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                //validate form
                if(validateLogin(username, password)){
                    //do login
                    doLoginAsAdmin(username, password);
                }
            }
        });
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

    private void doLoginAsUser(final String email, final String password){
        try {

            JSONObject body = new JSONObject();
            body.put("email", email);
            body.put("password", password);
            Call call = ApiUtils.getInstance().getUserService().login(body.toString());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()) {
                        ApiUtils.getInstance().setRole(ApiUtils.USER);
                        DoctorInfo.init((DoctorInfo) response.body());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Toast.makeText(LoginActivity.this, "Login exception!", Toast.LENGTH_SHORT).show();
        }
    }

    private void doLoginAsDoctor(final String email, final String password){
        try {

            JSONObject body = new JSONObject();
            body.put("email", email);
            body.put("password", password);
            Call call = ApiUtils.getInstance().getDoctorService().login(body.toString());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()) {
                        ApiUtils.getInstance().setRole(ApiUtils.DOCTOR);
                        DoctorInfo.init((DoctorInfo) response.body());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Toast.makeText(LoginActivity.this, "Login exception!", Toast.LENGTH_SHORT).show();
        }
    }

    private void doLoginAsAdmin(final String email,final String password){
        try {
            JSONObject body = new JSONObject();
            body.put("email", email);
            body.put("password", password);
            Call call = ApiUtils.getInstance().getAdminService().login(body.toString());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()) {
                        ApiUtils.getInstance().setRole(ApiUtils.ADMIN);
                        DoctorInfo.init((DoctorInfo) response.body());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){
            Toast.makeText(LoginActivity.this, "Login exception!", Toast.LENGTH_SHORT).show();
        }
    }
}
