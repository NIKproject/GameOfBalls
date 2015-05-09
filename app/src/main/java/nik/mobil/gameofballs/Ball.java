package nik.mobil.gameofballs;

import android.content.res.Resources;
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
    private Bitmap ball;
    private int ballMiddleX;
    private int ballMiddleY;

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



    public Ball(float posX, float posY,Bitmap ball ) {
        this.posX = posX;
        this.posY = posY;
        this.size=ball.getWidth();
        type=Type.NORMAL;
        this.ball=ball;
        //golyó középső ponja a kirajzolás miatt kell
        ballMiddleX=(int)(posX+size/2);
        ballMiddleY=(int)(posY+size/2);
    }
    public void Move(float posX,float posY)
    {
        this.posX+=posX;
        this.posY+=posY;
    }

    public void Change(Type type)
    {
        if(type!=this.type)
        {
            this.type=type;
            switch(type)
            {
                case NORMAL:
                    ball=BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.sphere_11);
                    break;
                case HEAVY:
                    ball=BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.sphere_00);
                    break;
                case LIGHT:
                    ball=BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.sphere_11);
                    break;
            }

        }

    }

    public void onDraw(Canvas canvas,int viewWidth,int viewHeight)
    {
        //a golyó mindig középen
        canvas.drawBitmap(ball,null,new Rect(viewWidth/2-ball.getWidth()/2,viewHeight/2-ball.getHeight()/2,viewWidth/2+ball.getWidth()/2,viewHeight/2+ball.getHeight()/2),null);
    }

    public int getBallMiddleX() {
        return ballMiddleX;
    }

    public int getBallMiddleY() {
        return ballMiddleY;
    }
}
