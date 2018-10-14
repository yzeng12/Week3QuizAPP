package com.example.yzeng.Week3AssignYixin.data.source;

import android.content.Context;

import com.example.yzeng.Week3AssignYixin.data.source.local.DataSourceDAO;

public class TaskRepository implements TaskDataSource {

    TaskDataSource dataSourceDao;

    public TaskRepository(Context context) {
        dataSourceDao = new DataSourceDAO(context);
    }

    @Override
    public void dbInitializer() {
        dataSourceDao.dbInitializer();
    }

    @Override
    public void getQuesAndAnsFromDB(TodoNoteCallBack callBack, int cursorPosition) {
        dataSourceDao.getQuesAndAnsFromDB(callBack, cursorPosition);
    }

}
