package nik.mobil.gameofballs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static nik.mobil.gameofballs.Type.*;

/**
 * Created by Gábor on 2015.05.02..
 */
public class MapView extends View {

    private int[][] maptiles;
    private Ball ball;
    private List<Box> boxes;
    private Rect mapRect;
    private Map mapReal;
    private boolean first;
    float nullX;
    float nullY;
    private int mapWidth;
    private int mapHeight;

    public MapView(Context context) throws IOException, XmlPullParserException {
        super(context);

        Init();

    }

    public MapView(Context context, AttributeSet attrs) throws IOException, XmlPullParserException {
        super(context, attrs);
        Init();
    }

    public MapView(Context context, AttributeSet attrs, int defStyleAttr) throws IOException, XmlPullParserException {
        super(context, attrs, defStyleAttr);
        Init();
    }

    private void Init() throws IOException, XmlPullParserException {
        first = true;
        maptiles = new int[45][28];
        boxes = new ArrayList<Box>();
        Bitmap ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sphere_11);

        ball = new Ball(12*35, 10*35, ballBitmap, (float) getWidth() / 28);
        Bitmap backGround = BitmapFactory.decodeResource(getResources(), R.drawable.map2_final);

        // mapWidth=backGround.getWidth();
        //mapHeight=backGround.getHeight();

        //Bitmap asd=BitmapFactory.decodeResource(getResources(),R.drawable.game_map2);
        mapReal = new Map(backGround);


        Resources res = getResources();

        //XmlResourceParser xpp = getContext().getAssets().openXmlResourceParser("maps/map2_final.xml");
        XmlResourceParser xpp = getResources().getXml(R.xml.map2_final);

        int eventType = xpp.getEventType();
        int i = 0, j = 0;
        String asd = xpp.getName();
        int db = 0;
        boolean stop = false;
        int event;
        String TAG_ITEM = "tile";
        while ((event = xpp.next()) != XmlPullParser.END_DOCUMENT) {

            if (event == XmlPullParser.START_TAG) {
                String tag = xpp.getName();
                if (TAG_ITEM.equals(tag)) {

                    if (j <= 44 && i <= 27) {
                        maptiles[j][i] = Integer.parseInt(xpp.getAttributeValue(0));
                    }
                    i++;
                    if (i % 28 == 0) {
                        i = 0;
                        j++;
                    }


                }

            }

            invalidate();
        }


