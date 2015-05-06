package nik.mobil.gameofballs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Gábor on 2015.05.02..
 */
public class Changer extends Subject {
    public Changer(float posX, float posY,float size) {
        super(posX, posY,size);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Bitmap bitmap= BitmapFactory.decodeFile("drawable/game_map.png");//kép kell vagy megadni hogy honnan vágja ki a game_map.pngből
        canvas.drawBitmap(bitmap,posX,posY,null);
    }


}
