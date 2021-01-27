package com.example.flappyw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TubeDrawingView extends View {

    private final int paintColor = Color.BLACK;
    private Paint drawPaint;
    float pointX;
    float pointY;
    float startX;
    float startY;
    float endX;

    public TubeDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
    }

    private void setupPaint() {
// Setup paint with color and stroke styles
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointX = event.getX();
        pointY = 0;
// Checks for the event that occurs
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = pointX;
                startY = pointY;
                return true;
            case MotionEvent.ACTION_UP:
                if(startX==0) {
                    endX = 0;
                }
                else{
                    endX = pointX;
                }
            case MotionEvent.ACTION_MOVE:
                break;
            default:
                return false;
        }
// Force a view to draw again
        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
            canvas.drawRect(pointX, pointY, pointX + (int) ((200.0/1080.0)*((3.0*Constants.TUBE_HEIGHT)/(2*Constants.SCREEN_HEIGHT))*Constants.SCREEN_WIDTH), (int) Constants.TUBE_HEIGHT, drawPaint);
    }

    public float returnXCoordinate(){
        return endX;
    }

}
