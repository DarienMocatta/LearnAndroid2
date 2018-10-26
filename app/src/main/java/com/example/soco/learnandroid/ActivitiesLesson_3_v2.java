package com.example.soco.learnandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class ActivitiesLesson_3_v2 extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    //drawer layout, menu.xml and navigation view used to create nav bar.
    //a constraint layout used within drawer layout to ensure correct positioning of other components such as textviews, fragments and buttons,
    //variables required to enable navigation bar
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    //textview declaration, will be initialised to be scrollable
    TextView scrollTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_lesson_3_v2);

        YouTubePlayerSupportFragment frag =
                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
        frag.initialize(YouTubeConfiguration.getApiKey(), this);

        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //make textview scrollable and set text
        scrollTextView = (TextView) findViewById(R.id.textViewScroll);
        scrollTextView.setMovementMethod(new ScrollingMovementMethod());
        //String Source from Android - Broadcast Receivers @developer.android.com/reference/android/content/BroadcastReceiver
        //String stored in strings.xml - formatted via html and converted to string with following code.
        scrollTextView.setText(Html.fromHtml(getString(R.string.Lesson3_String)));




        //NAV Bar
        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if(id== R.id.item_Activities)
                {
                    Intent intent = new Intent(ActivitiesLesson_3_v2.this, ActivitiesLesson_1_v2.class);
                    startActivity(intent);
                }
                else if(id== R.id.item_Sevices)
                {
                    Intent intent = new Intent(ActivitiesLesson_3_v2.this, ActivitiesLesson_2_v2.class);
                    startActivity(intent);
                }
                else if(id== R.id.item_Broadcast_Recievers)
                {
                    Intent intent = new Intent(ActivitiesLesson_3_v2.this, ActivitiesLesson_3_v2.class);
                    startActivity(intent);
                }
                else if(id== R.id.item_Content_Providers)
                {
                    Intent intent = new Intent(ActivitiesLesson_3_v2.this, ActivitiesLesson_4_v2.class);
                    startActivity(intent);
                }
                else if(id== R.id.item_Activity_Lifecycle)
                {
                    Intent intent = new Intent(ActivitiesLesson_3_v2.this, ActivitiesLesson_5_v2.class);
                    startActivity(intent);
                }
                else if(id== R.id.item_Quiz)
                {
                    Intent intent = new Intent(ActivitiesLesson_3_v2.this, QuizHomePage.class);
                    startActivity(intent);
                }


                return true;
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    //“Working with Android Broadcast Receiver” – Prabeesh R K
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {

           youTubePlayer.loadVideo("Iauww6F0y5o");

    }}

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
