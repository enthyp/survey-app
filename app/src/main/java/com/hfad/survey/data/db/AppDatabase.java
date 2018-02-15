package com.hfad.survey.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.hfad.survey.data.db.converter.DateConverter;
import com.hfad.survey.data.db.dao.AnswerDao;
import com.hfad.survey.data.db.dao.QuestionAnswersDao;
import com.hfad.survey.data.db.dao.QuestionDao;
import com.hfad.survey.data.db.dao.SurveyDao;
import com.hfad.survey.data.db.entity.AnswerEntity;
import com.hfad.survey.data.db.entity.QuestionEntity;
import com.hfad.survey.data.db.entity.SurveyEntity;

/**
 * Created by jlanecki on 06.02.18.
 */

@Database(entities = {SurveyEntity.class, QuestionEntity.class, AnswerEntity.class}, version = 3)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {


    private static final String DATABASE_NAME = "survey_db";

    private static AppDatabase Instance;

    public abstract SurveyDao SurveyDao();

    public abstract QuestionDao QuestionDao();

    public abstract AnswerDao AnswerDao();

    public abstract QuestionAnswersDao QuestionAnswersDao();

    private final MutableLiveData<Boolean> isDatabaseCreated = new MutableLiveData<>();


    public static AppDatabase getInstance(Context context) {
        if (Instance == null) {
            Instance =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                            DATABASE_NAME)
                            .fallbackToDestructiveMigration() // TODO: do something about this!!!
                            .build();

            Instance.updateDatabaseCreated(context.getApplicationContext());
        }

        return Instance;
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        isDatabaseCreated.postValue(true);
    }

    LiveData<Boolean> getDatabaseCreated() {
        return isDatabaseCreated;
    }
}
