package com.hfad.survey.data.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.hfad.survey.AppExecutors;
import com.hfad.survey.BasicApp;
import com.hfad.survey.data.db.entity.AnswerEntity;
import com.hfad.survey.data.db.entity.QuestionEntity;
import com.hfad.survey.data.db.entity.SurveyEntity;
import com.hfad.survey.viewmodel.QuestionAndAllAnswers;

import java.util.List;

/**
 * Created by jlanecki on 14.02.18.
 */

/* DataRepository provides unified API for all data sources */

public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase database;

    private final AppExecutors appExecutors;

    private MediatorLiveData<List<SurveyEntity>> listSurveys;

    private DataRepository(final AppDatabase database, Application application) {
        this.database = database;
        this.appExecutors = ((BasicApp) application).getAppExecutors();
        this.listSurveys = new MediatorLiveData<>();

        listSurveys.addSource(database.SurveyDao().loadAllSurveys(),
                new Observer<List<SurveyEntity>>() {
                    @Override
                    public void onChanged(@Nullable List<SurveyEntity> surveyEntities) {
                        if(database.getDatabaseCreated().getValue() != null) {
                            listSurveys.postValue(surveyEntities);
                        }
            }
        });
    }

    public static DataRepository getInstance(final AppDatabase database, Application application) {
        if(sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database, application);
                }
            }
        }

        return sInstance;
    }

    /* SurveyListViewModel */

    public LiveData<List<SurveyEntity>> loadAllSurveys() {
        return database.SurveyDao().loadAllSurveys();
    }

    public void deleteSurvey(final SurveyEntity survey) {
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                database.SurveyDao().deleteSurvey(survey);
            }
        });
    }

    /* AddSurveyViewModel */

    public long addSurvey(final SurveyEntity SurveyEntity) {
        return database.SurveyDao().insertSurvey(SurveyEntity);
    }

    public long addQuestion(final QuestionEntity QuestionEntity) {
        return database.QuestionDao().insertQuestion(QuestionEntity);
    }

    public void addAnswer(final AnswerEntity answerEntity) {
        database.AnswerDao().insertAnswer(answerEntity);
    }

    /* ShowFormViewModel */

    public LiveData<List<QuestionAndAllAnswers>> loadQuestionAndAnswers(long id) {
        return database.QuestionAnswersDao().loadQuestionsAndAnswers((int)id);
    }
}
