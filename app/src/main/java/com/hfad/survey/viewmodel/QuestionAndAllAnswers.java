package com.hfad.survey.viewmodel;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.hfad.survey.data.db.entity.AnswerEntity;
import com.hfad.survey.data.db.entity.QuestionEntity;

import java.util.List;

/**
 * Created by jlanecki on 15.02.18.
 */

public class QuestionAndAllAnswers {


    @Embedded
    public QuestionEntity question;

    @Relation(parentColumn = "id", entityColumn = "question_id")
    public List<AnswerEntity> answers;
}
