package com.hfad.survey;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hfad.survey.R;
import com.hfad.survey.db.entity.AnswerEntity;
import com.hfad.survey.db.entity.QuestionEntity;
import com.hfad.survey.viewmodel.ShowFormViewModel;

import java.util.List;

public class OpenFormActivity extends AppCompatActivity {

    private long surveyId;

    private TextView title;
    private String formTitle;

    private TextView description;
    private String formDescription;

    private LinearLayout QuestionListLinearLayout;

    private ShowFormViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        formTitle = getIntent().getStringExtra("survey_title");
        formDescription = getIntent().getStringExtra("survey_description");

        // set up ViewModel
        surveyId = getIntent().getLongExtra("survey_id", 0);
        viewModel = ViewModelProviders.of(this).get(ShowFormViewModel.class);
        viewModel.setId(surveyId);

        title = findViewById(R.id.form_text_title);
        description = findViewById(R.id.form_text_desc);
        QuestionListLinearLayout = findViewById(R.id.question_cards);

        // add Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.form_header));

        createForm();
    }

    public void createForm() {
        title.setText(formTitle);
        description.setText(formDescription);

        List<QuestionEntity> questionList = viewModel.loadQuestions();
        for (QuestionEntity question : questionList) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ViewGroup card = (ViewGroup)inflater.inflate(R.layout.form_question_card, null);

            TextView cardQuestion = card.findViewById(R.id.card_question);
            cardQuestion.setText(question.getContent());

            List<AnswerEntity> answerList = viewModel.loadAnswers(question.getId());
            for (AnswerEntity answer : answerList) {
                final View cardRow = inflater.inflate(R.layout.form_question_card_row, null);

                TextView cardRowOption = cardRow.findViewById(R.id.card_row_option);
                cardRowOption.setText(answer.getContent());
                LinearLayout cardLinearLayout = card.findViewById(R.id.card_linear_layout);
                cardLinearLayout.addView(cardRow, cardLinearLayout.getChildCount());
            }

            QuestionListLinearLayout.addView(card, QuestionListLinearLayout.getChildCount());
        }
    }

}
