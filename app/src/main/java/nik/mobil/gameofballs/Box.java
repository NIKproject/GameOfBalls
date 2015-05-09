package nik.mobil.gameofballs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Gábor on 2015.05.02..
 */
public class Box extends Subject {
    public Box(float posX, float posY,float size,Bitmap picture) {
        super(posX, posY,size,picture);
    }

    public void Move(float x,float y){
        posX+=x;
        posY+=y;
    }

    public int[] LocationOnScreen(int ballX,int ballY)
    {
        int[] pos=new int[2];
        pos[0]=(int)(posX-ballX);
        pos[1]=(int)(posY-ballY);
        return pos;
    }

    @Override
    public void onDraw(Canvas canvas,float ballX,float ballY,float ballXOnScreen,float ballYOnScreen) {

        int POSX=(int)(ballXOnScreen+(posX-ballX));
        int POSY=(int)(ballYOnScreen+(posY-ballY));
        canvas.drawBitmap(picture,POSX,POSY,null);
    }
}
