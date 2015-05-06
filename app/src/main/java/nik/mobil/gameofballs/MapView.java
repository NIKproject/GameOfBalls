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
    private static int cellSize=30;
    private List<Integer> map;
    private int[][] maptiles;
    private Paint paint;
    private Ball ball;
    private List<Box> boxes;
    private List<Changer> changers;
    private Rect mapRect;


    public MapView(Context context) {
        super(context);
        Init();

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

        //létrehozunk annyi box példányt amennyi csak van a pályán
        for (int i=0;i<maptiles.length;i++)//sor
        {
            for(int j=0;j<maptiles[i].length;j++)//oszlop
            {
                if(maptiles[i][j]==1)
                    boxes.add(new Box(i,j,30));
            }
        }
        mapRect=new Rect(0,0,300,300);

    }

    public void BallMove(float x,float y)
    {
        ball.Move(x,y);

        /*if ütközés egy tárggyal
        ha az doboz akkor eltoljuk, ha changer akkor váltunk, ha tüske akkor gameOver,
         */
        float leftTopX,leftTopY,leftBottomX,leftBottomY,rightTopX,rightTopY,rightBottomX,rightBottomY;
        leftTopX=ball.getPosX();
        leftTopY=ball.getPosY();
        int columnLeftTop= (int) Math.ceil(leftTopX+x/cellSize);
        int rowLeftTop=(int)Math.ceil(leftTopY+y/cellSize);

        leftBottomX=ball.getPosX();
        leftBottomY=ball.getPosY()+ball.getSize();
        int columnLeftBottom= (int) Math.ceil(leftBottomX+x/cellSize);
        int rowLeftBottom=(int)Math.ceil(leftBottomY+y/cellSize);

        rightTopX=ball.getPosX()+ball.getSize();
        rightTopY=ball.getPosY();
        int columnRightTop= (int) Math.ceil(rightTopX+x/cellSize);
        int rowRightTop=(int)Math.ceil(rightTopY+y/cellSize);

        rightBottomX=ball.getPosX()+ball.getSize();
        rightBottomY=ball.getPosY()+ball.getSize();
        int columnRightBottom= (int) Math.ceil(rightBottomX+x/cellSize);
        int rowRightBottom=(int)Math.ceil(rightBottomY+y/cellSize);

        //nem tudom hogy vannak az értékek majd tapasztaljuk
        //ha a gyorsulásmérő x pozitív, jobbra döntünk, negatív=balra
        //ha y pozitív előre megyünk, ha negatív akkor hátra döntjük magunk felé
        if(y>0)//előre megyünk
        {
            //csak a felső szomszéd cellákat kell ellenőrizni balfelső csúcsnál, jobbfelső csúcs
            if((maptiles[rowLeftTop][columnLeftTop]==2 || maptiles[rowRightTop][columnRightTop]==2) && ball.getType()==Type.LIGHT)//tüske
            {
                //Tüskébe mentünk strand labdával,GameOver nem lépünk
            }
            else if((maptiles[rowLeftTop][columnLeftTop]==5 || maptiles[rowRightTop][columnRightTop]==5)){//Fal
                //Falba ütköztünk nem tudunk arra lépni
                return;
            }
            else if((maptiles[rowLeftTop][columnLeftTop]==7 || maptiles[rowRightTop][columnRightTop]==7) && ball.getType()==Type.HEAVY){
                //Mágneses mezőre léptünk és acél golyónk van, elkezd húzni jobbra
                ball.Move(x+0.5f,y);
            }
        }
        else
        {
            //lefele megyünk, alsó szomszéd cellák
            if((maptiles[rowLeftBottom][columnLeftBottom]==2 || maptiles[rowRightBottom][columnRightBottom]==2) && ball.getType()==Type.LIGHT)//tüske
            {
                //Tüskébe mentünk strand labdával,GameOver nem lépünk
            }
            else if((maptiles[rowLeftBottom][columnLeftBottom]==5 || maptiles[rowRightBottom][columnRightBottom]==5)){//Fal
                //Falba ütköztünk nem tudunk arra lépni
                return;
            }
            else if((maptiles[rowLeftBottom][columnLeftBottom]==7 || maptiles[rowRightBottom][columnRightBottom]==7) && ball.getType()==Type.HEAVY){
                //Mágneses mezőre léptünk és acél golyónk van, elkezd húzni jobbra
                ball.Move(x+0.5f,y);
            }
        }

        if(x>0)//jobbra
        {
            //jobbra megyünk, jobb oldali szomszéd cellák
            if((maptiles[rowRightBottom][columnRightBottom]==2 || maptiles[rowRightTop][columnRightTop]==2) && ball.getType()==Type.LIGHT)//tüske
            {
                //Tüskébe mentünk strand labdával,GameOver nem lépünk
            }
            else if((maptiles[rowRightBottom][columnRightBottom]==5 || maptiles[rowRightTop][columnRightTop]==5)){//Fal
                //Falba ütköztünk nem tudunk arra lépni
                return;
            }
            else if((maptiles[rowRightBottom][columnRightBottom]==7 || maptiles[rowRightTop][columnRightTop]==7) && ball.getType()==Type.HEAVY){
                //Mágneses mezőre léptünk és acél golyónk van, elkezd húzni jobbra
                ball.Move(x+0.5f,y);
            }
        }
        else
        {
            //balra megyünk, bal oldali szomszéd cellák
            if((maptiles[rowLeftBottom][columnLeftBottom]==2 || maptiles[rowLeftTop][columnLeftTop]==2) && ball.getType()==Type.LIGHT)//tüske
            {
                //Tüskébe mentünk strand labdával,GameOver nem lépünk
            }
            else if((maptiles[rowLeftBottom][columnLeftBottom]==5 || maptiles[rowLeftTop][columnLeftTop]==5)){//Fal
                //Falba ütköztünk nem tudunk arra lépni
                return;
            }
            else if((maptiles[rowLeftBottom][columnLeftBottom]==7 || maptiles[rowLeftTop][columnLeftTop]==7) && ball.getType()==Type.HEAVY){
                //Mágneses mezőre léptünk és acél golyónk van, elkezd húzni jobbra
                ball.Move(x+0.5f,y);
            }
        }
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.game_map2);
        canvas.drawBitmap(bitmap,new Rect(10,10,canvas.getWidth(),canvas.getHeight()) , new Rect(0,0,canvas.getWidth()-100,canvas.getHeight()), null);

        ball.onDraw(canvas);
        //Ha a doboz benne van a kirajzolt felületbe akkor kirajzoljuk
        for(Box item:boxes){
            if(mapRect.contains((int)item.getPosX(),(int)item.getPosY(),(int)(item.getPosX()+item.getSize()),(int)(item.getPosY()+item.getSize())))
            {
                item.onDraw(canvas);
            }
        }
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
