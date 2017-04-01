package net.conciencia.mensajeandroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.conciencia.mensajeandroid.fragments.MessageViewFragment;
import net.conciencia.mensajeandroid.objects.Message;
import net.conciencia.mensajeandroid.objects.MessageList;

public class MessageViewAdapter extends FragmentPagerAdapter {

    private MessageList messages;

    public MessageViewAdapter(FragmentManager fm, MessageList messages) {
        super(fm);
        this.messages = messages;
    }

    @Override
    public Fragment getItem(int position) {
        return MessageViewFragment.newInstance(position + 1, messages);
    }

    @Override
    public int getCount() {
        return messages.getSize();
    }

    @Override
    public int getItemPosition(Object object) {
        if (object instanceof Message) {
            return messages.getMessageIndex((Message) object);
        }
        return 1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Not Implemented
        return "Not Implemented";
    }
}
