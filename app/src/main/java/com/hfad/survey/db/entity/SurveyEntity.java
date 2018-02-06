package com.hfad.survey.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Created by jlanecki on 06.02.18.
 */
@Entity (tableName = "surveys")
public class SurveyEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "survey_title")
    private String surveyTitle;

    @ColumnInfo(name = "survey_date")
    private Date surveyDate;


    public SurveyEntity() {}

    public SurveyEntity(String surveyTitle, Date surveyDate) {
        this.surveyTitle = surveyTitle;
        this.surveyDate = surveyDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurveyTitle() {
        return surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public Date getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(Date surveyDate) {
        this.surveyDate = surveyDate;
    }
}
