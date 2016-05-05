package net.conciencia.mensajeandroid;

import android.os.Parcel;
import android.os.Parcelable;

public class Sermon implements Parcelable {
    private String title;
    private String link;
    private String guid;
    private String pubDate;
    private String author;
    private String text;
    private String audioURL;
    private String videoURL;
    private String duration;

    public Sermon(String title, String link, String guid, String pubDate, String author, String text, String audioURL, String videoURL, String duration) {
        this.title       = title;
        this.link        = link;
        this.guid        = guid;
        this.pubDate     = pubDate;
        this.author      = author;
        this.text        = text;
        this.audioURL    = audioURL;
        this.videoURL    = videoURL;
        this.duration    = duration;
    }

    public Sermon(Parcel source) {
        title = source.readString();
        link = source.readString();
        guid = source.readString();
        pubDate = source.readString();
        author = source.readString();
        text = source.readString();
        audioURL = source.readString();
        videoURL = source.readString();
        duration = source.readString();
    }

    public String getTitle() {
        return this.title;
    }

    public String getLink() {
        return  this.link;
    }

    public String getGuid() {
        return this.guid;
    }

    public String getPubDate() {
        return this.pubDate;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getText() {
        return this.text;
    }

    public String getAudioURL() {
        return this.audioURL;
    }

    public String getVideoURL() {
        return this.videoURL;
    }

    public String getDuration() {
        return this.duration;
    }

    @Override
    public String toString() {
        return "Sermon{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", guid='" + guid + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", author='" + author + '\'' +
                ", text='" + text + '\'' +
                ", audioURL='" + audioURL + '\'' +
                ", videoURL='" + videoURL + '\'' +
                ", duration='" + duration + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(guid);
        dest.writeString(pubDate);
        dest.writeString(author);
        dest.writeString(text);
        dest.writeString(audioURL);
        dest.writeString(videoURL);
        dest.writeString(duration);
    }

    public static final Parcelable.Creator<Sermon> CREATOR = new Parcelable.Creator<Sermon>(){

        @Override
        public Sermon createFromParcel(Parcel source) {
            return new Sermon(source);
        }

        @Override
        public Sermon[] newArray(int size) {
            return new Sermon[size];
        }
    };

    /*public void println() {
        System.out.println("Title       = " + title);
        System.out.println("Link        = " + link);
        System.out.println("Guid        = " + guid);
        System.out.println("PubDate     = " + pubDate);
        System.out.println("Author      = " + author);
        System.out.println("Description = " + description);
        System.out.println("Audio Link  = " + audioURL);
        System.out.println("Video Link  = " + videoURL);
        System.out.println("Duration    = " + duration);
    }//*/
}

