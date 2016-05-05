package net.conciencia.mensajeandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by smccollum on 04.05.16.
 */
public class SermonListAdapter extends ArrayAdapter<Sermon> {

    public SermonListAdapter(Context context, Sermon[] sermons){
        super(context, R.layout.sermon_list_item, sermons);
    }
    public SermonListAdapter(Context context, List<Sermon> sermonList){
        this(context, makeArray(sermonList));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View sermonItem = layoutInflater.inflate(R.layout.sermon_list_item, parent, false);
        Sermon sermon = getItem(position);

        TextView titleView = (TextView)sermonItem.findViewById(R.id.sermonTitle);
        titleView.setText(sermon.getTitle());
        TextView dateView = (TextView)sermonItem.findViewById(R.id.sermonDate);
        dateView.setText(sermon.getPubDate());

        return sermonItem;
    }

    private static Sermon[] makeArray(List<Sermon> sermonList){
        Sermon[] sermonArray = new Sermon[sermonList.size()];
        int i = 0;
        for(Sermon sermon : sermonList){
            sermonArray[i++] = sermon;
        }
        return sermonArray;
    }
}
