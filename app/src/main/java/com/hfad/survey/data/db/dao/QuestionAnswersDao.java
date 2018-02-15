package com.hfad.survey.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.hfad.survey.viewmodel.QuestionAndAllAnswers;

import java.util.List;

/**
 * Created by jlanecki on 15.02.18.
 */

@Dao
public interface QuestionAnswersDao {


    @Query("SELECT * FROM questions WHERE survey_id = :surveyId")
    LiveData<List<QuestionAndAllAnswers>> loadQuestionsAndAnswers(int surveyId);
}
