package net.conciencia.mensajeandroid.objects;

public class Caso  {

    private String id;
    private String date;
    private String title;
    private String text;
    private String topic;

    public Caso(String id, String date, String title, String text, String topic) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.text = text;
        this.topic = topic;
    }

    public String getId() {
        return this.id;
    }

    public String getDate() {
        return this.date;
    }

    public String getTitle() {
        return this.title;
    }

    public String getText() {
        return this.text;
    }

    public String getTopic() {
        return this.topic;
    }

}
