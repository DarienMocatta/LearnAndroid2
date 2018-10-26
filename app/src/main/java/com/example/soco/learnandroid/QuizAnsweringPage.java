package com.example.soco.learnandroid;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;


import java.util.Locale;

public class QuizAnsweringPage extends AppCompatActivity {

    //Quiz Page implemented with reference to “Quiz App with SQLite” – by Coding in Flow
    //Key implementation to maintain data persistency between intents such as high score from 'QuizAnsweringPage.java'
    //Tutorial also aided with the adding of a countdown timer and changing colours when questions answered correct/wrong
    //Tutorial also aided in maintaining a users quiz experience when flipping device between horizontal and vertical.

    //https://codinginflow.com/tutorials/android/quiz-app-with-sqlite

    //key-value storage like implementation in QuizHomepage. For parsing score intent that returns to QuizHomePage
    //and remembering score.
    public static final String EXTRA_SCORE = "extraScore";

    //  Is equal to 30 seconds on clock
    private static final long COUNTDOWN_AMOUNT_MILLSEC = 30000;

    //This key-value memory storage for when device is flipped between horizontal and vertical
    //To remember score, time left, the question that the user one, the answer the user had selected and
    //the randomised question list.
    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    //declaration of required views
    private TextView tvQuestion;
    private TextView tvCurrentScore;
    private TextView tvQuestionCount;
    private TextView tvDifficulty;
    private TextView tvCountdownTimer;
    private RadioGroup optionGroup;
    private RadioButton multiOption1;
    private RadioButton multiOption2;
    private RadioButton multiOption3;
    private Button btnNextQuestion;
    //variables to enables answers to change color if right or wrong.
    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;

    private CountDownTimer countDownClock;
    private long timeLeftMilliSec;

    private ArrayList<Question> questionList;
    private int questionTracker;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;

    private long backPressedTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_questioning);


        tvQuestion = findViewById(R.id.text_view_question);
        tvCurrentScore = findViewById(R.id.text_view_score);
        tvQuestionCount = findViewById(R.id.text_view_question_count);
        tvDifficulty = findViewById(R.id.text_view_difficulty);
        tvCountdownTimer = findViewById(R.id.text_view_countdown);
        optionGroup = findViewById(R.id.radio_group);
        multiOption1 = findViewById(R.id.radio_button1);
        multiOption2 = findViewById(R.id.radio_button2);
        multiOption3 = findViewById(R.id.radio_button3);
        btnNextQuestion = findViewById(R.id.button_confirm_next);

        textColorDefaultRb = multiOption1.getTextColors();
        textColorDefaultCd = tvCountdownTimer.getTextColors();

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(QuizHomePage.EXTRA_DIFFICULTY);

        tvDifficulty.setText("Difficulty: "+ difficulty);

        if (savedInstanceState == null) {
            QuizDBHandler dbHelper = new QuizDBHandler(this);
            questionList = dbHelper.getQuestions(difficulty);
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuestion();
        } else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionTracker = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionTracker - 1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftMilliSec = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if (!answered) {
                startCountDown();
            } else {
                updateCountDownText();
                showSolution();
            }
        }
        //radio button onclick listener.

        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (multiOption1.isChecked() || multiOption2.isChecked() || multiOption3.isChecked()) {
                        //compare selection with answer
                        checkAnswer();
                    } else {
                        //if not selection, prompt user to select and option
                        Toast.makeText(QuizAnsweringPage.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //once a selection has been made and moved on from answer checker, proceed to next question.
                    showNextQuestion();
                }
            }
        });
    }

    //Resets colour and selection of question, initiates a countdown and
    // adds a another question counter to keep track for display and
    // receives the next three options from question array.

    private void showNextQuestion() {
        // Returns text to the default black colour. Is called everytime a new question is presented.
        multiOption1.setTextColor(textColorDefaultRb);
        multiOption2.setTextColor(textColorDefaultRb);
        multiOption3.setTextColor(textColorDefaultRb);
        // Removes the selection from the previous question
        optionGroup.clearCheck();

        if (questionTracker < questionCountTotal) {
            currentQuestion = questionList.get(questionTracker);

            tvQuestion.setText(currentQuestion.getQuestion());
            multiOption1.setText(currentQuestion.getMultiOption1());
            multiOption2.setText(currentQuestion.getMultiOption2());
            multiOption3.setText(currentQuestion.getMultiOption3());

            questionTracker++;
            tvQuestionCount.setText("Question: " + questionTracker + "/" + questionCountTotal);
            answered = false;
            btnNextQuestion.setText("Confirm");
            //sets time left to 30 seconds
            //issue with display where even though seconds left, countown
            timeLeftMilliSec = COUNTDOWN_AMOUNT_MILLSEC;
            startCountDown();
        } else {
            //finish quiz when no questions left.
            finishQuiz();
        }
    }

    private void startCountDown() {
        //start new countdown, counting per 1 second.
        countDownClock = new CountDownTimer(timeLeftMilliSec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMilliSec = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                //when time runs out use users input as selected answer.
                timeLeftMilliSec = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftMilliSec / 1000) / 60;
        int seconds = (int) (timeLeftMilliSec / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        tvCountdownTimer.setText(timeFormatted);

        if (timeLeftMilliSec < 10000) {
            tvCountdownTimer.setTextColor(Color.RED);
        } else {
            tvCountdownTimer.setTextColor(textColorDefaultCd);
        }
    }

    private void checkAnswer() {
        //check an answer option has been selected
        answered = true;
        //stop count down
        countDownClock.cancel();

        RadioButton rbSelected = findViewById(optionGroup.getCheckedRadioButtonId());
        //Index starts at zero + 1 for correct entry.
        int answerNr = optionGroup.indexOfChild(rbSelected) + 1;
        //Checks hard coded attribute answer number matches with user input
        if (answerNr == currentQuestion.getAnswerNumber()) {
            score++;
            tvCurrentScore.setText("Score: " + score);
        }

        showSolution();
    }

    //By Default after a question is answered it will turn red. However special conditions for when question answered correctly
    //turning it green.
    //Uses Question object variable 'AnswerNumber', where the correct answer is specified 1, 2 or 3.
    //AnswerNumber in UI follows downwards linear order due to order of radio buttons.
    // This is intuitive for users.
    private void showSolution() {
        multiOption1.setTextColor(Color.RED);
        multiOption2.setTextColor(Color.RED);
        multiOption3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNumber()) {
            case 1:
                multiOption1.setTextColor(Color.GREEN);
                tvQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                multiOption2.setTextColor(Color.GREEN);
                tvQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                multiOption3.setTextColor(Color.GREEN);
                tvQuestion.setText("Answer 3 is correct");
                break;
        }

        if (questionTracker < questionCountTotal) {
            btnNextQuestion.setText("Next");
        } else {
            btnNextQuestion.setText("Finish");
        }
    }

    //shared preference memory of score than is parsed back through to QuizHomePage
    //Determination fo high score determined in homepage.
    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    //User must double tap back button to exit quiz. Avoid accidental taps ending quiz prematurely.
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    //Question ended when timer is 0. User can move onto next question.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownClock != null) {
            countDownClock.cancel();
        }
    }

    //When activity created/destroyed by flipping phone vertical/horizontal and maintains values as explained at top of page.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionTracker);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftMilliSec);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }
}
