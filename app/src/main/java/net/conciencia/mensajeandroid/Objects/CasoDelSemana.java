package net.conciencia.mensajeandroid.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by stefanandonian on 12/8/16.
 */

public class CasoDelSemana implements Parcelable {

    public static final Creator<CasoDelSemana> CREATOR = new Parcelable.Creator<CasoDelSemana>(){

        @Override
        public CasoDelSemana createFromParcel(Parcel source) {
            return new CasoDelSemana(source);
        }

        @Override
        public CasoDelSemana[] newArray(int size) {
            return new CasoDelSemana[size];
        }
    };

    private String id;
    private String date;
    private String title;
    private String text;
    private String topic;

    public CasoDelSemana(Parcel source) {
        this.id = source.readString();
        this.date = source.readString();
        this.title = source.readString();
        this.text = source.readString();
        this.topic = source.readString();
    }

    public CasoDelSemana(String id, String date, String title, String text, String topic) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(date);
        parcel.writeString(title);
        parcel.writeString(text);
        parcel.writeString(topic);
    }
}
