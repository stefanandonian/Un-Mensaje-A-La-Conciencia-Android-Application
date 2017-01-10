package net.conciencia.mensajeandroid.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.conciencia.mensajeandroid.R;
import net.conciencia.mensajeandroid.Objects.Message;

import java.util.List;

public class MessageListAdapter extends ArrayAdapter<Message> {

    public MessageListAdapter(Context context, Message[] messages){
        super(context, R.layout.list_item_message, messages);
    }
    public MessageListAdapter(Context context, List<Message> messageList){
        this(context, makeArray(messageList));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        Message message = getItem(position);
        View sermonItem = layoutInflater.inflate(R.layout.list_item_message, parent, false);
        setVisualElements(message, sermonItem);
        return sermonItem;
    }

    private void setVisualElements(Message message, View sermonItem) {
        TextView titleView = (TextView)sermonItem.findViewById(R.id.messageTitle);
        titleView.setText(message.getTitle());
        TextView dateView = (TextView)sermonItem.findViewById(R.id.messageDate);
        dateView.setText(message.getPubDate());
    }

    private static Message[] makeArray(List<Message> messageList){
        Message[] messageArray = new Message[messageList.size()];
        int i = 0;
        for(Message message : messageList){
            messageArray[i++] = message;
        }
        return messageArray;
    }
}
