package nik.mobil.gameofballs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Gábor on 2015.05.02..
 */
enum Type{
    HEAVY,NORMAL,LIGHT
}

public class Ball {
    private float posX;
    private float posY;
    private float size;
    private Type type;

    public Type getType() {
        return type;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getSize() {
        return size;
    }



    public Ball(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
        this.size=30;
        type=Type.NORMAL;
    }
    public void Move(float posX,float posY)
    {
        this.posX+=posX;
        this.posY+=posY;
    }

    public void Change(Type type)
    {
        this.type=type;
    }

    public void onDraw(Canvas canvas)
    {
        //Bitmap ball = BitmapFactory.decodeResource(getResources(),)
        //canvas.drawBitmap(ball, null, new Rect(100, 100, 100 + canvas.getWidth() / 30, 100 + canvas.getHeight() / 30), null);
    }

}
