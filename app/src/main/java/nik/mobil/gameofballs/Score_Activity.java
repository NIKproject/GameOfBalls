package nik.mobil.gameofballs;

import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class Score_Activity extends ActionBarActivity {

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_);

        String[] names=new String[10];
        Integer[] score=new Integer[10];


        try{
            //kivesszük a score.xml elérési útját
            File file=new File(getFilesDir(),"score.xml");

            DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder=docFactory.newDocumentBuilder();

            InputStream is=getResources().openRawResource(R.raw.score);
            Document doc=docBuilder.parse(file);
            //az összes name és score tag elemet kiválasztjuk és bejárjuk őket olvasás miatt
            NodeList nodeList=doc.getElementsByTagName("name");
            for(int i=0;i<nodeList.getLength();i++){
                names[i]=nodeList.item(i).getChildNodes().item(0).getNodeValue();
            }

            nodeList=doc.getElementsByTagName("score");
            for(int i=0;i<nodeList.getLength();i++){
                score[i]=Integer.parseInt(nodeList.item(i).getChildNodes().item(0).getNodeValue());
            }
            //adat mentés vissza az eredetibe
            Transformer transformer= TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");

            StreamResult result=new StreamResult(file);
            DOMSource source=new DOMSource(doc);
            transformer.transform(source,result);



        }
        catch (Exception e){
            Log.d("test",e.getMessage());
        }

        String[] scores=new String[names.length];
        listView=(ListView)findViewById(R.id.score_list);

        for(int k=0;k<names.length;k++)
        {
            if(score[k]!=null)
            {
                scores[k]=names[k]+" - "+score[k].toString();
            }
            else
                scores[k]="";

        }
        //ListView adapter előállítás megjelenítéshez
        ListAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,scores);
        listView.setAdapter(adapter);

    }
    public void BackToMain(View view)
    {
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
