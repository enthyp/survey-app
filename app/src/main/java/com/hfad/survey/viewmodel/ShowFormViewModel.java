package com.hfad.survey.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.hfad.survey.data.db.AppDatabase;
import com.hfad.survey.data.db.DataRepository;
import com.hfad.survey.data.db.entity.AnswerEntity;
import com.hfad.survey.data.db.entity.QuestionEntity;

import java.util.List;

/**
 * Created by jlanecki on 07.02.18.
 */

public class ShowFormViewModel extends AndroidViewModel {


    private final DataRepository dataRepository;

    public ShowFormViewModel(Application application, DataRepository repository) {
        super(application);

        dataRepository = repository;
    }

    public LiveData<List<QuestionAndAllAnswers>> loadQuestionsAndAnswers(long id) {
        return dataRepository.loadQuestionAndAnswers(id);
    }
}
