package net.conciencia.mensajeandroid.Adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.conciencia.mensajeandroid.Fragments.CasoFragment;
import net.conciencia.mensajeandroid.Fragments.MessageListFragment;
import net.conciencia.mensajeandroid.R;

/**
 * Created by stefanandonian on 12/19/16.
 */

public class MensajeCasoAdapter extends FragmentPagerAdapter {

    Context context;

    public MensajeCasoAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0 : return new MessageListFragment();
            case 1 : return new CasoFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==1)
            return context.getString(R.string.daily_message);
        return context.getString(R.string.case_of_the_week);
    }
}
