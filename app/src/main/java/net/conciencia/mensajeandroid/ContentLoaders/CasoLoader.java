package net.conciencia.mensajeandroid.ContentLoaders;

import android.text.Html;
import android.util.Log;

import net.conciencia.mensajeandroid.Objects.CasoDeLaSemana;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class CasoLoader {

    private CasoDeLaSemana caso;
    private final String UN_MENSAJE_CASOS_FEED = "http://conciencia.net/api/casos.asmx/get?id=0";

    public static void main(String[] args) {
        CasoLoader casoLoader = new CasoLoader();
        CasoDeLaSemana caso = casoLoader.getCaso();
        caso.print();
    }

    public CasoLoader() {
        downloadXMLContent(getElementFromXML());
    }

    private Element getElementFromXML() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(UN_MENSAJE_CASOS_FEED).getDocumentElement();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }

    private void downloadXMLContent(Element xmlElement) {
        String text  = formatText(xmlElement.getElementsByTagName("text").item(0).getFirstChild().getNodeValue());
        String id    = xmlElement.getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
        Date date    = formatDate(xmlElement.getElementsByTagName("date").item(0).getFirstChild().getNodeValue());
        String title = xmlElement.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
        String topic = xmlElement.getElementsByTagName("topic").item(0).getFirstChild().getNodeValue();
        caso = new CasoDeLaSemana(id, date, title, text, topic);
    }

    public CasoDeLaSemana getCaso() {
        return caso;
    }

    private String formatText(String text) {
        return Html.fromHtml(text).toString();
    }

    private Date formatDate(String date) {
        try {
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date formattedDate = dateParser.parse(date);
            return formattedDate;
        } catch (ParseException pE) {
            Log.e("ParseException", "formatDate : either the date format or the date given was incorrect");
            return new Date();
        }
    }

}
