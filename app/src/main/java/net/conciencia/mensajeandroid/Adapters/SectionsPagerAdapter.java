package net.conciencia.mensajeandroid.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.conciencia.mensajeandroid.ContentLoaders.MessageLoader;
import net.conciencia.mensajeandroid.Fragments.MessageViewFragment;
import net.conciencia.mensajeandroid.Objects.Message;
import net.conciencia.mensajeandroid.Objects.ParcelableMessageArrayList;

/**
 * Created by stefanandonian on 1/9/17.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private ParcelableMessageArrayList messages;

    public SectionsPagerAdapter(FragmentManager fm, ParcelableMessageArrayList messages) {
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
        if (true)
            throw new NullPointerException();
        return super.getPageTitle(position);
    }
}
