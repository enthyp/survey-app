package com.hfad.survey.openform;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hfad.survey.R;
import com.hfad.survey.ViewModelFactory;
import com.hfad.survey.data.db.entity.AnswerEntity;
import com.hfad.survey.viewmodel.QuestionAndAllAnswers;
import com.hfad.survey.viewmodel.ShowFormViewModel;

import java.util.List;

public class OpenFormActivity extends AppCompatActivity {

    private long surveyId;

    private TextView title;
    private String formTitle;

    private TextView description;
    private String formDescription;

    private LinearLayout QuestionListLinearLayout;

    private List<QuestionAndAllAnswers> questionAnswersList;

    private ShowFormViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        surveyId = getIntent().getLongExtra("survey_id", 0);
        formTitle = getIntent().getStringExtra("survey_title");
        formDescription = getIntent().getStringExtra("survey_description");

        // Setup ViewModel
        viewModel = obtainViewModel(this);

        // Setup observation
        viewModel.loadQuestionsAndAnswers(surveyId).observe(this, new Observer<List<QuestionAndAllAnswers>>() {
            @Override
            public void onChanged(@Nullable List<QuestionAndAllAnswers> questionAndAllAnswers) {
                questionAnswersList = questionAndAllAnswers;
                updateForm();
            }
        });

        title = findViewById(R.id.form_text_title);
        description = findViewById(R.id.form_text_desc);
        QuestionListLinearLayout = findViewById(R.id.question_cards);

        // add Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.form_header));
    }

    public static ShowFormViewModel obtainViewModel(AppCompatActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        ShowFormViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(ShowFormViewModel.class);

        return viewModel;
    }

    public void updateForm() {
        title.setText(formTitle);
        description.setText(formDescription);

        for (QuestionAndAllAnswers question : questionAnswersList) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ViewGroup card = (ViewGroup)inflater.inflate(R.layout.form_question_card, null);

            TextView cardQuestion = card.findViewById(R.id.card_question);
            cardQuestion.setText(question.question.getContent());

            for (AnswerEntity answer : question.answers) {
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
