package net.conciencia.mensajeandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.conciencia.mensajeandroid.R;
import net.conciencia.mensajeandroid.ContentLoaders.RSSClient;
import net.conciencia.mensajeandroid.ContentLoaders.MessageLoader;
import net.conciencia.mensajeandroid.Objects.Message;

public class MessageViewActivity extends AppCompatActivity implements RSSClient {
    MessageLoader messageLoader;

    public MessageLoader getMessageLoader(){
        return messageLoader;
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
        setContentView(R.layout.activity_message_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.message_view_toolbar);
        toolbar.setTitle("Mensajes");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar);


        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle args = getIntent().getExtras();
        messageLoader = args.getParcelable(getString(R.string.rss_extra));

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(args.getInt(getString(R.string.message_index)));
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MessageViewFragment extends Fragment {
        RSSClient webClient;
        int sermonIndex;
        TextView titleTextView;
        TextView dateTextView;
        TextView message_contentsTextView;

        FloatingActionButton video_fab;
        FloatingActionButton audio_fab;
        FloatingActionButton send_mensaje_in_email_to_friend_fab;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SERMON_INDEX = "section_number";

        public MessageViewFragment() {
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
            titleTextView.setText(webClient.getMessageLoader().getSermon(sermonIndex).getTitle());
            dateTextView.setText(webClient.getMessageLoader().getSermon(sermonIndex).getPubDate());
            message_contentsTextView.setText(Html.fromHtml(webClient.getMessageLoader().getSermon(sermonIndex).getText()).toString());
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
        public static MessageViewFragment newInstance(int sermonIndex) {
            MessageViewFragment fragment = new MessageViewFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SERMON_INDEX, sermonIndex);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_message_view, container, false);

            titleTextView = (TextView) rootView.findViewById(R.id.title);
            dateTextView = (TextView) rootView.findViewById(R.id.date);
            message_contentsTextView = (TextView) rootView.findViewById(R.id.message_content);

            audio_fab = (FloatingActionButton) rootView.findViewById(R.id.message_view_audio_fab);
            audio_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playAudio();
                }
            });
            audio_fab.setEnabled(true);

            video_fab = (FloatingActionButton) rootView.findViewById(R.id.message_view_video_fab);
            video_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playVideo();
                }
            });
            video_fab.setEnabled(true);


            send_mensaje_in_email_to_friend_fab = (FloatingActionButton) rootView.findViewById(R.id.message_view_email_fab);
            send_mensaje_in_email_to_friend_fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMensajeInEmailToFriend();
                }
            });

            sermonIndex = getArguments().getInt(ARG_SERMON_INDEX);
            displaySermon();
            return rootView;
        }

        private void playAudio() {
            if(webClient == null)
                return;
            playMedia(webClient.getMessageLoader().getSermon(sermonIndex).getAudioURL());
        }
        private void playVideo(){
            if(webClient == null)
                return;
            playMedia(webClient.getMessageLoader().getSermon(sermonIndex).getVideoURL());
        }
        private void playMedia(String url){
            Log.d("net.conciencia", "URL: " + url);
            getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }

        private void sendMensajeInEmailToFriend(){
            Intent emailToFriend = new Intent(Intent.ACTION_SEND);
            emailToFriend.setData(Uri.parse("mailto:"));

            emailToFriend.putExtra(Intent.EXTRA_SUBJECT, webClient.getMessageLoader().getSermon(sermonIndex).getTitle());
            emailToFriend.setType("message/rfc822");
            emailToFriend.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(webClient.getMessageLoader().getSermon(sermonIndex).getText()));

            Intent chooser = Intent.createChooser(emailToFriend, "Enviar por Correo");
            startActivity(chooser);
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
            return MessageViewFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 7 total pages.
            if (messageLoader != null)
                return messageLoader.getMessages().size();
            return 1;
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof Message) {
                return messageLoader.getSermonIndex((Message) object);
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
}
