package com.example.flappyw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Bird extends BaseObject{
    Bitmap birdbm ;
    Bitmap scaledbm;
    private float drop;

    public Bird() {
        this.drop = 0;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(getBm(),this.x,this.y,null);
        drop();
    }

    private void drop() {
        this.drop += 0.6;
        this.y += this.drop;
    }

    public Bitmap getBirdbm() {
        return birdbm;
    }

    public void setBirdbm(Bitmap birdbm) {
        this.birdbm = birdbm;
        scaledbm = Bitmap.createScaledBitmap(this.birdbm,this.width,this.height,true);
    }

    public float getDrop() {
        return drop;
    }

    public void setDrop(float drop) {
        this.drop = drop;
    }

    @Override
    public Bitmap getBm(){
        System.out.println(drop);
        if(this.drop<0){
            Matrix matrix = new Matrix();
            matrix.postRotate(-25);
            return Bitmap.createBitmap(scaledbm, 0,0 , scaledbm.getWidth(),scaledbm.getHeight(),matrix,true);
        }else if (this.drop> 0){
            Matrix matrix = new Matrix();
            if (drop<70){
                matrix.postRotate(-25+(2*drop));
            }
            else{
                matrix.postRotate(30);
            }

            return  Bitmap.createBitmap(scaledbm, 0,0 , scaledbm.getWidth(),scaledbm.getHeight(),matrix,true);
        }else {
            Matrix matrix = new Matrix();
            return Bitmap.createBitmap(scaledbm, 0, 0, scaledbm.getWidth(), scaledbm.getHeight(), matrix, true);

        }
    }

}
