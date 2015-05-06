package nik.mobil.gameofballs;

import android.graphics.Canvas;

/**
 * Created by Gábor on 2015.05.02..
 */
public abstract class Subject {

    protected float posX;
    protected float posY;
    protected float size;

    public Subject(float posX, float posY, float size) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
    }



    public abstract void onDraw(Canvas canvas);
}
