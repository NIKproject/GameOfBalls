package nik.mobil.gameofballs;

import android.content.Context;
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
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

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
    Ball ball2;

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
        Bitmap ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sphere_11);

        ball = new Ball(/*12*35, 10*35*/0,0, ballBitmap, (float) getWidth() / 28);
        ball2 = new Ball(13*35,35*35,ballBitmap,(float)getWidth()/28);
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

        Bitmap box = BitmapFactory.decodeResource(getResources(), R.drawable.box);
        //létrehozunk annyi box példányt amennyi csak van a pályán
       /* for (int sor=0;i<maptiles.length;i++)//sor
        {
            for(int oszlop=0;j<maptiles[sor].length;j++)//oszlop
            {
                if(maptiles[sor][oszlop]==1)
                    boxes.add(new Box(oszlop*30,sor*30,30,box));
            }
        }*/
    }



    public void BallMove(float x,float y)
    {
        invalidate();

        if(first)
        {
            nullX=x;
            nullY=y;
            ball.setSize((float)(getWidth()/28));
            ball2.setSize((float)(getWidth()/28));
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
                RectF tr= new RectF(j*35,i*35,(j+1)*35,(i+1)*35);
                if(tr.intersect(ball.getToMoveRect(x,0))) {
                    /*switch(maptiles[i][j])
                    {

                    }*/
                    if(maptiles[i][j]==4 || maptiles[i][j]==8)
                    {
                        canmoveX=false;
                    }
                }
                if(tr.intersect(ball.getToMoveRect(0,y)))
                {
                    if(maptiles[i][j]==4 || maptiles[i][j]==8) {
                        canmoveY = false;
                    }
                }
            }
        }

        if(canmoveX)
        {
            ball.Move( x ,  0 , getWidth(), getHeight());
        }
        if(canmoveY)
        {
            ball.Move( 0 ,  y , getWidth(), getHeight());
        }

        /*switch (ball.getType())
        {
            case HEAVY :
                ball.Move((nullX - x) * (float) 0.5, (nullY - y) * (float) 0.5, getWidth(), getHeight());
                break;
            case LIGHT:
                ball.Move((nullX - x) * (float) 2, (nullY - y) * (float) 2, getWidth(), getHeight());
                break;
            case NORMAL:
                ball.Move(nullX - x , nullY - y , getWidth(), getHeight());
                break;
            default:
                break;
        }*/



        /*if ütközés egy tárggyal
        ha az doboz akkor eltoljuk, ha changer akkor váltunk, ha tüske akkor gameOver,
         */
        /*
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
        }*/

        invalidate();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.game_map2);
        //canvas.drawBitmap(bitmap,new Rect(10,10,canvas.getWidth(),canvas.getHeight()) , new Rect(0,0,canvas.getWidth()-100,canvas.getHeight()), null);

        //ball.onDraw(canvas);
        //Ha a doboz benne van a kirajzolt felületbe akkor kirajzoljuk

        //Bitmap box=BitmapFactory.decodeResource(getResources(),R.drawable.game_map2);
        //Bitmap background=BitmapFactory.decodeResource(getResources(),R.drawable.map1_nobox);
        //canvas.drawBitmap(background,new Rect(190,240,190+canvas.getWidth(),240+canvas.getHeight()),new Rect(0,0,canvas.getWidth(),canvas.getHeight()),null);
        /*new Rect((int)(ball.getPosX()-width/2+ball.getSize()/2),
                (int)(ball.getPosY()-height/2+ball.getSize()/2),
                (int)(ball.getPosX()+width/2-ball.getSize()/2),
                (int)(ball.getPosY()+height/2-ball.getSize()/2)*/
        /*mapRect=new Rect((int)(ball.getPosX()+ball.getSize()/2-canvas.getWidth()/2),
                (int)(ball.getPosY()+ball.getSize()/2-canvas.getHeight()/2),
                (int)(ball.getPosX()-ball.getSize()/2+canvas.getWidth()/2),
                (int)(ball.getPosY()-ball.getSize()/2-canvas.getHeight()/2)
        );*/
        //mapRect=new Rect(0,0,30,30);
        /*if((ball.getBallMiddleX()-this.getWidth()/2)>0 && (ball.getBallMiddleX()+this.getWidth()/2) < )
        {

        }*/

        /*if(ball.getBallMiddleX()-this.getWidth()/2>0 && ball.getBallMiddleX()+this.getWidth()/2<getWidth())
        {
            if(ball.getBallMiddleY() + this.getHeight() / 2>0 && ball.getBallMiddleY() + this.getHeight() / 2<getWidth())
            {
                mapRect = new Rect(ball.getBallMiddleX() - this.getWidth() / 2, ball.getBallMiddleY() - this.getHeight() / 2, ball.getBallMiddleX() + this.getWidth() / 2, ball.getBallMiddleY() + this.getHeight() / 2);
            }
            else if(ball.getBallMiddleY() + this.getHeight() / 2>0)
            {
                mapRect = new Rect(ball.getBallMiddleX() - this.getWidth() / 2, ball.getBallMiddleY() - this.getHeight() / 2, ball.getBallMiddleX() + this.getWidth() / 2, this.getHeight());
            }
            else
            {
                mapRect = new Rect(ball.getBallMiddleX() - this.getWidth() / 2, 0, ball.getBallMiddleX() + this.getWidth() / 2, ball.getBallMiddleY() + this.getHeight() / 2);

            }
        }
        else if(ball.getBallMiddleX()-this.getWidth()/2>0)
        {
            if(ball.getBallMiddleY() + this.getHeight() / 2>0 && ball.getBallMiddleY() + this.getHeight() / 2<getWidth())
            {
                mapRect = new Rect(ball.getBallMiddleX() - this.getWidth() / 2, ball.getBallMiddleY() - this.getHeight() / 2, this.getWidth() / 2, ball.getBallMiddleY() + this.getHeight() / 2);
            }
            else if(ball.getBallMiddleY() + this.getHeight() / 2>0)
            {
                mapRect = new Rect(ball.getBallMiddleX() - this.getWidth() / 2, ball.getBallMiddleY() - this.getHeight() / 2, this.getWidth() / 2, this.getHeight());
            }
            else
            {
                mapRect = new Rect(ball.getBallMiddleX() - this.getWidth() / 2, 0, this.getWidth() / 2, ball.getBallMiddleY() + this.getHeight() / 2);

            }
        }
        else
        {
            if(ball.getBallMiddleY() + this.getHeight() / 2>0 && ball.getBallMiddleY() + this.getHeight() / 2<getWidth())
            {
                mapRect = new Rect(0 , ball.getBallMiddleY() - this.getHeight() / 2, ball.getBallMiddleX() + this.getWidth() / 2, ball.getBallMiddleY() + this.getHeight() / 2);
            }
            else if(ball.getBallMiddleY() + this.getHeight() / 2>0)
            {
                mapRect = new Rect(0, ball.getBallMiddleY() - this.getHeight() / 2, ball.getBallMiddleX() + this.getWidth() / 2, this.getHeight());
            }
            else
            {
                mapRect = new Rect(0, 0, ball.getBallMiddleX() + this.getWidth() / 2, ball.getBallMiddleY() + this.getHeight() / 2);

            }
        }*/

        //mapRect=new Rect(ball.getBallMiddleX()-this.getWidth()/2,ball.getBallMiddleY()-this.getHeight()/2,ball.getBallMiddleX()+this.getWidth()/2,ball.getBallMiddleY()+this.getHeight()/2);

        //Rect drawRect=new Rect(0,0,getWidth(),getHeight());

       // mapReal.onDraw(canvas,mapRect,drawRect);
        //mapReal.onDraw(canvas,null,drawRect);

        setBackgroundResource(R.drawable.map2_final);

       // Bitmap box=BitmapFactory.decodeResource(getResources(),R.drawable.box);

        //canvas.drawBitmap(box,new Rect(0,0,30,30),new Rect(0,0,this.getWidth(),this.getHeight()),null);
        /*for(Box item:boxes){
            if(mapRect.contains((int)item.getPosX(),(int)item.getPosY(),(int)(item.getPosX()+item.getSize()),(int)(item.getPosY()+item.getSize())))
            {
                item.onDraw(canvas);
            }
        }*/
        ball.setSize((float)(getWidth()/28));
        ball.onDraw(canvas,this.getWidth(),this.getHeight());
        ball2.setSize((float)(getWidth()/28));
        ball2.onDraw(canvas,this.getWidth(),this.getHeight());

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
