package com.hfad.survey;

import android.app.Dialog;
import android.app.DialogFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.hfad.survey.db.entity.SurveyEntity;
import com.hfad.survey.viewmodel.SurveyListViewModel;

import java.util.ArrayList;
import java.util.List;

public class FormsActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView formsRecyclerView;
    FormRecyclerAdapter formsRecyclerViewAdapter;
    SurveyListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.forms_title));

        // set up the RecyclerView
        formsRecyclerView = findViewById(R.id.forms_recycler);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        formsRecyclerView.setLayoutManager(llManager);

        formsRecyclerViewAdapter = new FormRecyclerAdapter(new ArrayList<SurveyEntity>(), this, this);
        formsRecyclerView.setAdapter(formsRecyclerViewAdapter);

        viewModel = ViewModelProviders.of(this).get(SurveyListViewModel.class);

        viewModel.loadSurveyList().observe(FormsActivity.this, new Observer<List<SurveyEntity>>() {
            @Override
            public void onChanged(@Nullable List<SurveyEntity> surveys) {
                formsRecyclerViewAdapter.addItems(surveys);
            }
        });

        formsRecyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onClick(final View v) {
        if(v.getId() == R.id.delete_form_button) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.form_dialog)
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {}
                    })
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            SurveyEntity surveyEntity = (SurveyEntity)v.getTag();
                            viewModel.deleteSurvey(surveyEntity);
                        }
                    });
            builder.show();
        } else if (v.getId() == R.id.open_survey_button) {
            SurveyEntity surveyEntity = (SurveyEntity)v.getTag();
            long id = surveyEntity.getId();
            String title = surveyEntity.getSurveyTitle();
            String description = surveyEntity.getSurveyDescription();

            Intent intent = new Intent(this, OpenFormActivity.class);
            intent.putExtra("survey_id", id);
            intent.putExtra("survey_title", title);
            intent.putExtra("survey_description", description);
            startActivity(intent);
        }
    }
}
