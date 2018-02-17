package com.hfad.survey.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by jlanecki on 16.02.18.
 */

public class OnDeleteDialogFragment extends DialogFragment {

    public interface OnDeleteClickListener {
        void onDeleteClick();
    }

    OnDeleteClickListener listener;

    public static OnDeleteDialogFragment newInstance(int title, int yes, int no) {
        OnDeleteDialogFragment frag = new OnDeleteDialogFragment();
        Bundle args = new Bundle();
        args.putInt("title", title);
        args.putInt("yes", yes);
        args.putInt("no", no);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnDeleteClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnDeleteClickListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        int yes = getArguments().getInt("yes");
        int no = getArguments().getInt("no");

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        listener.onDeleteClick();
                    }
                })
                .setNegativeButton(no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                })
                .create();
    }

}
