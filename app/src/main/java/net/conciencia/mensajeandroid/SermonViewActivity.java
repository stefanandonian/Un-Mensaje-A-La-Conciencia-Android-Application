package net.conciencia.mensajeandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SermonViewActivity extends AppCompatActivity implements RSSClient {
    RSSInterface rssInterface;

    public RSSInterface getRssInterface(){
        return rssInterface;
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sermon_view);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        Bundle args = getIntent().getExtras();
        rssInterface = args.getParcelable(Constants.RSS_EXTRA);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(args.getInt(Constants.SERMON_INDEX));
        mSectionsPagerAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class SermonViewFragment extends Fragment {
        RSSClient webClient;
        int sermonIndex;
        TextView titleTextView;
        TextView dateTextView;
        TextView message_contentsTextView;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SERMON_INDEX = "section_number";

        public SermonViewFragment() {
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if(context instanceof RSSClient){
                webClient = (RSSClient)context;
                displaySermon();
            }else{
                throw new UnsupportedOperationException("Invalid activity");
            }
        }

        private void displaySermon(){
            if(webClient == null
                    ||titleTextView == null
                    ||dateTextView == null
                    ||message_contentsTextView == null)
                return;
            titleTextView.setText(webClient.getRssInterface().getSermon(sermonIndex).getTitle());
            dateTextView.setText(webClient.getRssInterface().getSermon(sermonIndex).getPubDate());
            message_contentsTextView.setText(Html.fromHtml(webClient.getRssInterface().getSermon(sermonIndex).getText()));
        }

        @Override
        public void onDetach() {
            super.onDetach();
            webClient = null;
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static SermonViewFragment newInstance(int sermonIndex) {
            SermonViewFragment fragment = new SermonViewFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SERMON_INDEX, sermonIndex);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sermon, container, false);

            titleTextView = (TextView) rootView.findViewById(R.id.title);
            dateTextView = (TextView) rootView.findViewById(R.id.date);
            message_contentsTextView = (TextView) rootView.findViewById(R.id.message_content);

            sermonIndex = getArguments().getInt(ARG_SERMON_INDEX);
            displaySermon();

            //titleTextView.setText(getString(R.string.titleResource, getArguments().getInt(ARG_SERMON_INDEX)));
            //dateTextView.setText(getString(R.string.dateResource, getArguments().getInt(ARG_SERMON_INDEX)));
            //message_contentsTextView.setText(getString(R.string.contentResource, getArguments().getInt(ARG_SERMON_INDEX)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a SermonViewFragment (defined as a static inner class below).
            return SermonViewFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            if(rssInterface != null)
                return rssInterface.getSermons().size();
            return 1;
        }

        @Override
        public int getItemPosition(Object object) {
            if(object instanceof Sermon) {
                return rssInterface.getSermonIndex((Sermon) object);
            }
            return 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(true)
                throw new NullPointerException();
            return super.getPageTitle(position);
        }
/*@Override
        public CharSequence getPageTitle(int position) {
            Log.d("net.conciencia.mensaje", "Title: " + rssInterface.getSermon(position).getTitle());
            return (CharSequence)rssInterface.getSermon(position-1).getTitle();
        }//*/
    }
}
