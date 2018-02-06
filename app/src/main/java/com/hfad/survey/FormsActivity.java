package com.hfad.survey;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class FormsActivity extends AppCompatActivity {

    RecyclerView formsRecyclerView;
    ArrayList<String> forms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.forms_title));

        forms = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            forms.add("Form #" + i);
        }

        // set up the RecyclerView
        formsRecyclerView = findViewById(R.id.forms_recycler);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        formsRecyclerView.setLayoutManager(llManager);

        FormRecyclerAdapter adapter = new FormRecyclerAdapter(forms);
        formsRecyclerView.setAdapter(adapter);

        formsRecyclerView.setHasFixedSize(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


}
