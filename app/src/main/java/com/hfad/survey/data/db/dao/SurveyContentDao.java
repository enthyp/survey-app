package com.hfad.survey.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.hfad.survey.viewmodel.SurveyContents;

import java.util.List;

/**
 * Created by jlanecki on 15.02.18.
 */

@Dao
public interface SurveyContentDao {

    @Transaction
    @Query("SELECT * FROM surveys WHERE id = :surveyId")
    LiveData<SurveyContents> loadSurveyContents(int surveyId);
}
