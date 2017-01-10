package net.conciencia.mensajeandroid.ContentLoaders;

import android.text.Html;
import android.util.Log;

import net.conciencia.mensajeandroid.Objects.Caso;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class CasoLoader {

    private Caso caso;
    private final String UN_MENSAJE_CASOS_FEED = "http://conciencia.net/api/casos.asmx/get?id=0";
    private final String OLD_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private final String NEW_DATE_FORMAT = "MM / dd / yyyy";

    public CasoLoader() {
        downloadXMLContentAndStoreInClassCaso(getElementFromXML());
    }

    public Caso getCaso() {
        return caso;
    }

    private void downloadXMLContentAndStoreInClassCaso(Element xmlElement) {
        String text = formatHtmlAsPlainText(xmlElement.getElementsByTagName("text").item(0).getFirstChild().getNodeValue());
        String date = changeDateFormat(xmlElement.getElementsByTagName("date").item(0).getFirstChild().getNodeValue());
        String id = xmlElement.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
        String title = xmlElement.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
        String topic = xmlElement.getElementsByTagName("topic").item(0).getFirstChild().getNodeValue();
        caso = new Caso(id, date, title, text, topic);
    }

    private Element getElementFromXML() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(UN_MENSAJE_CASOS_FEED).getDocumentElement();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String formatHtmlAsPlainText(String text) {
        return Html.fromHtml(text).toString();
    }

    private String changeDateFormat(String oldDateString) {
        try {
            SimpleDateFormat dateFormatConverter = new SimpleDateFormat(this.OLD_DATE_FORMAT, Locale.US);
            Date temporaryDateContainer = dateFormatConverter.parse(oldDateString);
            dateFormatConverter.applyPattern(this.NEW_DATE_FORMAT);
            return dateFormatConverter.format(temporaryDateContainer);
        } catch (ParseException pE) {
            Log.e("ParseException", "changeDateFormat : either the date format or the date string was invalid");
            pE.printStackTrace();
        }
        return "";
    }

}
