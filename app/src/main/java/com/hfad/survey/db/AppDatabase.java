package com.hfad.survey.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.hfad.survey.db.converter.DateConverter;
import com.hfad.survey.db.dao.AnswerDao;
import com.hfad.survey.db.dao.QuestionDao;
import com.hfad.survey.db.dao.SurveyDao;
import com.hfad.survey.db.entity.AnswerEntity;
import com.hfad.survey.db.entity.QuestionEntity;
import com.hfad.survey.db.entity.SurveyEntity;

/**
 * Created by jlanecki on 06.02.18.
 */

@Database(entities = {SurveyEntity.class, QuestionEntity.class, AnswerEntity.class}, version = 3)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase Instance;

    public abstract SurveyDao SurveyDao();

    public abstract QuestionDao QuestionDao();

    public abstract AnswerDao AnswerDao();

    public static AppDatabase getDatabase(Context context) {
        if (Instance == null) {
            Instance =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "survey_db")
                            .fallbackToDestructiveMigration() //OH NAY!
                            .allowMainThreadQueries() // OH GOD, NO! just for showcase
                            .build();
        }
        return Instance;
    }
}
