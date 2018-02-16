package com.hfad.survey.openform;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hfad.survey.R;
import com.hfad.survey.ViewModelFactory;
import com.hfad.survey.data.db.entity.AnswerEntity;
import com.hfad.survey.viewmodel.QuestionAndAllAnswers;
import com.hfad.survey.viewmodel.ShowFormViewModel;
import com.hfad.survey.viewmodel.SurveyContents;


public class OpenFormActivity extends AppCompatActivity {

    private long surveyId;

    private TextView title;
    private String formTitle;

    private TextView description;
    private String formDescription;

    private LinearLayout QuestionListLinearLayout;

    private SurveyContents formContents;

    private ShowFormViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initial setup
        surveyId = getIntent().getLongExtra("survey_id", 0);
        title = findViewById(R.id.form_text_title);
        description = findViewById(R.id.form_text_desc);
        QuestionListLinearLayout = findViewById(R.id.question_cards);

        // Setup ViewModel
        viewModel = obtainViewModel(this);

        // Setup observation
        viewModel.loadSurveyContents(surveyId).observe(this, new Observer<SurveyContents>() {
            @Override
            public void onChanged(@Nullable SurveyContents surveyContents) {
                formContents = surveyContents;
                updateForm();
            }
        });


        // add Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.form_header));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }

        return true;
    }

    public static ShowFormViewModel obtainViewModel(AppCompatActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        ShowFormViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(ShowFormViewModel.class);

        return viewModel;
    }

    public void updateForm() {
        title.setText(formContents.survey.getSurveyTitle());
        description.setText(formContents.survey.getSurveyDescription());

        for (QuestionAndAllAnswers question : formContents.questionsWithAnswers) {
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
