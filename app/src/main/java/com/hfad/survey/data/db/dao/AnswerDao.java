package com.hfad.survey.data.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.hfad.survey.data.db.entity.AnswerEntity;

import java.util.List;

/**
 * Created by jlanecki on 07.02.18.
 */

@Dao
public interface AnswerDao {

    @Query("SELECT * FROM answers WHERE question_id = :questionId")
    LiveData<List<AnswerEntity>> loadAnswers(int questionId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAnswer(AnswerEntity question);

    @Delete
    void deleteAnswer(AnswerEntity question);
}
