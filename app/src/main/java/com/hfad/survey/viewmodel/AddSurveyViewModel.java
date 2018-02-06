package com.hfad.survey.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.hfad.survey.db.AppDatabase;
import com.hfad.survey.db.entity.SurveyEntity;

/**
 * Created by jlanecki on 06.02.18.
 */

public class AddSurveyViewModel extends AndroidViewModel {

    private AppDatabase appDatabase;

    public AddSurveyViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void addSurvey(final SurveyEntity SurveyEntity) {
        new AddSurveyViewModel.addAsyncTask(appDatabase).execute(SurveyEntity);
    }

    private static class addAsyncTask extends AsyncTask<SurveyEntity, Void, Void> {

        private AppDatabase db;

        addAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final SurveyEntity... params) {
            db.SurveyDao().insertSurvey(params[0]);
            return null;
        }

    }
}
