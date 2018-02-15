package com.hfad.survey.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by jlanecki on 07.02.18.
 */

@Entity(tableName = "answers",
        foreignKeys = {
                @ForeignKey(entity = QuestionEntity.class,
                        parentColumns = "id",
                        childColumns = "question_id",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "question_id")
        })
public class AnswerEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "question_id")
    private long questionId;

    @ColumnInfo(name = "content")
    private String content;

    public AnswerEntity() {}

    public AnswerEntity(long questionId, String content) {
        this.questionId = questionId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
