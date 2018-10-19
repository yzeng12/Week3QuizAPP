package com.example.yzeng.Week3AssignYixin.data.source.RoomDBA;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insert(TodoNoteRoom todoNote);

    @Query("DELETE FROM Todo_table")
    void deleteAll();

    @Query("SELECT * from Todo_table ORDER BY question ASC")
    List<TodoNoteRoom> getAllWords();

    @Query("SELECT question from Todo_table ")
    //List<String> getQuestion();
    String[] getQuestion();

    @Query("SELECT answer1 from Todo_table")
    String[] getOption1();

    @Query("SELECT answer2 from Todo_table")
    String[] getOption2();

    @Query("SELECT answer3 from Todo_table")
    String[] getOption3();

    @Query("SELECT answer4 from Todo_table")
    String[] getOption4();
}