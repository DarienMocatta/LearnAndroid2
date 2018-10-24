package com.example.soco.learnandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    TextView introText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //Set Intro Text:
        introText = (TextView) findViewById(R.id.introText) ;
        introText.setText(Html.fromHtml(getString(R.string.Intro_Sring)));


        //navigation
        dl = (DrawerLayout)findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this,dl,R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);

        dl.addDrawerListener(abdt);
        abdt.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        NavigationView nav_view = (NavigationView)findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if(id== R.id.item_Activities)
                {
                    Intent intent = new Intent(MainActivity.this, ActivitiesLesson_1_v2.class);
                    startActivity(intent);
                }
                else if(id== R.id.item_Sevices)
                {
                    Intent intent = new Intent(MainActivity.this, ActivitiesLesson_2_v2.class);
                    startActivity(intent);
                }
                else if(id== R.id.item_Broadcast_Recievers)
                {
                    Intent intent = new Intent(MainActivity.this, ActivitiesLesson_3_v2.class);
                    startActivity(intent);
                }
                else if(id== R.id.item_Content_Providers)
                {
                    Intent intent = new Intent(MainActivity.this, ActivitiesLesson_4_v2.class);
                    startActivity(intent);
                }
                else if(id== R.id.item_Activity_Lifecycle)
                {
                    Intent intent = new Intent(MainActivity.this, ActivitiesLesson_5_v2.class);
                    startActivity(intent);
                }
                else if(id== R.id.item_Quiz)
                {
                    Intent intent = new Intent(MainActivity.this, QuizInitialise.class);
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
}
