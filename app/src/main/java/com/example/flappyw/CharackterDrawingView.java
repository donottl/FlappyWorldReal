package com.example.flappyw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CharackterDrawingView extends View {

    private final int paintColor = Color.BLACK;
    private Paint drawPaint;
    float pointX;
    float pointY;
    float startX;
    float startY;
    float endX;
    float endY;

    public CharackterDrawingView(Context context, AttributeSet attrs) {
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
        pointY = event.getY();
// Checks for the event that occurs
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = pointX;
                startY = pointY;
                return true;
            case MotionEvent.ACTION_UP:
                if (startX == 0 && startY == 0) {
                    endX = 0;
                    endY = 0;
                } else {
                    endX = pointX;
                    endY = pointY;
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
        canvas.drawRect(pointX, pointY, pointX + 200*Constants.SCREEN_WIDTH/1080, pointY + 200*Constants.SCREEN_WIDTH/1080, drawPaint);
    }

    public float returnXCoordinate() {
        return endX;
    }

    public float returnYCoordinate() {
        return endY;
    }
}

