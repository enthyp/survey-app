package com.hfad.survey.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jlanecki on 07.02.18.
 */

@Entity(tableName = "questions",
        foreignKeys = {
        @ForeignKey(entity = SurveyEntity.class,
                parentColumns = "id",
                childColumns = "survey_id",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "survey_id")
        })
public class QuestionEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "survey_id")
    private long surveyId;

    @ColumnInfo(name = "content")
    private String content;

    public QuestionEntity() {}

    public QuestionEntity(long surveyId, String content) {
        this.surveyId = surveyId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
