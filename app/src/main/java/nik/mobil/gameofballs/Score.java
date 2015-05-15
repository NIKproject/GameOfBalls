package nik.mobil.gameofballs;

import android.content.res.Resources;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Gábor on 2015.05.15..
 */
public final class Score {
    static String[] namesArray;
    static Integer[] scoreArray;
    static File file;
    static Resources res;
    static ClassLoader loader;
    static InputStream is;
    public static void AddFile(File file2){
        file=file2;
    }
    public static void AddScore(int score,String name){
        //Új eredmény hozzáadása az eredménylistánkhoz
         namesArray=new String[10];
        scoreArray=new Integer[10];
        try{

            DocumentBuilderFactory docFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder=docFactory.newDocumentBuilder();
            //kiolvassuk az eddigi eredményeket a score.xmlből
            Document doc=docBuilder.parse(file);
            NodeList nodeList=doc.getElementsByTagName("name");
            for(int i=0;i<nodeList.getLength();i++){
                namesArray[i]=nodeList.item(i).getChildNodes().item(0).getNodeValue();
            }

            nodeList=doc.getElementsByTagName("score");
            for(int i=0;i<nodeList.getLength();i++){
                scoreArray[i]=Integer.parseInt(nodeList.item(i).getChildNodes().item(0).getNodeValue());
            }

            //az eredmény alapján beszúrás, majd a tömb további elemeit eggyel letoljuk
            for(int i=0;i<scoreArray.length;i++)
            {
                if(score>scoreArray[i]){
                    Integer tempScore=scoreArray[i];
                    Integer tempScore2;
                    String tempName=namesArray[i];
                    String tempName2;
                    scoreArray[i]=score;
                    namesArray[i]=name;
                    for(int j=i+1;j<scoreArray.length;j++)
                    {
                        tempScore2=scoreArray[j];
                        tempName2=namesArray[j];
                        scoreArray[j]=tempScore;
                        namesArray[j]=tempName;
                        tempScore=tempScore2;
                        tempName=tempName2;
                    }
                    break;
                }
            }
            //Visszaírjuk az új értékeket az XML-be
            Transformer transformer= TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            nodeList=doc.getElementsByTagName("name");
            for(int i=0;i<nodeList.getLength();i++){
                nodeList.item(i).setTextContent(namesArray[i]);
            }
            nodeList=doc.getElementsByTagName("score");
            for(int i=0;i<nodeList.getLength();i++){
                nodeList.item(i).setTextContent(scoreArray[i].toString());
            }


            StreamResult result=new StreamResult(file);
            DOMSource source=new DOMSource(doc);
            transformer.transform(source,result);



        }
        catch (Exception e){
            Log.d("test", e.getMessage());
        }
    }
}
