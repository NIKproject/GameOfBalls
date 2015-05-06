package nik.mobil.gameofballs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Gábor on 2015.05.07..
 */
public class Map {
    private Rect mapVisibleRect;
    private int offsetX;
    private int offsetY;
    private Bitmap background;

    public Map(Rect mapVisibleRect, int offsetX, int offsetY) {
        this.mapVisibleRect = mapVisibleRect;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        background= BitmapFactory.decodeFile("drawable/game_map.png");
    }
    public void MapMove(float x,float y)
    {
        offsetX+=x;
        offsetY+=y;
    }


    public void onDraw(Canvas canvas){
        //használjuk az offsetet a térkép eltolásához hogy melyik felületet látjuk épp és mit rajzolunk ki róla
    }
}
