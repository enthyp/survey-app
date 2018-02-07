package com.hfad.survey.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import com.hfad.survey.db.AppDatabase;
import com.hfad.survey.db.entity.AnswerEntity;
import com.hfad.survey.db.entity.QuestionEntity;

import java.util.List;

/**
 * Created by jlanecki on 07.02.18.
 */

public class ShowFormViewModel extends AndroidViewModel {

    private List<QuestionEntity> QuestionList;

    private AppDatabase appDatabase;

    public ShowFormViewModel(Application application) {
        super(application);

        appDatabase = AppDatabase.getDatabase(this.getApplication());
    }

    public void setId(long id) {
        QuestionList = appDatabase.QuestionDao().loadQuestions((int)id);
    }

    public List<QuestionEntity> loadQuestions() { return QuestionList ;}

    public List<AnswerEntity> loadAnswers(int id) {
        return appDatabase.AnswerDao().loadAnswers(id);
    }
}
