package com.example.flappyw;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.IOException;

public class GameOver extends Activity {
    int score;
    int highscore=0;
    private SharedPreferences myPreferences;
    private String sharedFile = "com.example.flappyw";
    String flappstring = Constants.flappstring;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_o);
        myPreferences = getSharedPreferences(sharedFile, MODE_PRIVATE);
        highscore = myPreferences.getInt("highscore",0);
        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        TextView scoreview = (TextView) findViewById(R.id.scorevalue);
        TextView highscoreview = (TextView) findViewById(R.id.highscorevalue);
        if(score >= highscore) {
        highscore = score;
        }
        if(score<10) {
            scoreview.setText(0+Integer.toString(score));
        }else {scoreview.setText(Integer.toString(score));}
        if(highscore<10){
            highscoreview.setText(0+Integer.toString(highscore));
        }else {highscoreview.setText(Integer.toString(highscore));}
        if(Constants.sondcheck == 1) {
            MediaPlayer mediaPlayer = new MediaPlayer();

            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(Constants.flappstring);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setVolume(50, 50);
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor prefEdit = myPreferences.edit();
        prefEdit.putInt("highscore", highscore);
        prefEdit.apply();
    }

    public void restartGame(View view) {
        try {
            StartGame.stopMediaPlayer();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Stop fehler");
        }
        Intent go = new Intent(GameOver.this, com.example.flappyw.StartGame.class);
        startActivity(go);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
        finish();
    }
    public void backtomain (View view){
        try {
            StartGame.stopMediaPlayer();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Stop fehler");
        }
        Intent backmain = new Intent(GameOver.this,MainActivity.class);
        startActivity(backmain);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backmain = new Intent(GameOver.this,MainActivity.class);
        startActivity(backmain);
        overridePendingTransition(R.transition.transition_quick,R.transition.transition_quick);
        finish();
    }
}
