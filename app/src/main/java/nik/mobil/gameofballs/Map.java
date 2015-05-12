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

    
    private Bitmap background;

    public Map(Bitmap background) {
        this.background=background;
    }

    public void onDraw(Canvas canvas,Rect mapVisibleRect,Rect mapDrawRect){
        //a térkép a labda adataiból kapja a mapVisibleRect Rectanglet ami miatt a pálya is mozogni fog nem kell Move metódus
        //canvas.drawBitmap(background,mapVisibleRect,mapDrawRect,null);
        canvas.drawBitmap(background,null,new Rect(0,0,canvas.getWidth(),canvas.getHeight()),null);
       /* if(mapVisibleRect.left>0 && mapVisibleRect.right<background.getWidth() )
        {
          if(mapVisibleRect.top>0 && mapVisibleRect.bottom<background.getHeight())
          {

          }
        }*/




    }
}
