package com.hfad.survey;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

public class CreateFormActivity extends AppCompatActivity {

    private LinearLayout QuestionListLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*// prepare Add Question button for floating context menu
        Button addQuestionButton = (Button)findViewById(R.id.add_question_button);
        registerForContextMenu(addQuestionButton);*/

        QuestionListLinearLayout = (LinearLayout)findViewById(R.id.question_cards);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.save_form, menu);
        return true;
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

    public void onDeleteQuestion(View v) {
        // get the parent Cardview and its parent LinearLayout
        ViewGroup parentCardView = (ViewGroup)(v.getParent()).getParent();
        ViewGroup parentLinearLayout = (ViewGroup)parentCardView.getParent();

        // delete the parentCardview
        parentLinearLayout.removeView((View)parentCardView);
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
        if(item.getItemId() == R.id.save_form) {
            // TERRIFYING THINGS HAPPEN
        }

        return super.onOptionsItemSelected(item);
    }


}
