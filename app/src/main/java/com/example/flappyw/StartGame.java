package com.example.flappyw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileNotFoundException;

public class StartGame extends Activity {
    GameView gameView;

    static public Bitmap Tube;
    static public Bitmap Charackter;
    static public Bitmap Background;
    RelativeLayout game;
    static MediaPlayer mediaPlayer;
    ImageView BackgroundView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(Constants.NoSong==0) {
            try {
                mediaPlayer = MediaPlayer.create(this, Constants.LiveSong);
                mediaPlayer.start();

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("MusicFehler");
            }
        }

        Intent intent = getIntent();
        checkIntents();

        gameView = new GameView(this, null);
        setContentView(R.layout.game_view);

        game = (RelativeLayout) findViewById(R.id.gameviewLayout);
        BackgroundView = (ImageView) findViewById(R.id.main_backgroundImage);
        setBackgroundPic();



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {
            mediaPlayer.stop();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            System.out.println("BackmeinErrorStartGame");
        }
        Intent backmain = new Intent(StartGame.this,MainActivity.class);
        startActivity(backmain);
        finish();
    }

    public static Bitmap returnTube(){
        return Tube;
    }

    public static Bitmap returnCharackter(){
        return Charackter;
    }

    public void setBackgroundPic(){
        BitmapDrawable ob = new BitmapDrawable(getResources(), Background);
        try {
           // game.setBackground(ob);
            BackgroundView.setImageBitmap(Background);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("OK");
            game.setBackgroundResource(R.drawable.background_final);
        }


    }

    public void checkIntents(){
        if(getIntent() != null){
            try {
              // Bitmap Tube1 = BitmapFactory.decodeStream(this.openFileInput("Tube"));
               Tube =  BitmapFactory.decodeStream(this.openFileInput("Tube"));
               // Tube = Bitmap.createScaledBitmap(Tube1,  (int) ((200.0/1080.0)*Constants.SCREEN_WIDTH), (int) ((1000.0/2.0)*Constants.SCREEN_WIDTH), true);
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
                System.out.println("Error Charackter main");
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
        if(Constants.CheckCodeTube==0){
            Tube=Constants.DefaultTube;
        }
        if(Constants.CheckCodeCharackter==0){
            Charackter=Constants.DefaultCharackter;
        }
        if(Constants.CheckCodeBackground==0){
            Background=Constants.DefaultBackground;
        }
    }

    public static void stopMediaPlayer(){
        if(mediaPlayer!=null) {
            if (mediaPlayer.isPlaying() == true) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
    }

}

