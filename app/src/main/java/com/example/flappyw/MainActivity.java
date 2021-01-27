package com.example.flappyw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {
    Button Startbutton;
    View highscoreview;
    private SharedPreferences myPreferences;
    int highscore;
    private String sharedFile = "com.example.flappyw";

    Bitmap Tube;
    Bitmap Charackter;
    Bitmap Background;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.SCREEN_HEIGHT= displayMetrics.heightPixels;
        Constants.SCREEN_WIDTH= displayMetrics.widthPixels;
        Constants.SCREEN_HEIGHT_2=Constants.SCREEN_HEIGHT/2;
        Constants.SCREEN_WIDTH_2=Constants.SCREEN_WIDTH/2;

        Intent intent = getIntent();

        checkIntents();
        initDefaultBitmaps();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1024);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 10);
        }


        myPreferences = getSharedPreferences(sharedFile, MODE_PRIVATE);
        highscore = myPreferences.getInt("highscore",0);

      TextView highscoreview = (TextView) findViewById(R.id.mainhigh);
        highscoreview.setText("Highscore: " + Integer.toString(highscore));



/*        Startbutton = (Button)findViewById(R.id.buttonstart);
        Startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Spielstart = new Intent(MainActivity.this,GameView.class);
                startActivity(Spielstart);
                setContentView(R.layout.game_view);

            }
        });*/
        Button clickButton = (Button) findViewById(R.id.CreateButton);
        if (clickButton != null) {
            clickButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    startCreateActivity();
                }
            });
        }


    }




    public void startGame(View view) {
        Intent go = new Intent(MainActivity.this, com.example.flappyw.StartGame.class);
        go.putExtra("KEY", "Tube");
        go.putExtra("KEY2","Charackter");
        go.putExtra("KEY4","Background");
        startActivity(go);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);

        this.finish();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*Intent backmain = new Intent(MainActivity.this,MainActivity.class);
        startActivity(backmain);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
        finish();*/
        onPause();
    }

    public void startSound(View view) {
        Intent sound = new Intent(MainActivity.this,Soundrecorder.class);
        startActivity(sound);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
        finish();
    }

    public void startCreateActivity(){
        Intent tube = new Intent(this, CreateActivity.class);
        startActivity(tube);
        this.finish();
    }

    public void initDefaultBitmaps(){
        Constants.DefaultCharackter = BitmapFactory.decodeResource(this.getResources(), R.drawable.flappyfinal);
        Constants.DefaultBackground = BitmapFactory.decodeResource(this.getResources(), R.drawable.background_final);
        Constants.DefaultTube = BitmapFactory.decodeResource(this.getResources(), R.drawable.t3);
    }

   public void checkIntents(){
        if(getIntent() != null){
            try {
                Tube = BitmapFactory.decodeStream(this.openFileInput("Tube"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Error Tube main");
            }
        }
        if(getIntent() != null){

            try {
                Charackter = BitmapFactory.decodeStream(this.openFileInput("Charackter"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Keine Charackter");
            }
        }
        if(getIntent() != null){

            try {
                Background = BitmapFactory.decodeStream(this.openFileInput("Background"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("Keine Background");
            }
        }
    }
}