        //létrehozunk annyi box példányt amennyi csak van a pályán

    }



    public void BallMove(float x,float y)
    {
        invalidate();

        if(first)
        {
            nullX=x;
            nullY=y;
            ball.setSize((float)((getWidth()/28)*0.8));
            first=false;


        }


        x=nullX-x;
        y=nullY-y;
        int i=0;
        int j=0;
        boolean canmoveX=true;
        boolean canmoveY=true;
        for ( i=0;i<45;i++)
        {
            for ( j=0; j<28;j++)
            {
               // RectF rt=ball.getToMoveRect(x,y);
                RectF tr= new RectF(j*getWidth()/28,i*getHeight()/45,(j+1)*getWidth()/28,(i+1)*getHeight()/45);
                if(tr.intersect(ball.getToMoveRect(x,0))) {

                    switch(maptiles[i][j])
                    {
                        case 2:
                            canmoveX=false;
                            break;
                        case 3:
                            canmoveX=false;
                            break;
                        case 4 :
                                canmoveX=false;
                                break;
                        case 1 :
                            if(ball.getType()==Type.HEAVY)
                            {
                                canmoveX=false;
                                if(x>0 && (maptiles[i][j+1]==5 || maptiles[i][j+1]==6||maptiles[i][j+1]==7 || maptiles[i][j+1]>9)) {

                                    maptiles[i][j] = 5;
                                    maptiles[i][j+1] = 1;
                                }
                                else if(maptiles[i][j-1]==5 || maptiles[i][j-1]==6||maptiles[i][j-1]==7 || maptiles[i][j-1]>9)
                                {
                                    maptiles[i][j] = 5;
                                    maptiles[i][j-1] = 1;
                                }
                            }
                            else
                            {
                                canmoveX = false;
                            }
                            break;

                        case 6:
                            x-=0.5;
                            break;

                        case 7:
                            x+=0.5;
                            break;
                        case 8:
                            canmoveX=false;
                            Toast.makeText( getContext().getApplicationContext(), "Game over", Toast.LENGTH_LONG).show();

                           Activity activity = (Activity) getContext();
                            activity.finish();
                            break;

                        case 10:

                            ball.Change(Type.NORMAL, BitmapFactory.decodeResource(getResources(), R.drawable.sphere_11));
                            break;
                        case 11:

                            ball.Change(Type.HEAVY,BitmapFactory.decodeResource(getResources(), R.drawable.sphere_00));
                            break;
                        case 12 :
                            ball.Change(Type.LIGHT,BitmapFactory.decodeResource(getResources(), R.drawable.volyball));

                            break;
                        case 13:
                            canmoveX=false;
                            Toast.makeText( getContext().getApplicationContext(), "You won", Toast.LENGTH_LONG).show();

                            Activity activity1 = (Activity) getContext();
                            activity1.finish();
                            break;
                    }
                }
                if(tr.intersect(ball.getToMoveRect(0,y)))
                {
                    switch(maptiles[i][j])
                    {
                        case 4 :
                            canmoveY=false;
                            break;
                        case 1 :
                            if(ball.getType()==Type.HEAVY)
                            {
                                canmoveY=false;
                                if(y>0 && (maptiles[i+1][j]==5 || maptiles[i+1][j]==6||maptiles[i+1][j]==7 || maptiles[i+1][j]>9)) {

                                    maptiles[i][j] = 5;
                                    maptiles[i+1][j] = 1;
                                }
                                else if(maptiles[i-1][j]==5 || maptiles[i-1][j]==6||maptiles[i-1][j]==7 || maptiles[i-1][j]>9)
                                {
                                    maptiles[i][j] = 5;
                                    maptiles[i-1][j] = 1;
                                }
                            }
                            else
                            {
                                canmoveY = false;
                            }
                            break;
                        case 2:
                            canmoveY=false;
                            break;
                        case 3:
                            canmoveY=false;
                            break;
                        case 5 :

                            break;
                        case 6:
                            x-=0.5;
                            break;

                        case 7:
                            x+=0.5;

                            break;

                        case 8:
                            canmoveY=false;
                            Toast.makeText( getContext().getApplicationContext(), "Game over", Toast.LENGTH_LONG).show();

                            Activity activity = (Activity) getContext();
                            activity.finish();
                            break;
                        case 10:

                            ball.Change(Type.NORMAL,BitmapFactory.decodeResource(getResources(), R.drawable.sphere_11));
                            break;
                        case 11:

                            ball.Change(Type.HEAVY,BitmapFactory.decodeResource(getResources(), R.drawable.sphere_00));
                            break;
                        case 12 :
                            ball.Change(Type.LIGHT,BitmapFactory.decodeResource(getResources(), R.drawable.volyball));

                            break;
                        case 13:
                            canmoveY=false;
                            Toast.makeText( getContext().getApplicationContext(), "You won", Toast.LENGTH_LONG).show();

                            Activity activity1 = (Activity) getContext();
                            activity1.finish();
                            break;
                    }
                }
            }
        }
        switch (ball.getType())
        {
            case HEAVY :
                x=x*(float)0.75;
                y=y*(float)0.75;
                break;
            case LIGHT:
                x=x*(float)1.25;
                y=y*(float)1.25;
                break;
        }
        if(canmoveX)
        {
            ball.Move( x ,  0 , getWidth(), getHeight());
        }
        if(canmoveY)
        {
            ball.Move( 0 ,  y , getWidth(), getHeight());
        }







        invalidate();


    }

    @Override
    protected void onDraw(Canvas canvas) {


        //mapRect=new Rect(ball.getBallMiddleX()-this.getWidth()/2,ball.getBallMiddleY()-this.getHeight()/2,ball.getBallMiddleX()+this.getWidth()/2,ball.getBallMiddleY()+this.getHeight()/2);

        //Rect drawRect=new Rect(0,0,getWidth(),getHeight());

       // mapReal.onDraw(canvas,mapRect,drawRect);
        //mapReal.onDraw(canvas,null,drawRect);

        setBackgroundResource(R.drawable.map2_final);



        //canvas.drawBitmap(box,new Rect(0,0,30,30),new Rect(0,0,this.getWidth(),this.getHeight()),null);
        if(first) {
                 Bitmap box = BitmapFactory.decodeResource(getResources(), R.drawable.box);
                    for (int sor = 0; sor < maptiles.length; sor++)//sor
                     {
                           for (int oszlop = 0; oszlop < maptiles[sor].length; oszlop++)//oszlop
                           {
                                if (maptiles[sor][oszlop] == 1)
                                      boxes.add(new Box(oszlop * getWidth() / 28, sor * getHeight() / 45, getWidth() / 28, box));
                               }
                            }
                 }

        /*for(Box item:boxes){

                item.onDraw(canvas);

        }*/

        for (int i=0; i<45; i++ )
        {
            for (int j=0; j<28; j++ )
            {
                if(maptiles[i][j]==1)

                {
                    canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.box),null,new Rect(j*getWidth()/28,i*getHeight()/45,(j+1)*getWidth()/28,(i+1)*getHeight()/45),null);
                }
            }
        }

        ball.onDraw(canvas,this.getWidth(),this.getHeight());

    }



    /*public void ParseMapFromXml() throws XmlPullParserException, IOException {
        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

        File iomap = new File("asdl");
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
    }*/


}
