package com.example.yzeng.Week3AssignYixin.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.yzeng.Week3AssignYixin.ListFragment;
import com.example.yzeng.Week3AssignYixin.QuizQuestionFragment;
import com.example.yzeng.Week3AssignYixin.R;
import com.example.yzeng.Week3AssignYixin.data.TodoNote;

public class MainActivity extends AppCompatActivity implements MainContract.View,ListFragment.OnQuestionClickListener {

    TextView quizQuestion;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    MainPresenter presenter;
    public int cursorPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        presenter.initializer();

        quizQuestion = findViewById(R.id.questionTextView);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);

        presenter.getquizQA(cursorPosition);

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
                presenter.getquizQA(cursorPosition );
                break;
        }
    }

    @Override
    public void onQuestionClick(String question) {
        QuizQuestionFragment questionFragment =
                (QuizQuestionFragment) getFragmentManager().findFragmentById(R.id.fragment_quiz);
        questionFragment.updateQuestion(question);

    }
}
