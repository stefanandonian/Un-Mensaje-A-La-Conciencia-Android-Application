package net.conciencia.mensajeandroid.Activities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import net.conciencia.mensajeandroid.Adapters.SectionsPagerAdapter;
import net.conciencia.mensajeandroid.Objects.ParcelableMessageArrayList;
import net.conciencia.mensajeandroid.R;
import net.conciencia.mensajeandroid.ContentLoaders.RSSClient;
import net.conciencia.mensajeandroid.ContentLoaders.MessageLoader;

public class MessageViewActivity extends AppCompatActivity {

    ParcelableMessageArrayList messages;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);
        Bundle args = getIntent().getExtras();
        messages = args.getParcelable(getString(R.string.rss_extra));
        setMessageViewActionBar();
        setMessageView_ViewPagerAndPagerAdapter(args);
    }

    private void setMessageView_ViewPagerAndPagerAdapter(Bundle args) {
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this.messages);
        mViewPager = (ViewPager) findViewById(R.id.message_view_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(args.getInt(getString(R.string.message_index)));
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    private void setMessageViewActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.message_view_toolbar);
        setSupportActionBar(toolbar);
        setToolbarTitleAndTitleColor(toolbar);
        setActionBarBackArrow();
    }

    private void setToolbarTitleAndTitleColor(Toolbar toolbar) {
        toolbar.setTitle("Mensajes");
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void setActionBarBackArrow() {
        setActionBarBackArrowColor();
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }
    }

    private void setActionBarBackArrowColor() {
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }
}
