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

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(picture,posX,posY,null);
    }
}
