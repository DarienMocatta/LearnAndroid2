package com.example.soco.learnandroid;

import android.os.Parcel;
import android.os.Parcelable;

//
public class Question implements Parcelable {
    public static final String DIFFICULTY_EASY = "Easy";
    public static final String DIFFICULTY_MEDIUM = "Medium";
    public static final String DIFFICULTY_HARD = "Hard";

    private String question;
    private String multiOption1;
    private String multiOption2;
    private String multiOption3;
    private int answerNumber;
    private String difficulty;

    public Question() {
    }

    public Question(String question, String option1, String option2, String option3, int answerNr, String difficulty) {
        this.question = question;
        this.multiOption1 = option1;
        this.multiOption2 = option2;
        this.multiOption3 = option3;
        this.answerNumber = answerNr;
        this.difficulty = difficulty;
    }

    protected Question(Parcel in) {
        question = in.readString();
        multiOption1 = in.readString();
        multiOption2 = in.readString();
        multiOption3 = in.readString();
        answerNumber = in.readInt();
        difficulty = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(multiOption1);
        dest.writeString(multiOption2);
        dest.writeString(multiOption3);
        dest.writeInt(answerNumber);
        dest.writeString(difficulty);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getMultiOption1() {
        return multiOption1;
    }

    public void setMultiOption1(String multiOption1) {
        this.multiOption1 = multiOption1;
    }

    public String getMultiOption2() {
        return multiOption2;
    }

    public void setMultiOption2(String multiOption2) {
        this.multiOption2 = multiOption2;
    }

    public String getMultiOption3() {
        return multiOption3;
    }

    public void setMultiOption3(String multiOption3) {
        this.multiOption3 = multiOption3;
    }

    public int getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(int answerNumber) {
        this.answerNumber = answerNumber;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public static String [] getAllDifficultyLevels(){
        return new String[]{
                DIFFICULTY_EASY, DIFFICULTY_MEDIUM, DIFFICULTY_HARD
        };
    }
    }
