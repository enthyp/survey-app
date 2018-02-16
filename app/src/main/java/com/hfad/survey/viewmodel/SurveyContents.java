package com.hfad.survey.viewmodel;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.hfad.survey.data.db.entity.QuestionEntity;
import com.hfad.survey.data.db.entity.SurveyEntity;

import java.util.List;

/**
 * Created by jlanecki on 16.02.18.
 */

public class SurveyContents {


    @Embedded
    public SurveyEntity survey;

    @Relation(parentColumn = "id", entityColumn = "survey_id", entity = QuestionEntity.class)
    public List<QuestionAndAllAnswers> questionsWithAnswers;
}
