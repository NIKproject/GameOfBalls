package nik.mobil.gameofballs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.File;

/**
 * Created by Gábor on 2015.05.07..
 */
public class Map {
    private Rect mapVisibleRect;
    private int offsetX;
    private int offsetY;
    private Bitmap background;

    public Map(Rect mapVisibleRect, int offsetX, int offsetY,Bitmap background) {
        this.mapVisibleRect = mapVisibleRect;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.background=background;


    }
    public void MapMove(float x,float y)
    {
        mapVisibleRect.left+=x;
        mapVisibleRect.top+=y;
        mapVisibleRect.right+=x;
        mapVisibleRect.bottom+=y;
    }


    public void onDraw(Canvas canvas,Rect mapVisibleRect){
        //használjuk az offsetet a térkép eltolásához hogy melyik felületet látjuk épp és mit rajzolunk ki róla
        canvas.drawBitmap(background,mapVisibleRect,new Rect(0,0,canvas.getWidth(),canvas.getHeight()),null);

    }
}
