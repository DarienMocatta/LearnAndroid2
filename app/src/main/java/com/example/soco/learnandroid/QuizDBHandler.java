package com.example.soco.learnandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.soco.learnandroid.QuizTableColumns.*;

import java.util.ArrayList;

//class to create sqlite database.
//SQLite Database implemented teachings from “Quiz App with SQLite” – by Coding in Flow
// https://codinginflow.com/tutorials/android/quiz-app-with-sqlite
public class QuizDBHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LearnAndroidQuiz.db";
    private static final int DATABASE_VERSION = 1;

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
                QuestionsTable.COLUMN_MULTI_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_MULTI_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_MUTLI_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NUMBER + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";

        //initialises table
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        insertQuestions();
    }

    //To avoid errors when alterations made to database. Can change version variable at top and the database will fix itself.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    //creates Question objects will are serialised via "Parceable" which enables the question data to be passed between activities
    private void insertQuestions() {
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
        Question q13 = new Question("A ________ service offers a client-server interface that allows components to interact with the service, send requests, receive results, and even do so across processes with interprocess communication (IPC).",
                "Foreground", "Background", "Bound", 3, Question.DIFFICULTY_HARD);
        addQuestion(q13);
        Question q14 = new Question("How are Activites in a system managed?",
                "Activity Stack", "Activity Organiser", "Preference log", 1, Question.DIFFICULTY_HARD);
        addQuestion(q4);
        Question q15 = new Question("The foreground lifecycle of an activity occurs between which two commands?",
                "onResume(), onStop()", "onResume(), onPause()", "onStart(), onResume()", 2, Question.DIFFICULTY_HARD);
        addQuestion(q15);
           }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_MULTI_OPTION1, question.getMultiOption1());
        cv.put(QuestionsTable.COLUMN_MULTI_OPTION2, question.getMultiOption2());
        cv.put(QuestionsTable.COLUMN_MUTLI_OPTION3, question.getMultiOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NUMBER, question.getAnswerNumber());
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
                question.setMultiOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_MULTI_OPTION1)));
                question.setMultiOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_MULTI_OPTION2)));
                question.setMultiOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_MUTLI_OPTION3)));
                question.setAnswerNumber(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NUMBER)));
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
                question.setMultiOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_MULTI_OPTION1)));
                question.setMultiOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_MULTI_OPTION2)));
                question.setMultiOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_MUTLI_OPTION3)));
                question.setAnswerNumber(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NUMBER)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}