package com.hfad.survey;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hfad.survey.forms.FormsFragment;
import com.hfad.survey.results.ResultsFragment;
import com.hfad.survey.util.ActivityUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup the NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_surveys);
        navigationView.setNavigationItemSelectedListener(this);

        // Add fragment to the activity
        SurveyFragment surveyFragment = SurveyFragment.newInstance();
        ActivityUtil.replaceFragmentInActivity(
                getSupportFragmentManager(), surveyFragment, R.id.main_content_frame);

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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Class fragmentClass = null;

        if (id == R.id.nav_surveys) {
            fragmentClass = SurveyFragment.class;
        } else if (id == R.id.nav_forms) {
            fragmentClass = FormsFragment.class;
        } else if (id == R.id.nav_results) {
            fragmentClass = ResultsFragment.class;
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_help) {

        }

        try {
            Fragment newFragment = (Fragment)fragmentClass.newInstance();
            ActivityUtil.replaceFragmentInActivity(
                    getSupportFragmentManager(), newFragment, R.id.main_content_frame);
        } catch (Exception e) {
            e.printStackTrace();
        }


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(id);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
