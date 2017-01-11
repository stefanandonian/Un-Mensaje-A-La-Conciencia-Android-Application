package net.conciencia.mensajeandroid.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MessageList implements Parcelable {

    private ArrayList<Message> messageList;

    public MessageList(ArrayList<Message> messageList) {
        this.messageList = messageList;
    }

    private MessageList(Parcel source) {
        messageList = new ArrayList<>();
        source.readTypedList(messageList, Message.CREATOR);
    }

    public Message getMessage(int sermonIndex) {
        return messageList.get(sermonIndex-1);
    }

    public int getMessageIndex(Message object) {
        for(int i = 0; i < messageList.size(); ++i){
            if (object.getAudioURL().equals(messageList.get(i).getAudioURL()))
                return i;
        } return -1;
    }

    public int getSize() {
        return messageList.size();
    }

    public Message[] getMessagesAsArray() {
        Message[] messageArray = new Message[messageList.size()];
        int i = 0;
        for(Message message : messageList) {
            messageArray[i++] = message;
        }
        return messageArray;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(messageList);
    }

    public static final Parcelable.Creator<MessageList> CREATOR = new Parcelable.Creator<MessageList>(){

        @Override
        public MessageList createFromParcel(Parcel source) {
            return new MessageList(source);
        }

        @Override
        public MessageList[] newArray(int size) {
            return new MessageList[size];
        }
    };
}
