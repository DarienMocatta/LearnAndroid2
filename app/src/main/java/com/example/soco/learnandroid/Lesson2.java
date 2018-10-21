package com.example.soco.learnandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Lesson2 extends AppCompatActivity {

    private static final String TAG = "Lesson2";

    YouTubePlayerView mYouTubePlayerView2;
    Button btnPlay2;
    YouTubePlayer.OnInitializedListener mOnInitializedListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson2);
        Log.d(TAG, "on Create: Starting");
        btnPlay2 = (Button) findViewById(R.id.btnPlay);
        mYouTubePlayerView2 = (YouTubePlayerView) findViewById(R.id.youtubePlay);

        mOnInitializedListener2 = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "on Create: Done initialising.");
                //youTubePlayer.loadVideo("2duc77RHqw");
                youTubePlayer.loadVideo("7nxOTIBZ7Mw");
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "on Create: Failed to initialize.");
            }
        };
        btnPlay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on Create: Initialising Youtube Player.");
                mYouTubePlayerView2.initialize(YouTubeConfiguration.getApiKey(), mOnInitializedListener2);

            }
        });


    }
}
