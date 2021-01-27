package com.example.flappyw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.Future;
import java.util.logging.LogRecord;

public class GameView extends View {
    private Bird bird;
     Bitmap birdtest;
     private android.os.Handler handler;
     private Runnable runnable;
     private ArrayList<Pipe> arrayPipes;
     private int sumpipe, distance;
     Rect playerrect;
     public boolean gamerun =true;
     private boolean startgame = false;
     int score = 0;
     Bitmap Tube;
     Bitmap Charackter;








    public GameView(Context context,@Nullable AttributeSet attrs) {
        super(context, attrs);
        try {
            Tube = StartGame.returnTube();
            Charackter = StartGame.returnCharackter();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Tube & Char import fail");
        }

        initBird();
        initPipe();




        handler= new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (gamerun==true) {
                            invalidate();
                }else {handler.removeCallbacks(runnable);

                    score = score/2;
                    Thread.currentThread().interrupt();
                    Intent gameover = new Intent(getContext(),GameOver.class);
                    gameover.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    gameover.putExtra("score", score);
                    getContext().startActivity(gameover);

                }

            }

        };

    }

    private void initPipe() {
        sumpipe = 6;
        distance = 500 * Constants.SCREEN_HEIGHT / 1920;
        arrayPipes = new ArrayList<>();
        for (int i = 0; i < sumpipe; i++) {
            if (i < sumpipe / 2) {
                this.arrayPipes.add(new Pipe(Constants.SCREEN_WIDTH+i*((Constants.SCREEN_WIDTH+200*Constants.SCREEN_WIDTH/1080+400)/(sumpipe/2)),
                                           0, 200*Constants.SCREEN_WIDTH/1080,2*Constants.SCREEN_HEIGHT/3));
                if (Tube == null) {
                    this.arrayPipes.get(this.arrayPipes.size() - 1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.t3));
                } else {
                    this.arrayPipes.get(this.arrayPipes.size() - 1).setBm(Tube);
                }
                this.arrayPipes.get(this.arrayPipes.size()-1).randomY();
            }else {
                this.arrayPipes.add(new Pipe(this.arrayPipes.get(i-sumpipe/2).getX(),this.arrayPipes.get(i-sumpipe/2).getY()+this.arrayPipes.get(i-sumpipe/2).getHeight()+this.distance,
                                        200*Constants.SCREEN_WIDTH/1080,2*Constants.SCREEN_HEIGHT/3));
                if (Tube == null) {
                    this.arrayPipes.get(this.arrayPipes.size() - 1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.t3));
                } else {
                    this.arrayPipes.get(this.arrayPipes.size() - 1).setBm(Tube);
                }
            }

        }
    }
    private void initBird() {
        bird = new Bird();
        bird.setWidth(120*Constants.SCREEN_WIDTH/1080);
        bird.setHeight(100*Constants.SCREEN_HEIGHT/1920);
        bird.setY(Constants.SCREEN_HEIGHT/2-bird.getHeight());
        bird.setX(100*Constants.SCREEN_WIDTH/1080);
        if(Charackter==null) {
            birdtest = BitmapFactory.decodeResource(getResources(), R.drawable.flappyfinal);
        }
        else{
            birdtest = Charackter;
        }
        bird.setBirdbm(birdtest);

    }


    public void draw(Canvas canvas) {
        super.draw(canvas);

            if (startgame == false){
                bird.setDrop(0);
                bird.draw(canvas);
            }


            if (gamerun == true && startgame == true) {
                bird.draw(canvas);
                playerrect = bird.getRect();
                Paint paint = new Paint();
                paint.setARGB(0, 0, 0, 0);


                canvas.drawRect(playerrect, paint);
                bird.draw(canvas);

                for (int i = 0; i < sumpipe; i++) {
                    if (this.arrayPipes.get(i).getX() < -arrayPipes.get(i).getWidth()) {
                        this.arrayPipes.get(i).setX(Constants.SCREEN_WIDTH + 400);
                        if (i < sumpipe / 2) {
                            arrayPipes.get(i).randomY();
                        } else {
                            arrayPipes.get(i).setY(this.arrayPipes.get(i - sumpipe / 2).getY() + this.arrayPipes.get(i - sumpipe / 2).getHeight() + this.distance);
                        }
                    }
                    this.arrayPipes.get(i).draw(canvas);
                    canvas.drawRect(arrayPipes.get(i).getRect(), paint);
                    canvas.drawRect(playerrect, paint);


                    handler.postDelayed(runnable, 20);
                    for (int b = 0; b < sumpipe; b++) {
                        collision(playerrect, arrayPipes.get(b).getRect());


                    }if(bird.getX() == arrayPipes.get(i).getX()){
                        score++;
                    }
                }

                System.out.println("Punkte:" + score/2);


            }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN){

            bird.setDrop(-15);
            if(startgame==false) {
                invalidate();

                startgame = true;
            }


        }
        return true;
    }



    public boolean collision(Rect a,Rect b ){
        if(a.intersect(b) || bird.getY() < 0 || bird.getY() >= Constants.SCREEN_HEIGHT - bird.getHeight()) {
            gamerun = false;
        }

        return gamerun;
    }




}
