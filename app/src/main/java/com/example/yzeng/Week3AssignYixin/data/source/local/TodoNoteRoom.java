package com.example.yzeng.Week3AssignYixin.data.source.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Todo_table")
public class TodoNoteRoom {

    @PrimaryKey (autoGenerate=true)
    @NonNull
    @ColumnInfo(name = "ID")
    int id;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    @ColumnInfo(name = "question")
    String question;


    @NonNull
    @ColumnInfo(name = "answer1")
    String answer1;
    @NonNull
    @ColumnInfo(name = "answer2")
    String answer2;
    @NonNull
    @ColumnInfo(name = "answer3")
    String answer3;
    @NonNull
    @ColumnInfo(name = "answer4")
    String answer4;


    public TodoNoteRoom(String question, String answer1, String answer2, String answer3, String answer4) {
        this.question = question;

        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }
}
