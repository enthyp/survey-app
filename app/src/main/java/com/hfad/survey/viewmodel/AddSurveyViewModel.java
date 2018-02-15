package com.hfad.survey.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.hfad.survey.createform.SurveyForm;
import com.hfad.survey.data.db.DataRepository;
import com.hfad.survey.data.db.entity.AnswerEntity;
import com.hfad.survey.data.db.entity.QuestionEntity;
import com.hfad.survey.data.db.entity.SurveyEntity;

/**
 * Created by jlanecki on 06.02.18.
 */

public class AddSurveyViewModel extends AndroidViewModel {


    private final DataRepository dataRepository;

    public AddSurveyViewModel(Application application, DataRepository repository) {
        super(application);

        dataRepository = repository;
    }

    public long addSurvey(final SurveyEntity surveyEntity) {
        return dataRepository.addSurvey(surveyEntity);
    }

    public long addQuestion(final QuestionEntity QuestionEntity) {
        return dataRepository.addQuestion(QuestionEntity);
    }

    public void addAnswer(final AnswerEntity answerEntity) {
        dataRepository.addAnswer(answerEntity);
    }

    public void addSurvey(SurveyForm surveyForm) {
        SurveyEntity surveyEntity = new SurveyEntity(surveyForm.getTitle(),
                surveyForm.getDescription(), surveyForm.getDate());

        long id = this.addSurvey(surveyEntity);
        for (int i = 0; i < surveyForm.getQuestions().size(); i++) {
            QuestionEntity questionEntity = new QuestionEntity(id, surveyForm.getQuestions().get(i));

            long questionId = this.addQuestion(questionEntity);
            for (String answer : surveyForm.getAnswers(i)) {
                AnswerEntity answerEntity = new AnswerEntity(questionId, answer);

                this.addAnswer(answerEntity);
            }
        }
    }
}
