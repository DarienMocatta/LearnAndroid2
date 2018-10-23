package com.example.soco.learnandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ActivitiesLesson extends YouTubeBaseActivity {




    private static final String TAG = "ActivitiesLesson";

    YouTubePlayerView mYouTubePlayerView;
    Button btnPlay;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    TextView scrollTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_lesson);



        Log.d(TAG, "on Create: Starting");
        btnPlay = (Button) findViewById(R.id.btnPlay);
        mYouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlay);

        //make textview scrollable and set text
       scrollTextView = (TextView) findViewById(R.id.textViewScroll);
       scrollTextView.setMovementMethod(new ScrollingMovementMethod());
       scrollTextView.setText(Html.fromHtml(getString(R.string.Lesson1_String)));

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "on Create: Done initialising.");
                //youTubePlayer.loadVideo("2duc77RHqw");
                youTubePlayer.loadVideo("ibti6yg_NCc");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "on Create: Failed to initialize.");
            }
        };
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on Create: Initialising Youtube Player.");
                mYouTubePlayerView.initialize(YouTubeConfiguration.getApiKey(), mOnInitializedListener);

            }
        });


    }
}

