package net.conciencia.mensajeandroid.ContentLoaders;

import android.content.Context;

import net.conciencia.mensajeandroid.Objects.Message;
import net.conciencia.mensajeandroid.Objects.ParcelableMessageArrayList;
import net.conciencia.mensajeandroid.R;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MessageLoader {

    private ParcelableMessageArrayList parcelable_messages;
    private Context context;

    public MessageLoader(Context context){
        this.context = context;
        parcelable_messages = loadMessagesFromUnMensajeALaConcienciaRssFeed();
    }

    public ParcelableMessageArrayList getParcelable_messages() {
        return parcelable_messages;
    }

    public ParcelableMessageArrayList loadMessagesFromUnMensajeALaConcienciaRssFeed(){
        NodeList rssFeedNodeList = getNodeListFromXML();
        ArrayList<Message> messages = getArrayMessageArrayListFromNodeList(rssFeedNodeList);
        return new ParcelableMessageArrayList(messages);
    }

    // w3c.dom tool returns an itemized version of Un Mensaje's RSS Feed for
    // Messages in an object called a NodeList from the w3c.dom library
    private NodeList getNodeListFromXML() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(context.getString(R.string.un_mensaje_rss_feed)).getDocumentElement().getElementsByTagName("item");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ArrayList<Message> getArrayMessageArrayListFromNodeList(NodeList xmlNodeList){
        ArrayList<Message> parsedMessages = new ArrayList<>();
        for(int i = 0 ; i < xmlNodeList.getLength();i++) {
            Element element = (Element)xmlNodeList.item(i);
            Message message = getMessageFromElement(element);
            parsedMessages.add(message);
        }
        return parsedMessages;
    }

    private Message getMessageFromElement(Element messageElement) {
        String title       = getTitleFromRssFeed(messageElement);
        String link        = getLinkFromRssFeed(messageElement);
        String guid        = getGuidFromRssFeed(messageElement);
        String pubDate     = getPubDateFromRssFeed(messageElement);
        String author      = getAuthorFromRssFeed(messageElement);
        String description = getDescriptionFromRssFeed(messageElement);
        String audioURL    = getAudioURLFromRssFeed(messageElement);
        String videoURL    = getVideoURLFromRssFeed(messageElement);
        String duration    = getDurationFromRssFeed(messageElement);
        return new Message(title, link, guid, pubDate, author, description, audioURL, videoURL, duration);
    }

    private String getMessageAttributeByTagNameFromElement(Element itemElement, String tagName) {
        return itemElement.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
    }

    private String getTitleFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "title");
    }

    private String getLinkFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "link");
    }

    private String getGuidFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "guid");
    }

    private String getPubDateFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "pubDate");
    }

    private String getAuthorFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "author");
    }

    private String getDescriptionFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "description");
    }

    private String getDurationFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, "itunes:duration");
    }

    private String getAudioOrVideoUrlFromRssFeed(Element itemElement, int audioURLOrVideoURLndex) {
        return itemElement.getElementsByTagName("enclosure").item(audioURLOrVideoURLndex).getAttributes().item(0).getNodeValue();
    }

    private String getVideoURLFromRssFeed(Element messageElement) {
        return getAudioOrVideoUrlFromRssFeed(messageElement, 1);
    }

    private String getAudioURLFromRssFeed(Element messageElement) {
        return getAudioOrVideoUrlFromRssFeed(messageElement, 0);
    }
}
