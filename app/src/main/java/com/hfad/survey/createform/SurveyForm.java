package com.hfad.survey.createform;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jlanecki on 15.02.18.
 */

/* This class is used to avoid iteratively inserting form elements
 * into database as it would unnecessarily keep the UI thread occupied */

public class SurveyForm {


    public SurveyForm(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    private String title;

    private String description;

    private Date date;

    private List<String> questions;

    private List<List<String>> answers;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void addQuestion(String question) {
        this.questions.add(question);
        this.answers.add(new ArrayList<String>());
    }

    public List<String> getAnswers(int position) {
        return answers.get(position);
    }

    public void addAnswer(String answer, int position) {
        this.answers.get(position).add(answer);
    }
}
