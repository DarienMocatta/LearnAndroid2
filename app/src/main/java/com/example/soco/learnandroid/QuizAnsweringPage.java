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
    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_AMOUNT_MILLSEC = 30000;

    //
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

    private CountDownTimer countDownTimer;
    private long timeLeftMilliSec;

    private ArrayList<Question> questionList;
    private int questionTracker;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;

    private long backPressedTime;


    //Navigation View Variables
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

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

        btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answered) {
                    if (multiOption1.isChecked() || multiOption2.isChecked() || multiOption3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(QuizAnsweringPage.this, "Please select an answer", Toast.LENGTH_SHORT).show();
                    }
                } else {
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
            finishQuiz();
        }
    }

    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftMilliSec, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMilliSec = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
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
        answered = true;

        countDownTimer.cancel();

        RadioButton rbSelected = findViewById(optionGroup.getCheckedRadioButtonId());
        int answerNr = optionGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNumber()) {
            score++;
            tvCurrentScore.setText("Score: " + score);
        }

        showSolution();
    }

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

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

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
