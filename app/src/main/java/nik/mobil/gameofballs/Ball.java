package nik.mobil.gameofballs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Gábor on 2015.05.02..
 */

public class Ball extends Subject {
    private float posX;
    private float posY;
    private float size;
    private Type type;

    @Override
    public void onDraw(Canvas canvas) {
        Bitmap bitmap= BitmapFactory.decodeFile("sphere_11.png");
        canvas.drawBitmap(bitmap,posX,posY,null);
    }

    public enum Type{
        HEAVY,NORMAL,LIGHT
    }

    public Ball(float posX, float posY, float size) {
        super(posX,posY,size);
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



}
