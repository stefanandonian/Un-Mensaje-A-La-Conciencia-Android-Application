package net.conciencia.mensajeandroid.Adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import net.conciencia.mensajeandroid.Fragments.CasoFragment;
import net.conciencia.mensajeandroid.Fragments.InformationFragment;
import net.conciencia.mensajeandroid.Fragments.MessageListFragment;
import net.conciencia.mensajeandroid.R;

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
            case 2 : return new InformationFragment();
            default:
                Log.d(context.getString(R.string.debug_TAG), "MensajeCasoAdapter received incorrect int position argument");
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0 : return context.getString(R.string.daily_message);
            case 1 : return context.getString(R.string.case_of_the_week);
            case 2 : return context.getString(R.string.information_tab_title);
            default:
                return null;
        }
    }
}
