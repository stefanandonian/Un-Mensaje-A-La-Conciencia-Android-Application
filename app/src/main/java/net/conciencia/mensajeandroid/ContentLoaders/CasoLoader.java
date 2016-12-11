package net.conciencia.mensajeandroid.ContentLoaders;

import android.os.Parcelable;

import net.conciencia.mensajeandroid.Objects.CasoDelSemana;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class CasoLoader {

    private static final String UN_MENSAJE_CASOS_FEED = "http://conciencia.net/api/casos.asmx/get?id=0";

    public static void main(String[] args) {
        System.out.println(getCaso().getText());
    }

    public static Element getElementFromXML() {
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

    public static CasoDelSemana getCaso() {
        String text = getElementFromXML().getElementsByTagName("text").item(0).getFirstChild().getNodeValue();
        String id   = getElementFromXML().getElementsByTagName("id").item(0).getFirstChild().getNodeValue();
        String date = getElementFromXML().getElementsByTagName("date").item(0).getFirstChild().getNodeValue();
        String title = getElementFromXML().getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
        String topic = getElementFromXML().getElementsByTagName("topic").item(0).getFirstChild().getNodeValue();
        CasoDelSemana casoDelSemana = new CasoDelSemana(id, date, title, text, topic);
        return casoDelSemana;
    }

}
