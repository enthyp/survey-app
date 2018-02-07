package com.hfad.survey.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.hfad.survey.db.entity.QuestionEntity;

import java.util.List;

/**
 * Created by jlanecki on 07.02.18.
 */

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions WHERE survey_id = :surveyId")
    List<QuestionEntity> loadQuestions(int surveyId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertQuestion(QuestionEntity question);

    @Delete
    void deleteQuestion(QuestionEntity question);
}
