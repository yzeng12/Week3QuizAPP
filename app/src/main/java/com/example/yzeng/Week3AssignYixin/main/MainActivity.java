package com.example.yzeng.Week3AssignYixin.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.yzeng.Week3AssignYixin.QuizQuestionFragment;
import com.example.yzeng.Week3AssignYixin.R;
import com.example.yzeng.Week3AssignYixin.data.TodoNote;
import com.example.yzeng.Week3AssignYixin.data.source.local.TodoDao;
import com.example.yzeng.Week3AssignYixin.data.source.local.TodoNoteRoom;
import com.example.yzeng.Week3AssignYixin.data.source.local.TodoRoomDataBase;
import com.example.yzeng.Week3AssignYixin.list.ListFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View, ListFragment.OnQuestionClickListener {

    TextView quizQuestion;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    MainPresenter presenter;
    public int cursorPosition = 0;
    private List<TodoNoteRoom> allTodoList;
    private TodoNoteRoom todoNoteRoom;
    //********
    TodoRoomDataBase db;
    private TodoDao mTodoDao;


    //*********
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        presenter.initializer();
        //initialize
        db = TodoRoomDataBase.getDatabase(this);
        mTodoDao = db.TodoDao();

        //funny question
        TodoNoteRoom todoNote = new TodoNoteRoom("Question1 who is not Android trainer", "Ansari", "Shiva",
                "abdul", "navneet");
        TodoNoteRoom todoNote1 = new TodoNoteRoom("Question2 which 3 training program is available at RJT", "android", "IOS",
                "java", "TensorFlow");
        TodoNoteRoom todoNote2 = new TodoNoteRoom("Question3 what is your favorite launch at RJT", "Chipotle", "Spaghetti",
                "Portillo's ", "PandaExpress");
        insert(todoNote);
        insert(todoNote1);
        insert(todoNote2);


        //
        quizQuestion = findViewById(R.id.questionTextView);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);

        // presenter.getquizQA(cursorPosition);


        //*****  get first question
//        Log.i("aaa", "got this 2");
//        Log.i("aaa", "got this one");

        getAsyncTask getTask = new getAsyncTask(mTodoDao);
        getTask.execute();


//        quizQuestion.setText();
//        checkBox1.setText();
//        checkBox2.setText();
//        checkBox3.setText();
//        checkBox4.setText();
        //****

    }

    @Override
    public void Showquiz(TodoNote todonote) {
        quizQuestion.setText(todonote.getQuestion());
        checkBox1.setText(todonote.getAnswer1());
        checkBox2.setText(todonote.getAnswer2());
        checkBox3.setText(todonote.getAnswer3());
        checkBox4.setText(todonote.getAnswer4());
    }

    @Override
    public void positionIncreaseComfirm() {
        cursorPosition++;
    }

    @Override
    public void positionDecreaseComfirm() {
        if (cursorPosition == 0) {
            return;
        }
        cursorPosition--;
    }

    public void buttonListener(View view) {
        switch (view.getId()) {
            case R.id.nextButton:
                presenter.positionIncrease();
                presenter.getquizQA(cursorPosition);
                break;
            case R.id.prevButton:
                presenter.positionDecrease();
                presenter.getquizQA(cursorPosition);
                break;
        }
    }

    @Override
    public void onQuestionClick(String question) {
        QuizQuestionFragment questionFragment =
                (QuizQuestionFragment) getFragmentManager().findFragmentById(R.id.fragment_quiz);
        questionFragment.updateQuestion(question);

    }

    private class getAsyncTask extends AsyncTask<Void, Void, Void> {

        private TodoDao mAsyncTaskDao;

        public getAsyncTask(TodoDao mTodoDao) {

            this.mAsyncTaskDao = mTodoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //List<String> question =mTodoDao.getQuestion();
            String question = mTodoDao.getQuestion();
            String option1 = mTodoDao.getOption1();
            String option2 = mTodoDao.getOption2();
            String option3 = mTodoDao.getOption3();
            String option4 = mTodoDao.getOption4();
            todoNoteRoom = new TodoNoteRoom(question, option1, option2, option3, option4);
            Log.i("aaa", question.toString());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            quizQuestion.setText(todoNoteRoom.getQuestion());
            checkBox1.setText(todoNoteRoom.getAnswer1());
            checkBox2.setText(todoNoteRoom.getAnswer2());
            checkBox3.setText(todoNoteRoom.getAnswer3());
            checkBox4.setText(todoNoteRoom.getAnswer4());


//            ArrayAdapter<Word> adapter = new ArrayAdapter<Word>(MainActivity.this,android.R.layout.simple_list_item_1,
//                    android.R.id.text1,allWordsList);
//            mlistView.setAdapter(adapter);

        }
    }


    public void insert(TodoNoteRoom todoNoteRoom) {
        new insertAsyncTask(mTodoDao).execute(todoNoteRoom);
    }

    private static class insertAsyncTask extends AsyncTask<TodoNoteRoom, Void, Void> {

        private TodoDao mAsyncTaskDao;

        insertAsyncTask(TodoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final TodoNoteRoom... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
