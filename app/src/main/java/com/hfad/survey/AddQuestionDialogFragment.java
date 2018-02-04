package com.hfad.survey;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ListAdapter;
import android.widget.Toast;

/**
 * Created by jlanecki on 03.02.18.
 */

public class AddQuestionDialogFragment extends DialogFragment {

    String option1 = getResources().getString(R.string.multiple_choice);
    String option2 = getResources().getString(R.string.checkboxes);
    String option3 = getResources().getString(R.string.dropdown);

    final String [] items = new String[] {option1, option2, option3};
    final Integer[] icons = new Integer[] {R.drawable.ic_radio_button_checked_black_24dp,
            R.drawable.ic_check_box_black_24dp, R.drawable.ic_drop_down_circle_black_24dp};
    ListAdapter adapter = new ArrayAdapterWithIcon(getActivity(), items, icons);


    public static AddQuestionDialogFragment newInstance(int title) {
        AddQuestionDialogFragment frag = new AddQuestionDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                Toast.makeText(getActivity(), "Item Selected: " + item, Toast.LENGTH_SHORT).show();
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
