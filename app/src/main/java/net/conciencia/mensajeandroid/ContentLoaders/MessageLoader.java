package net.conciencia.mensajeandroid.ContentLoaders;

/**
 * Created by smccollum on 05.05.16.
 */
import android.os.Parcel;
import android.os.Parcelable;

import net.conciencia.mensajeandroid.Objects.Message;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * RSS interface for    Un Mensaje A La Conciencia's website
 */
public class MessageLoader implements Parcelable {
    private static final String UN_MENSAJE_RSS_FEED = "http://conciencia.net/rss.aspx";

    boolean dataLoaded = false;
    ArrayList<Message> messages;

    public MessageLoader(Parcel source) {
        messages = source.readArrayList(Message.class.getClassLoader());
    }
    public MessageLoader(){
        messages = new ArrayList<Message>();
    }

    public boolean loadMessagesFromWeb(){
        NodeList feed = getNodeListFromXML();
        if(feed == null)
            return false;
        messages = getArrayMessageArrayListFromDocument(feed);
        dataLoaded = true;
        return true;
    }

    public Message getSermon(int sermonIndex){
        return messages.get(sermonIndex-1);
    }

    // w3c.dom tool returns an itemized version of Un Mensaje's RSS Feed for
    // Messages in an object called a NodeList from the w3c.dom library
    public NodeList getNodeListFromXML() {
        try
        {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(UN_MENSAJE_RSS_FEED).getDocumentElement().getElementsByTagName("item");
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    private ArrayList<Message> getArrayMessageArrayListFromDocument(NodeList xmlNodeList){
        ArrayList<Message> parsedMessages = new ArrayList<Message>();
        for(int i = 0 ; i < xmlNodeList.getLength();i++) {
            Element element = (Element)xmlNodeList.item(i);
            Message message = getMessageFromElement(element);
            parsedMessages.add(message);
        }
        return parsedMessages;
    }

    private Message getMessageFromElement(Element messageElement) {
        String title       = getTitle(messageElement);
        String link        = getLink(messageElement);
        String guid        = getGuid(messageElement);
        String pubDate     = getPubDate(messageElement);
        String author      = getAuthor(messageElement);
        String description = getDescription(messageElement);
        String audioURL    = getAudioURL(messageElement);
        String videoURL    = getVideoURL(messageElement);
        String duration    = getDuration(messageElement);
        return new Message(title, link, guid, pubDate, author, description, audioURL, videoURL, duration);
    }

    private String getMessageAttributeByTagNameFromElement(Element itemElement, String tagName) {
        return itemElement.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
    }

    private String getTitle(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "title");
    }

    private String getLink(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "link");
    }

    private String getGuid(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "guid");
    }

    private String getPubDate(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "pubDate");
    }

    private String getAuthor(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "author");
    }

    private String getDescription(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "description");
    }

    private String getDuration(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "itunes:duration");
    }

    private String getAudioOrVideoURL(Element itemElement, int audioURLOrVideoURLndex) {
        return itemElement.getElementsByTagName("enclosure").item(audioURLOrVideoURLndex).getAttributes().item(0).getNodeValue();
    }

    private String getVideoURL(Element messageElement) {
        return getAudioOrVideoURL(messageElement, 1);
    }

    private String getAudioURL(Element messageElement) {
        return getAudioOrVideoURL(messageElement, 0);
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(getMessages());
    }

    public static final Parcelable.Creator<MessageLoader> CREATOR = new Parcelable.Creator<MessageLoader>(){

        @Override
        public MessageLoader createFromParcel(Parcel source) {
            return new MessageLoader(source);
        }

        @Override
        public MessageLoader[] newArray(int size) {
            return new MessageLoader[size];
        }
    };

    public int getSermonIndex(Message object) {
        for(int i = 0; i < messages.size(); ++i){
            // not complete, made by sam
        }
        return -1;
    }
}
