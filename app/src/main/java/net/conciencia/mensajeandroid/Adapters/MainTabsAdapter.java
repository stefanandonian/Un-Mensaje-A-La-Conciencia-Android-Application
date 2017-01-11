package net.conciencia.mensajeandroid.adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import net.conciencia.mensajeandroid.fragments.CasoFragment;
import net.conciencia.mensajeandroid.fragments.InformationFragment;
import net.conciencia.mensajeandroid.fragments.MessageListFragment;
import net.conciencia.mensajeandroid.R;

public class MainTabsAdapter extends FragmentPagerAdapter {

    private Context context;

    public MainTabsAdapter(FragmentManager fm, Context context) {
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
                Log.d(context.getString(R.string.debug_TAG), context.getString(R.string.main_tabs_adapter_getItem_error_explanation));
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
            case 0 : return context.getString(R.string.daily_message_tab);
            case 1 : return context.getString(R.string.case_of_the_week_tab);
            case 2 : return context.getString(R.string.information_tab);
            default:
                return null;
        }
    }
}
