package com.hfad.survey;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.hfad.survey.data.db.DataRepository;
import com.hfad.survey.viewmodel.AddSurveyViewModel;
import com.hfad.survey.viewmodel.ShowFormViewModel;
import com.hfad.survey.viewmodel.SurveyListViewModel;

/**
 * Created by jlanecki on 14.02.18.
 */

/* Used to inject DataRepository into ViewModels */

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    @SuppressLint("StaticFieldLeak")
    private static ViewModelFactory Instance;

    private final DataRepository dataRepository;

    private final Application application;

    public static ViewModelFactory getInstance(Application application) {
        if (Instance == null) {
            synchronized (ViewModelFactory.class) {
                if (Instance == null) {
                    Instance = new ViewModelFactory(application,
                            ((BasicApp)application).getRepository());
                }
            }
        }

        return Instance;
    }

    public static void destroyInstance() {
        Instance = null;
    }

    private ViewModelFactory(Application application, DataRepository repository) {
        this.application = application;
        dataRepository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddSurveyViewModel.class)) {
            //noinspection unchecked
            return (T) new AddSurveyViewModel(application, dataRepository);
        } else if (modelClass.isAssignableFrom(ShowFormViewModel.class)) {
            //noinspection unchecked
            return (T) new ShowFormViewModel(application, dataRepository);
        } else if (modelClass.isAssignableFrom(SurveyListViewModel.class)) {
            //noinspection unchecked
            return (T) new SurveyListViewModel(application, dataRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
