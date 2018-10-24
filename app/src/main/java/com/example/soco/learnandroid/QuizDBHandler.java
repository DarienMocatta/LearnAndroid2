package com.example.soco.learnandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.soco.learnandroid.QuizTableInitialise.*;

import java.util.ArrayList;

public class QuizDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 3;

    private SQLiteDatabase db;

    public QuizDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("Which of the following are not a key component in Android",
                "Activities", "Content Providers", "Intents", 3, Question.DIFFICULTY_EASY);
        addQuestion(q1);
        Question q2 = new Question("_______is an application component that can perform long-running operations in the background, and it doesn't provide a user interface.",
                "Activities", "Services", "Content Providers", 2, Question.DIFFICULTY_EASY);
        addQuestion(q2);
        Question q3 = new Question("What method is called when initializing an Activity?",
                "OnCreate", "onStart", "onResume", 1, Question.DIFFICULTY_EASY);
        addQuestion(q3);
        Question q4 = new Question("Which of the following components is used to share data across applications?",
                "Services", "Content Providers", "Broadcast Receivers", 2, Question.DIFFICULTY_EASY);
        addQuestion(q4);
        Question q5 = new Question("Which method is called when shutting the activity down?",
                "onPause", "onStop", "onDestroy", 3, Question.DIFFICULTY_EASY);
        addQuestion(q5);
        Question q6 = new Question("Broadcast Receivers are best used for",
                "The system automatically sending messages when various system events occur", "Running multiple activities simultaneously in the background", "Accessing and sharing data across multiple applications.", 1, Question.DIFFICULTY_MEDIUM);
        addQuestion(q6);
        Question q7 = new Question("Once the onStop method is called, which method is required next to resume the activity?",
                "onStart", "onCreate", "onRestart", 3, Question.DIFFICULTY_MEDIUM);
        addQuestion(q7);
        Question q8 = new Question("Audio playing in the background is an example of which type of component",
                "Content Provider", "Services", "Broadcast Recievers", 2, Question.DIFFICULTY_MEDIUM);
        addQuestion(q8);
        Question q9 = new Question("The AbstractThreadedSyncAdapter, CursorAdapter and CursorLoader all rely on which component class?",
                "Activity", "Broadcast Receiver", "Content Provider", 3, Question.DIFFICULTY_MEDIUM);
        addQuestion(q9);
        Question q10 = new Question("Which of the following is not a type of Service?",
                "Foreground", "Middleground", "Background", 2, Question.DIFFICULTY_MEDIUM);
        addQuestion(q10);
        Question q11 = new Question("Activity class takes care of creating a window for you in which you can place your UI with ________",
                "setContentView(View)", "onCreate(Bundle savedInstanceState)", "super.onCreate(savedInstanceState);", 1, Question.DIFFICULTY_HARD);
        addQuestion(q11);
        Question q12 = new Question("The visible lifecycle of an activity occurs between which two methods?",
                "onStart(), onStop()", "onStart(), onPause()", "onCreate(), onStop", 1, Question.DIFFICULTY_HARD);
        addQuestion(q12);
        Question q13 = new Question("Placeholder",
                "A", "B", "C", 3, Question.DIFFICULTY_HARD);
        addQuestion(q13);
        Question q14 = new Question("Placeholder",
                "A", "B", "C", 1, Question.DIFFICULTY_HARD);
        addQuestion(q4);
        Question q15 = new Question("Placeholder",
                "A", "B", "C", 2, Question.DIFFICULTY_HARD);
        addQuestion(q15);
           }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}