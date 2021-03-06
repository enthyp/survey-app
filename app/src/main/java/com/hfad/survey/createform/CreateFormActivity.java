package com.hfad.survey.createform;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hfad.survey.BasicApp;
import com.hfad.survey.R;
import com.hfad.survey.ViewModelFactory;
import com.hfad.survey.util.OnDeleteDialogFragment;
import com.hfad.survey.viewmodel.AddSurveyViewModel;

import java.util.Calendar;

public class CreateFormActivity extends AppCompatActivity
        implements OnDeleteDialogFragment.OnDeleteClickListener {

    private EditText title;

    private EditText description;

    private LinearLayout QuestionListLinearLayout;

    private AddSurveyViewModel viewModel;

    private View deletedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // create ViewModel instance
        viewModel = obtainViewModel(this);

        // add Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.create_form_title));

        title = findViewById(R.id.edit_text_title);
        description = findViewById(R.id.edit_text_desc);
        QuestionListLinearLayout = findViewById(R.id.question_cards);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_form, menu);
        return true;
    }

    public static AddSurveyViewModel obtainViewModel(AppCompatActivity activity) {
        // Use a Factory to inject dependencies into the ViewModel
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        AddSurveyViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(AddSurveyViewModel.class);

        return viewModel;
    }

    public void onAddQuestion(View v) {
        // inflate the QuestionCard View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View cardView = inflater.inflate(R.layout.question_card, null);

        // set onClick method for Add Option Button
        final Button addOptionButton = cardView.findViewById(R.id.add_option_button);
        addOptionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onAddOption(v);
            }
        });

        // set onClick method for Delete Question ImageButton
        final ImageButton deleteQuestionButton = cardView.findViewById(R.id.delete_question_button);
        deleteQuestionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onDeleteQuestion(v);
            }
        });

        // add this QuestionCard to the QuestionList
        QuestionListLinearLayout.addView(cardView, QuestionListLinearLayout.getChildCount());



        /*String option1 = getResources().getString(R.string.multiple_choice);
        String option2 = getResources().getString(R.string.checkboxes);
        String option3 = getResources().getString(R.string.dropdown);

        final String [] items = new String[] {option1, option2, option3};
        final Integer[] icons = new Integer[] {R.drawable.ic_radio_button_checked_black_24dp,
                R.drawable.ic_check_box_black_24dp, R.drawable.ic_drop_down_circle_black_24dp};
        ListAdapter adapter = new ArrayAdapterWithIcon(this, items, icons);

        new AlertDialog.Builder(this)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item ) {
                        Toast.makeText(CreateFormActivity.this, "Item Selected: " + item, Toast.LENGTH_SHORT).show();
                    }
                }).show();*/
    }

    public void onDeleteQuestion(final View v) {
        deletedView = v;
        showDialog();
    }

    public void onDeleteClick() {
        // get the parent Cardview and its parent LinearLayout
        ViewGroup parentCardView = (ViewGroup)(deletedView.getParent()).getParent();
        ViewGroup parentLinearLayout = (ViewGroup)parentCardView.getParent();

        // delete the parentCardview
        parentLinearLayout.removeView((View)parentCardView);
    }

    void showDialog() {
        DialogFragment newFragment = OnDeleteDialogFragment.newInstance(
                R.string.form_dialog, R.string.yes, R.string.no
        );
        newFragment.show(getSupportFragmentManager(), "dialog");
    }


    public void onAddOption(View v) {
        // get the parent CardView
        ViewGroup parentCardView = (ViewGroup)v.getParent();

        // inflate the QuestionCardRow View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.question_card_row, null);

        // set onClick method for Delete ImageButton
        final ImageButton deleteOptionButton = rowView.findViewById(R.id.delete_option_button);
        deleteOptionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onDeleteOption(v);
            }
        });

        // add RowView to the CardView
        parentCardView.addView(rowView, parentCardView.getChildCount() - 2);
    }

    public void onDeleteOption(View v) {
        // get both parent RowView and its parent CardView
        ViewGroup parentRowView = (ViewGroup)v.getParent();
        ViewGroup parentCardView = (ViewGroup)parentRowView.getParent();

        // remove given RowView from parent CardView
        parentCardView.removeView(parentRowView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()) {
            case R.id.save_form:
                onSaveSurvey();
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onSaveSurvey() {
        final SurveyForm surveyForm = new SurveyForm(
                title.getText().toString(),
                description.getText().toString(),
                Calendar.getInstance().getTime());

        LinearLayout questionCards = findViewById(R.id.question_cards);
        for (int i = 0; i < questionCards.getChildCount(); i++) {
            CardView card = (CardView) questionCards.getChildAt(i);

            EditText question = card.findViewById(R.id.card_question);
            String content = question.getText().toString();

            if(!content.isEmpty()) {
                surveyForm.addQuestion(content);

                LinearLayout answers = card.findViewById(R.id.card_linear_layout);
                for (int j = 2; j < answers.getChildCount(); j++) {
                    View child = answers.getChildAt(j);

                    if (child.getId() == R.id.card_row_linear_layout) {
                        EditText option = child.findViewById(R.id.card_row_option);
                        content = option.getText().toString();

                        if(!content.isEmpty()) {
                            surveyForm.addAnswer(content, i);
                        }
                    }
                }
            }
        }

        ((BasicApp)getApplication()).getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                viewModel.addSurvey(surveyForm);
            }
        });
    }
}
