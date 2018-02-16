package com.hfad.survey.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.hfad.survey.data.db.AppDatabase;
import com.hfad.survey.data.db.DataRepository;
import com.hfad.survey.data.db.entity.SurveyEntity;

import java.util.List;

/**
 * Created by jlanecki on 06.02.18.
 */

public class SurveyListViewModel extends AndroidViewModel {


    private final DataRepository dataRepository;

    public SurveyListViewModel(Application application, DataRepository repository) {
        super(application);

        dataRepository = repository;
    }

    public LiveData<List<SurveyEntity>> loadSurveyList() {
        return dataRepository.loadAllSurveys();
    }

    public void deleteSurvey(SurveyEntity surveyEntity) {
        dataRepository.deleteSurvey(surveyEntity);
    }

}
