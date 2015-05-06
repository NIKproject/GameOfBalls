package nik.mobil.gameofballs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gábor on 2015.05.02..
 */
public class MapView extends View {

    private final static int WIDTH = 100;
    private final static int HEIGHT = 500;
    private List<Integer> map;
    private int[][] maptiles;
    private Paint paint;
    private Ball ball;


    public MapView(Context context) {
        super(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void Init() {
        maptiles=new int[30][30];
        ball=new Ball(50,50);
        /*
        Ide kerül az xml kiolvasasásának kódja, maptile tömb feltöltése
         */

    }

    public void BallMove(float x,float y)
    {
        ball.Move(x,y);
        /*if ütközés egy tárggyal
        ha az doboz akkor eltoljuk, ha changer akkor váltunk, ha tüske akkor gameOver,
         */

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.game_map2);
        canvas.drawBitmap(bitmap,new Rect(10,10,canvas.getWidth(),canvas.getHeight()) , new Rect(0,0,canvas.getWidth()-100,canvas.getHeight()), null);
        Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.sphere_11);
        canvas.drawBitmap(ball, null, new Rect(100, 100, 100 + canvas.getWidth() / 30, 100 + canvas.getHeight() / 30), null);

    }


    public void ParseMapFromXml() throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

        File iomap = new File("..assets/map1.xml");
        FileInputStream fis = new FileInputStream(iomap);

        parser.setInput(new InputStreamReader(fis));
        parser.nextTag();

        parser.require(XmlPullParser.START_TAG, null, "map");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("data")) {
                map.add(readTile(parser));
            } else {
                skip(parser);
            }
        }


    }

    private Integer readTile(XmlPullParser parser) throws XmlPullParserException, IOException {
        String tile = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("tile")) {
                if (parser.next() == XmlPullParser.TEXT) {
                    tile = parser.getText();
                }
            } else {
                skip(parser);
            }
        }
        return Integer.parseInt(tile);

    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

    private void FromListToArray()
    {
        maptiles=new int[30][30];
        int i=0;
        int j=0;
        int z=0;
        while(z<map.size())
        {
            maptiles[i][j]=map.get(z);
            if(j<30)
            {
                j++;
            }
            else
            {
                i++;
                j=0;
            }
            z++;

        }
    }

}
