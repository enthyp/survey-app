package com.hfad.survey;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hfad.survey.db.entity.SurveyEntity;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by jlanecki on 05.02.18.
 */

class FormRecyclerAdapter extends RecyclerView.Adapter<FormRecyclerAdapter.FormViewHolder> {

    private List<SurveyEntity> forms;
    private View.OnClickListener clickListener;
    private Context context;

    public FormRecyclerAdapter(List<SurveyEntity> forms, View.OnClickListener clickListener, Context context) {
        this.forms = forms;
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public FormViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_card, parent, false);
        FormViewHolder fv = new FormViewHolder(v);

        return fv;
    }

    @Override
    public void onBindViewHolder(FormViewHolder holder, int position) {
        SurveyEntity surveyEntity = forms.get(position);
        holder.formTitle.setText(surveyEntity.getSurveyTitle());

        Format formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String date = formatter.format(surveyEntity.getSurveyDate());
        date = context.getResources().getString(R.string.addition_date) + date;
        holder.formDate.setText(date);

        holder.openSurveyButton.setTag(surveyEntity);
        holder.openSurveyButton.setOnClickListener(clickListener);

        holder.deleteButton.setTag(surveyEntity);
        holder.deleteButton.setOnClickListener(clickListener);
    }

    @Override
    public int getItemCount() {
        return forms.size();
    }

    public void addItems(List<SurveyEntity> surveyEntityList) {
        this.forms = surveyEntityList;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class FormViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView formTitle;
        TextView formDate;
        Button openSurveyButton;
        ImageButton deleteButton;

        FormViewHolder(View itemView) {
            super(itemView);

            cv = itemView.findViewById(R.id.cv);
            formDate = itemView.findViewById(R.id.form_date);
            formTitle = itemView.findViewById(R.id.form_title);
            openSurveyButton = itemView.findViewById(R.id.open_survey_button);
            deleteButton = itemView.findViewById(R.id.delete_form_button);
        }
    }
}
