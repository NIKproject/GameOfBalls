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
    private Rect ballRect;



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



    public Ball(float posX, float posY,Bitmap ball,float size ) {
        this.posX = posX;
        this.posY = posY;
        this.size=ball.getWidth();
        type=Type.NORMAL;
        this.ball=ball;
        this.size=size*(float)0.8;
        //golyó középső ponja a kirajzolás miatt kell
        ballMiddleX=(int)(this.posX+size/2);
        ballMiddleY=(int)(this.posY+size/2);
        ballRect=new Rect(Math.round(posX),Math.round(posY),Math.round(posX+size),Math.round(posY+size));
    }
    public void Move(float posX,float posY, int mapWidth, int mapHeight)
    {



        if(this.posX+posX >0 && this.posX+posX+size<mapWidth)
        {
            this.posX+=posX;
            ballMiddleX=(int)(this.posX+size/2);
        }
        if(this.posY+posY>0 && this.posY+posY+size<mapHeight) {
            this.posY += posY;
            ballMiddleY=(int)(this.posY+size/2);
        }
        ballRect=new Rect(Math.round(this.posX),Math.round(this.posY),Math.round(this.posX+size),Math.round(this.posY+size));
    }

    public void Change(Type type,Bitmap ball)
    {
        if(type!=this.type)
        {
            this.type=type;
            this.ball=ball;

        }

    }

    public void onDraw(Canvas canvas,int viewWidth,int viewHeight)
    {
        //a golyó mindig középen
        //canvas.drawBitmap(ball,null,new Rect(viewWidth/2-ball.getWidth()/2,viewHeight/2-ball.getHeight()/2,viewWidth/2+ball.getWidth()/2,viewHeight/2+ball.getHeight()/2),null);
       canvas.drawBitmap(ball,null,new Rect((int)posX,(int)posY,(int)(posX+size),(int)(posY+size)),null);

    }

    public int getBallMiddleX() {
        return ballMiddleX;
    }

    public int getBallMiddleY() {
        return ballMiddleY;
    }

    public void setSize(float size)
    {
        this.size=size;
    }
    public Rect getRect()
    {
        return ballRect;
    }

}
