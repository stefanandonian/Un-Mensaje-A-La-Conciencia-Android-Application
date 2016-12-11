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

/**
 * Created by smccollum on 04.05.16.
 */
public class SermonListAdapter extends ArrayAdapter<Message> {

    public SermonListAdapter(Context context, Message[] messages){
        super(context, R.layout.sermon_list_item, messages);
    }
    public SermonListAdapter(Context context, List<Message> messageList){
        this(context, makeArray(messageList));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View sermonItem = layoutInflater.inflate(R.layout.sermon_list_item, parent, false);
        Message message = getItem(position);

        TextView titleView = (TextView)sermonItem.findViewById(R.id.sermonTitle);
        titleView.setText(message.getTitle());
        TextView dateView = (TextView)sermonItem.findViewById(R.id.sermonDate);
        dateView.setText(message.getPubDate());

        return sermonItem;
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
