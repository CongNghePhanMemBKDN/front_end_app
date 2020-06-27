package com.example.bookingcare.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.bookingcare.Common;
import com.example.bookingcare.R;
import com.example.bookingcare.remote.doctor.DoctorController;
import com.example.bookingcare.remote.user.UserController;
import com.example.bookingcare.ui.user.UpdateDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    NavController navController;
    TextView txtFullName;
    UpdateDialog bottomDialog;
    JSONObject updateBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);


        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);
        navigationView.setNavigationItemSelectedListener(this);

        AppBarConfiguration mAppBarConfiguration = null;

        View headerView = navigationView.getHeaderView(0);
        txtFullName = headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.CONTROLLER.getInfo().getFullName());

        int role = getIntent().getIntExtra(Common.ROLE_NAME, Common.ROLE_USER);

        switch (role) {
            case Common.ROLE_USER:
                navGraph.setStartDestination(R.id.nav_home_user);
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_home_user, R.id.nav_account, R.id.nav_user_appointment, R.id.nav_expertise, R.id.nav_doctors, R.id.nav_contact, R.id.nav_logout)
                        .setDrawerLayout(drawerLayout)
                        .build();
                navigationView.getMenu().findItem(R.id.nav_home_doctor).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_doctor_appointment).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_schedule_manage).setVisible(false);

                break;
            case Common.ROLE_DOCTOR:
                navGraph.setStartDestination(R.id.nav_home_doctor);
                mAppBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.nav_home_doctor, R.id.nav_doctor_appointment, R.id.nav_schedule_manage, R.id.nav_contact, R.id.nav_logout)
                        .setDrawerLayout(drawerLayout)
                        .build();
                navigationView.getMenu().findItem(R.id.nav_home_user).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_account).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_user_appointment).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_doctors).setVisible(false);
                navigationView.getMenu().findItem(R.id.nav_expertise).setVisible(false);
                break;
        }
        if (mAppBarConfiguration == null) {
            throw new IllegalArgumentException();
        }
        navController.setGraph(navGraph);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_logout:
                        doLogout();
                        return true;
                    case R.id.nav_account:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        showUpdateDialog();
                        return true;
                }
                //This is for maintaining the behavior of the Navigation view
                NavigationUI.onNavDestinationSelected(menuItem, navController);
                //This is for closing the drawer after acting on it
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        bottomDialog = new UpdateDialog(this,
                new Runnable() {
            @Override
            public void run() {
                bottomDialog.dismiss();
//                showWaitingCircle();
            }},
                new Runnable() {
            @Override
            public void run() {
//                hideWaitingCircle();
            }});
        bottomDialog.setTitle("Update information");
        updateBody = new JSONObject();

    }

    private void showUpdateDialog() {
        bottomDialog.dismiss();
        bottomDialog.show();
    }

    private void doLogout() {
        UserController.getInstance().setInfo(null);
        DoctorController.getInstance().setInfo(null);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void showWaitingCircle() {
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
    }

    public void hideWaitingCircle() {
        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }
}
