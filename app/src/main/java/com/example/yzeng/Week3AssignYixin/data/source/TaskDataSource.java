package com.example.yzeng.Week3AssignYixin.data.source;

import com.example.yzeng.Week3AssignYixin.data.TodoNote;

public interface TaskDataSource {
    void dbInitializer();

    void getQuesAndAnsFromDB(TodoNoteCallBack callBack, int cursorPosition);

    interface TodoNoteCallBack{
        void ShowQuiz(TodoNote todonote);

        void positionDecrease();
    }
}
