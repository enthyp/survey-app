package com.hfad.survey.forms;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.survey.BasicApp;
import com.hfad.survey.R;
import com.hfad.survey.ViewModelFactory;
import com.hfad.survey.data.db.entity.SurveyEntity;
import com.hfad.survey.openform.OpenFormActivity;
import com.hfad.survey.util.OnDeleteDialogFragment;
import com.hfad.survey.viewmodel.SurveyListViewModel;

import java.util.ArrayList;
import java.util.List;

public class FormsFragment extends Fragment implements View.OnClickListener {

    RecyclerView formsRecyclerView;
    FormRecyclerAdapter formsRecyclerViewAdapter;
    SurveyListViewModel viewModel;
    View deletedView;

    public FormsFragment() {
        // Required empty public constructor
    }

    public static FormsFragment newInstance() {
        return new FormsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_forms, container, false);

        // Setup the toolbar
        Toolbar toolbar = root.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        // // Setup the DrawerLayout
        DrawerLayout drawer = getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Setup the RecyclerView
        formsRecyclerView = root.findViewById(R.id.forms_recycler);
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        formsRecyclerView.setLayoutManager(llManager);

        formsRecyclerViewAdapter = new FormRecyclerAdapter(new ArrayList<SurveyEntity>(), this, getActivity());
        formsRecyclerView.setAdapter(formsRecyclerViewAdapter);

        viewModel = obtainViewModel((AppCompatActivity) getActivity());

        viewModel.loadSurveyList().observe(getActivity(), new Observer<List<SurveyEntity>>() {
            @Override
            public void onChanged(@Nullable List<SurveyEntity> surveys) {
                formsRecyclerViewAdapter.addItems(surveys);
            }
        });

        formsRecyclerView.setHasFixedSize(true);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.app_bar_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static SurveyListViewModel obtainViewModel(AppCompatActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        SurveyListViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(SurveyListViewModel.class);

        return viewModel;
    }

    @Override
    public void onClick(final View v) {
        if(v.getId() == R.id.delete_form_button) {
            deletedView = v;
            showDialog();
        } else if (v.getId() == R.id.open_survey_button) {
            SurveyEntity surveyEntity = (SurveyEntity)v.getTag();
            long id = surveyEntity.getId();

            Intent intent = new Intent(getActivity(), OpenFormActivity.class);
            intent.putExtra("survey_id", id);
            startActivity(intent);
        }
    }

    public void onDeleteClick() {
        final SurveyEntity surveyEntity = (SurveyEntity)deletedView.getTag();
        ((BasicApp)getActivity().getApplication()).getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                viewModel.deleteSurvey(surveyEntity);
            }
        });
    }

    void showDialog() {
        DialogFragment newFragment = OnDeleteDialogFragment.newInstance(
                R.string.form_dialog, R.string.yes, R.string.no
        );
        newFragment.show(getChildFragmentManager(), "dialog");
    }
}
