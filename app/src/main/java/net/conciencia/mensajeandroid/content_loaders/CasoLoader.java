package net.conciencia.mensajeandroid.content_loaders;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;

import net.conciencia.mensajeandroid.R;
import net.conciencia.mensajeandroid.objects.Caso;

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
    private Context context;
    private final String UN_MENSAJE_CASOS_FEED = "http://conciencia.net/api/casos.asmx/get?id=0";
    private final String OLD_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private final String NEW_DATE_FORMAT = "MM / dd / yyyy";

    public CasoLoader(Context context) {
        this.context = context;
        downloadXMLContentAndStoreInCaso(getElementFromXML());
    }

    public Caso getCaso() {
        return caso;
    }

    private void downloadXMLContentAndStoreInCaso(Element xmlElement) {
        String text = getText(xmlElement);
        String date = getDate(xmlElement);
        String id = getId(xmlElement);
        String title = getTitle(xmlElement);
        String topic = getTopic(xmlElement);
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

    private String getText(Element xmlElement) {
        return eraseNewlineChars(xmlElement.getElementsByTagName(context.getString(R.string.caso_loader_caso_text_tag)).item(0).getFirstChild().getNodeValue());
    }

    private String formatHtmlAsPlainText(String text) {
        return fromHtml(text).toString();
    }

    private String getDate(Element xmlElement) {
        return changeDateFormat(xmlElement.getElementsByTagName(context.getString(R.string.caso_loader_date_tag)).item(0).getFirstChild().getNodeValue());
    }

    private String changeDateFormat(String oldDateString) {
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

    private String getTopic(Element xmlElement) {
        return xmlElement.getElementsByTagName(context.getString(R.string.caso_loader_topic_tag)).item(0).getFirstChild().getNodeValue();
    }

    private String getId(Element xmlElement) {
        return xmlElement.getElementsByTagName(context.getString(R.string.caso_loader_id_tag)).item(0).getFirstChild().getNodeValue();
    }

    private String getTitle(Element xmlElement) {
        return xmlElement.getElementsByTagName(context.getString(R.string.caso_loader_title_tag)).item(0).getFirstChild().getNodeValue();
    }

    private String eraseNewlineChars(String s) {
        return s.replaceAll("\n","");
    }

    @SuppressWarnings("deprecation")
    private  Spanned fromHtml(String html){
        Spanned result;
        //html = eraseNewlineChars(html);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

}
