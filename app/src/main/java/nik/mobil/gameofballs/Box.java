package nik.mobil.gameofballs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by Gábor on 2015.05.02..
 */
public class Box extends Subject {
    public Box(float posX, float posY,float size,Bitmap picture) {
        super(posX, posY,size,picture);
    }

    public void Move(float x,float y, int[][] maptiles){
        if(x>0)
        {
            while (maptiles[(int)posY][(int)(posX+size+x)]==5)
            {
                posX+=x;
            }
        }
        else
        {
            while (maptiles[(int)posY][(int)(posX+x)]==5)
            {
                posX+=x;
            }
        }
        if(y> 0)
        {
            while (maptiles[(int)(posY+size+y)][(int)(posX)]==5)
            {
                posY+=y;
            }
        }
        else
        {
            while (maptiles[(int)(posY+y)][(int)(posX)]==5)
            {
                posY+=y;
            }
        }


    }



    @Override
    public void onDraw(Canvas canvas ) {


        //canvas.drawBitmap(picture,posX,posY,null);
        canvas.drawBitmap(picture,null,new RectF(posX,posY,posX+size,posY+size),null);
    }

    public RectF getRect()
    {
        return new RectF(posX,posY,posX+size,posY+size);
    }

    public RectF getToRect(float x, float y)
    {
        return new RectF(posX+x,posY+y,posX+size+x,posY+size+y);
    }
}
