package com.hfad.survey.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.hfad.survey.db.AppDatabase;
import com.hfad.survey.db.entity.SurveyEntity;

import java.util.List;

/**
 * Created by jlanecki on 06.02.18.
 */

public class SurveyListViewModel extends AndroidViewModel {

    private final LiveData<List<SurveyEntity>> SurveyList;

    private AppDatabase appDatabase;

    public SurveyListViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());

        SurveyList = appDatabase.SurveyDao().loadAllSurveys();
    }

    public LiveData<List<SurveyEntity>> loadSurveyList() {
        return SurveyList;
    }

    public void deleteSurvey(SurveyEntity SurveyEntity) {
        new deleteAsyncTask(appDatabase).execute(SurveyEntity);
    }

    private static class deleteAsyncTask extends AsyncTask<SurveyEntity, Void, Void> {

        private AppDatabase db;

        deleteAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final SurveyEntity... params) {
            db.SurveyDao().deleteSurvey(params[0]);
            return null;
        }

    }

}
