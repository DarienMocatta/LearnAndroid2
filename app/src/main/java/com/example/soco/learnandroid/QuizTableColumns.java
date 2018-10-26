package com.example.soco.learnandroid;

import android.provider.BaseColumns;
//this class declares the columns required for SQLite database.
//Questions comprise of a Question, 3 Options, a corresponding correct answer and a difficulty.

public final class QuizTableColumns {

    private QuizTableColumns(){}

    public static class QuestionsTable implements BaseColumns{


        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "questions";
        public static final String COLUMN_MULTI_OPTION1 = "multiOption1";
        public static final String COLUMN_MULTI_OPTION2 = "multiOption2";
        public static final String COLUMN_MUTLI_OPTION3 = "multiOption3";
        public static final String COLUMN_ANSWER_NUMBER = "answer_number";
        public static final String COLUMN_DIFFICULTY = "difficulty";
    }
}
