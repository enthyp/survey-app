package com.hfad.survey.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.hfad.survey.db.entity.SurveyEntity;

import java.util.List;

/**
 * Created by jlanecki on 06.02.18.
 */

@Dao
public interface SurveyDao {

    @Query("SELECT * FROM surveys")
    LiveData<List<SurveyEntity>> loadAllSurveys();

    @Query("SELECT * FROM surveys WHERE id = :surveyId")
    LiveData<SurveyEntity> loadSurvey(int surveyId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSurvey(SurveyEntity survey);

    @Delete
    void deleteSurvey(SurveyEntity survey);
}
