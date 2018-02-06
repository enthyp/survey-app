package com.hfad.survey;

import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hfad.survey.db.entity.SurveyEntity;

import org.w3c.dom.Text;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlanecki on 05.02.18.
 */

class FormRecyclerAdapter extends RecyclerView.Adapter<FormRecyclerAdapter.FormViewHolder> {

    private List<SurveyEntity> forms;

    public FormRecyclerAdapter(List<SurveyEntity> forms) {
        this.forms = forms;
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

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = formatter.format(surveyEntity.getSurveyDate());
        holder.formDate.setText(date);
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

        FormViewHolder(View itemView) {
            super(itemView);

            cv = itemView.findViewById(R.id.cv);
            formDate = itemView.findViewById(R.id.form_date);
            formTitle = itemView.findViewById(R.id.form_title);
        }
    }
}
