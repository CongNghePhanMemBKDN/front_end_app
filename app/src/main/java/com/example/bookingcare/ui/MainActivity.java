package com.example.bookingcare.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.bookingcare.R;
import com.example.bookingcare.remote.ApiUtils;
import com.example.bookingcare.remote.doctor.DoctorInfo;
import com.google.android.material.navigation.NavigationView;

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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    TextView txtFullName;

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
        navigationView.setNavigationItemSelectedListener(this);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.mobile_navigation);

        AppBarConfiguration mAppBarConfiguration = null;
        if (ApiUtils.getInstance().isUser()) {
            navGraph.setStartDestination(R.id.nav_home_user);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home_user, R.id.nav_departments, R.id.nav_contact)
                    .setDrawerLayout(drawerLayout)
                    .build();
            navigationView.getMenu().findItem(R.id.nav_home_doctor).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_home_admin).setVisible(false);
        } else if (ApiUtils.getInstance().isDoctor()) {
            navGraph.setStartDestination(R.id.nav_home_doctor);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home_doctor, R.id.nav_schedule_manage, R.id.nav_contact)
                    .setDrawerLayout(drawerLayout)
                    .build();
            navigationView.getMenu().findItem(R.id.nav_home_admin).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_home_user).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_doctors).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_departments).setVisible(false);

        } else if (ApiUtils.getInstance().isAdmin()) {
            navGraph.setStartDestination(R.id.nav_home_admin);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home_admin, R.id.nav_departments, R.id.nav_contact)
                    .setDrawerLayout(drawerLayout)
                    .build();
            navigationView.getMenu().findItem(R.id.nav_home_user).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_home_doctor).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_doctors).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_departments).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_pages).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_blog).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_contact).setVisible(false);
        }
        if (mAppBarConfiguration == null){
            throw new IllegalArgumentException();
        }
        navController.setGraph(navGraph);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View headerView = navigationView.getHeaderView(0);
        txtFullName =headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(DoctorInfo.getInstance().getFullName());

    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else{
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
}
