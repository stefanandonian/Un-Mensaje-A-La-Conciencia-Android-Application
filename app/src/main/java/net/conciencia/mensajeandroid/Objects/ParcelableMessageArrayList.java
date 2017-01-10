package net.conciencia.mensajeandroid.Objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ParcelableMessageArrayList implements Parcelable {

    private ArrayList<Message> messages;

    public ParcelableMessageArrayList(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ParcelableMessageArrayList(Parcel source) {}

    public Message getMessage(int sermonIndex){
        return messages.get(sermonIndex-1);
    }

    public int getSize() {
        return messages.size();
    }

    public int getMessageIndex(Message object) {
        for(int i = 0; i < messages.size(); ++i){
            if (object.getAudioURL().equals(messages.get(i).getAudioURL()))
                return i;
        }
        return -1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(messages);
    }

    public static final Parcelable.Creator<ParcelableMessageArrayList> CREATOR = new Parcelable.Creator<ParcelableMessageArrayList>(){

        @Override
        public ParcelableMessageArrayList createFromParcel(Parcel source) {
            return new ParcelableMessageArrayList(source);
        }

        @Override
        public ParcelableMessageArrayList[] newArray(int size) {
            return new ParcelableMessageArrayList[size];
        }
    };
}
