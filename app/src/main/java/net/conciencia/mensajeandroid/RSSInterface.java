package net.conciencia.mensajeandroid;

/**
 * Created by smccollum on 05.05.16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * RSS interface for Un Mensaje A La Conciencia's website
 */
public class RSSInterface implements Parcelable {
    private static final String UN_MENSAJE_RSS_FEED = "http://conciencia.net/rss.aspx";

    boolean dataLoaded = false;
    List<Sermon> sermons;

    public RSSInterface(Parcel source) {
        sermons = source.readArrayList(ClassLoader.getSystemClassLoader());
    }
    public RSSInterface(){
        sermons = new ArrayList<Sermon>();
    }

    public static void main(String[] args) {
        RSSInterface umr = new RSSInterface();
        System.out.println("Retrieving information from web..." + (umr.loadSermonsFromWeb() ? "Successful" : "Failed!"));
        System.out.println("Printing Items...");
        System.out.println(umr.getSermons());
        System.out.println("Finished Printing Items...");
    }

    /**
     * Load the sermons from the site so that they can be accessed
     * @return
     */
    public boolean loadSermonsFromWeb(){
        long startTime = System.currentTimeMillis();
        Document feed = getXML();
        System.out.printf("Duration: %fs\n", (double)((System.currentTimeMillis()-startTime)));
        if(feed == null)
            return false;
        sermons = parseDocument(feed);
        dataLoaded = true;
        return true;
    }

    public Sermon getSermon(int sermonIndex){
        return sermons.get(sermonIndex);
    }

    public Document getXML() {
        try
        {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document dom = b.parse(UN_MENSAJE_RSS_FEED);
            return dom;
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    private ArrayList<Sermon> parseDocument(Document xmlDocument){
        //get the root element
        Element documentElement = xmlDocument.getDocumentElement(), itemElement;

        ArrayList<Sermon> parsedSermons = new ArrayList<Sermon>();

        //get a nodelist of elements
        NodeList items = documentElement.getElementsByTagName("item");
        if(items != null && items.getLength() > 0) {
            for(int i = 0 ; i < items.getLength();i++) {

                //get the itemElement element
                itemElement = (Element)items.item(i);

                //get the Employee object
                Sermon sermon = this.getItem(itemElement);

                //add it to list
                parsedSermons.add(sermon);
            }
        }
        return parsedSermons;
    }

    private Sermon getItem(Element itemElement) {
        String title       = getTextValue(itemElement, "title");
        String link        = getTextValue(itemElement, "link");
        String guid        = getTextValue(itemElement, "guid");
        String pubDate     = getTextValue(itemElement, "pubDate");
        String author      = getTextValue(itemElement, "author");
        String description = getTextValue(itemElement, "description");
        String[] audioVideo = getEnclosureValues(itemElement, "enclosure");
        String audioURL = audioVideo[0];
        String videoURL = audioVideo[1];
        String duration = getTextValue(itemElement, "itunes:duration");
        return new Sermon(title, link, guid, pubDate, author, description, audioURL, videoURL, duration);
    }

    private String getTextValue(Element itemElement, String tagName) {
        String textValue = null;
        NodeList nodeList = itemElement.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            Element element = (Element)nodeList.item(0);
            textValue = element.getFirstChild().getNodeValue();
        }
        return textValue;
    }

    private String[] getEnclosureValues(Element itemElement, String tagName) {
        String[] attributeValues = new String[2];
        NodeList audioVideo = itemElement.getElementsByTagName("enclosure");
        attributeValues[0]  = audioVideo.item(0).getAttributes().item(2).toString();
        attributeValues[1]  = audioVideo.item(1).getAttributes().item(2).toString();
        return attributeValues;
    }

    /*private void print(ArrayList<Sermon> parsedSermons) {
        for (Sermon sermon : parsedSermons) {
            sermon.println();
            System.out.println();
        }
    }//*/

    public List<Sermon> getSermons() {
        return sermons;
    }
    public void setSermons(List<Sermon> sermons){
        this.sermons = sermons;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(getSermons());
    }

    public static final Parcelable.Creator<RSSInterface> CREATOR = new Parcelable.Creator<RSSInterface>(){

        @Override
        public RSSInterface createFromParcel(Parcel source) {
            return new RSSInterface(source);
        }

        @Override
        public RSSInterface[] newArray(int size) {
            return new RSSInterface[size];
        }
    };
}
