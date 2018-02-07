package com.hfad.survey.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;

import com.hfad.survey.db.AppDatabase;
import com.hfad.survey.db.entity.AnswerEntity;
import com.hfad.survey.db.entity.QuestionEntity;
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

    public long addSurvey(final SurveyEntity SurveyEntity) {
        return appDatabase.SurveyDao().insertSurvey(SurveyEntity);
    }


    public long addQuestion(final QuestionEntity QuestionEntity) {
        return appDatabase.QuestionDao().insertQuestion(QuestionEntity);
    }


    public void addAnswer(final AnswerEntity AnswerEntity) {
        new AddSurveyViewModel.addAnswerAsyncTask(appDatabase).execute(AnswerEntity);
    }

    private static class addAnswerAsyncTask extends AsyncTask<AnswerEntity, Void, Void> {

        private AppDatabase db;

        addAnswerAsyncTask(AppDatabase appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final AnswerEntity... params) {
            db.AnswerDao().insertAnswer(params[0]);
            return null;
        }

    }
}
