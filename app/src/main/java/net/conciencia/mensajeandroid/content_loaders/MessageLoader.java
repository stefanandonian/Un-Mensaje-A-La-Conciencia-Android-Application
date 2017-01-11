package net.conciencia.mensajeandroid.content_loaders;

import android.content.Context;

import net.conciencia.mensajeandroid.objects.Message;
import net.conciencia.mensajeandroid.objects.MessageList;
import net.conciencia.mensajeandroid.R;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MessageLoader {

    MessageList messageList;
    private Context context;
    private final String OLD_DATE_FORMAT = "E, dd MMM yyyy HH:mm:ss Z";
    private final String NEW_DATE_FORMAT = "MM / dd / yyyy";

    public MessageLoader(Context context) {
        this.context = context;
        messageList = loadMessagesFromUnMensajeALaConcienciaRssFeed();
    }

    public MessageList getMessageList() {
        return messageList;
    }

    private MessageList loadMessagesFromUnMensajeALaConcienciaRssFeed(){
        NodeList rssFeedNodeList = getNodeListFromXML();
        ArrayList<Message> messages = getArrayMessageArrayListFromNodeList(rssFeedNodeList);
        return new MessageList(messages);
    }

    // w3c.dom tool returns an itemized version of Un Mensaje's RSS Feed for
    // Messages in an object called a NodeList from the w3c.dom library
    private NodeList getNodeListFromXML() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(context.getString(R.string.un_mensaje_message_feed)).getDocumentElement().getElementsByTagName("item");
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
        String pubDate     = changeDateStringFormat(getPubDateFromRssFeed(messageElement));
        String author      = getAuthorFromRssFeed(messageElement);
        String description = getDescriptionFromRssFeed(messageElement);
        String audioURL    = getAudioURLFromRssFeed(messageElement);
        String videoURL    = getVideoURLFromRssFeed(messageElement);
        String duration    = getDurationFromRssFeed(messageElement);
        return new Message(title, link, guid, pubDate, author, description, audioURL, videoURL, duration);
    }

    private String changeDateStringFormat(String oldDateString) {
        try {
            SimpleDateFormat dateFormatConverter = new SimpleDateFormat(this.OLD_DATE_FORMAT, Locale.US);
            Date temporaryDateContainer = dateFormatConverter.parse(oldDateString);
            dateFormatConverter.applyPattern(this.NEW_DATE_FORMAT);
            return dateFormatConverter.format(temporaryDateContainer);
        } catch (ParseException pE) {
            pE.printStackTrace();
            return "";
        }
    }

    private String getMessageAttributeByTagNameFromElement(Element itemElement, String tagName) {
        return itemElement.getElementsByTagName(tagName).item(0).getFirstChild().getNodeValue();
    }

    private String getTitleFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, context.getString(R.string.message_loader_title_tag));
    }

    private String getLinkFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, context.getString(R.string.message_loader_link_tag));
    }

    private String getGuidFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, context.getString(R.string.message_loader_guid_tag));
    }

    private String getPubDateFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, context.getString(R.string.message_loader_date_tag));
    }

    private String getAuthorFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, context.getString(R.string.message_loader_author_tag));
    }

    private String getDescriptionFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, context.getString(R.string.message_loader_description_tag));
    }

    private String getDurationFromRssFeed(Element messageElement) {
        return getMessageAttributeByTagNameFromElement(messageElement, context.getString(R.string.message_loader_duration_tag));
    }

    private String getAudioOrVideoUrlFromRssFeed(Element itemElement, int audioUrlOrVideoUrlIndex) {
        return itemElement.getElementsByTagName(context.getString(R.string.message_loader_audio_and_video_tag)).item(audioUrlOrVideoUrlIndex).getAttributes().item(0).getNodeValue();
    }

    private String getVideoURLFromRssFeed(Element messageElement) {
        return getAudioOrVideoUrlFromRssFeed(messageElement, 1);
    }

    private String getAudioURLFromRssFeed(Element messageElement) {
        return getAudioOrVideoUrlFromRssFeed(messageElement, 0);
    }
}
