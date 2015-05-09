package nik.mobil.gameofballs;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Gábor on 2015.05.02..
 */
public abstract class Subject {

    protected float posX;
    protected float posY;
    protected float size;
    protected Bitmap picture;

    public Subject(float posX, float posY, float size,Bitmap picture) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.picture=picture;
    }

    public float getPosX(){
        return posX;
    }





    public abstract void onDraw(Canvas canvas);

    public float getPosY() {
        return posY;
    }

    public float getSize() {
        return size;
    }
}
