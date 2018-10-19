package com.example.yzeng.Week3AssignYixin.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yzeng.Week3AssignYixin.QuizQuestionFragment;
import com.example.yzeng.Week3AssignYixin.R;
import com.example.yzeng.Week3AssignYixin.data.TodoNote;
import com.example.yzeng.Week3AssignYixin.data.source.RoomDBA.TodoDao;
import com.example.yzeng.Week3AssignYixin.data.source.RoomDBA.TodoNoteRoom;
import com.example.yzeng.Week3AssignYixin.data.source.RoomDBA.TodoRoomDataBase;
import com.example.yzeng.Week3AssignYixin.list.ListFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainContract.View, ListFragment.OnQuestionClickListener {

    TextView quizQuestion;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4;
    MainPresenter presenter;
    public int cursorPosition = 0;
    List<TodoNoteRoom> allTodoList;
    TodoNoteRoom[] todoNoteRoom = new TodoNoteRoom[3];
    static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;

    private AddressResultReceiver mResultReceiver;
    public String mLatitudeText = "";
    public String mLongitudeText = "";
    boolean mAddressRequested;
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
        mResultReceiver = new AddressResultReceiver(new Handler());
        //initialize
        db = TodoRoomDataBase.getDatabase(this);
        mTodoDao = db.TodoDao();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        quizQuestion = findViewById(R.id.questionTextView);
        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

    @Override
    public void onStart() {
        super.onStart();


//        Log.i("aaaa", mLatitudeText + " " + mLongitudeText);


        //

        // presenter.getquizQA(cursorPosition);
        //*****  get first question
//        Log.i("aaa", "got this 2");

//        quizQuestion.setText();
//        checkBox1.setText();
//        checkBox2.setText();
//        checkBox3.setText();
//        checkBox4.setText();
        //****

    }

    @Override
    protected void onResume() {
        super.onResume();


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
            String[] question = mTodoDao.getQuestion();
            String[] option1 = mTodoDao.getOption1();
            String[] option2 = mTodoDao.getOption2();
            String[] option3 = mTodoDao.getOption3();
            String[] option4 = mTodoDao.getOption4();
            //todoNoteRoom = new TodoNoteRoom(question, option1, option2, option3, option4);

            for (int i = 0; i < 3; i++) {
                todoNoteRoom[i] = new TodoNoteRoom(question[i], option1[i], option2[i], option3[i], option4[i]);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            quizQuestion.setText(todoNoteRoom[0].getQuestion());
            checkBox1.setText(todoNoteRoom[0].getAnswer1());
            checkBox2.setText(todoNoteRoom[0].getAnswer2());
            checkBox3.setText(todoNoteRoom[0].getAnswer3());
            checkBox4.setText(todoNoteRoom[0].getAnswer4());


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

    public void buttonListener2(View view) {
        switch (view.getId()) {
            case R.id.nextButton:
                if (cursorPosition == 2) {
                    return;
                }
                cursorPosition++;
                quizQuestion.setText(todoNoteRoom[cursorPosition].getQuestion());
                checkBox1.setText(todoNoteRoom[cursorPosition].getAnswer1());
                checkBox2.setText(todoNoteRoom[cursorPosition].getAnswer2());
                checkBox3.setText(todoNoteRoom[cursorPosition].getAnswer3());
                checkBox4.setText(todoNoteRoom[cursorPosition].getAnswer4());
                break;
            case R.id.prevButton:
                if (cursorPosition == 0) {
                    return;
                }
                cursorPosition--;
                quizQuestion.setText(todoNoteRoom[cursorPosition].getQuestion());
                checkBox1.setText(todoNoteRoom[cursorPosition].getAnswer1());
                checkBox2.setText(todoNoteRoom[cursorPosition].getAnswer2());
                checkBox3.setText(todoNoteRoom[cursorPosition].getAnswer3());
                checkBox4.setText(todoNoteRoom[cursorPosition].getAnswer4());
                break;
        }
    }

    public boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request permission
                    startLocationPermissionRequest();
                }
            };

        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    public void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }


    public void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            requestPermissions();
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                        .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                            mLatitudeText = String.format(Locale.ENGLISH, "%s: %f", "Latitude", mLastLocation.getLatitude());
                            mLongitudeText = String.format(Locale.ENGLISH, "%s: %f", "Longitude", mLastLocation.getLongitude());
                            Log.i("aaaaa", mLatitudeText + " " + mLongitudeText);

                            Intent intent = new Intent(MainActivity.this, FetchAddressIntentService.class);

                            // Pass the result receiver as an extra to the service.
                            intent.putExtra(Constants.RECEIVER, mResultReceiver);

                            // Pass the location data as an extra to the service.
                            intent.putExtra(Constants.LOCATION_DATA_EXTRA, mLastLocation);

                            // Start the service. If the service isn't already running, it is instantiated and started
                            // (creating a process for it if needed); if it is running then it remains running. The
                            // service kills itself automatically once all intents are processed.
                            startService(intent);




                        } else {
                            Log.w("aaa", "getLastLocation:exception", task.getException());
                            //showSnackbar(getString(R.string.no_location_detected));
                        }
                    }
                });
    }

    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        /**
         *  Receives data sent from FetchAddressIntentService and updates the UI in MainActivity.
         */
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string or an error message sent from the intent service.
            String  mAddressOutput = resultData.getString(Constants.RESULT_DATA_KEY);

            Log.i("aaa",mAddressOutput);
            TodoNoteRoom todoNote = new TodoNoteRoom("Question1 who is not Android trainer", "Ansari", "Shiva",
                    "abdul", "navneet");
            TodoNoteRoom todoNote1 = new TodoNoteRoom("Question2 which 3 training program is available at RJT", "android", "IOS",
                    "java", "TensorFlow");
            TodoNoteRoom todoNote2 = new TodoNoteRoom("Question3 what is your favorite launch at RJT", "Chipotle", mLongitudeText,
                    mAddressOutput , mLatitudeText);

            insert(todoNote);
            insert(todoNote1);
            insert(todoNote2);
            getAsyncTask getTask = new getAsyncTask(mTodoDao);
            getTask.execute();
            // mLocationAddressTextView.setText(mAddressOutput);

            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {
                Toast.makeText(MainActivity.this, "SUCCESS_RESULT", Toast.LENGTH_SHORT).show();
            }

            // Reset. Enable the Fetch Address button and stop showing the progress bar.
             mAddressRequested = false;

        }
    }


}
