package net.conciencia.mensajeandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import net.conciencia.mensajeandroid.adapters.MainTabsAdapter;
import net.conciencia.mensajeandroid.objects.MessageList;
import net.conciencia.mensajeandroid.R;
import net.conciencia.mensajeandroid.fragments.MessageListFragment;

public class MainTabsActivity extends AppCompatActivity implements MessageListFragment.MessageListInteraction {

    ViewPager mensaje_caso_pager;
    TabLayout mensaje_caso_tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tabs);
        setVisualElements();
    }

    private void setVisualElements() {
        mensaje_caso_pager = (ViewPager)findViewById(R.id.mensaje_caso_pager);
        mensaje_caso_pager.setAdapter(new MainTabsAdapter(getSupportFragmentManager(), this));
        mensaje_caso_tablayout = (TabLayout)findViewById(R.id.main_tablayout);
        mensaje_caso_tablayout.setupWithViewPager(mensaje_caso_pager);
    }


    @Override
    public void onMessageSelected(MessageList messageList, int sermonIndex) {
        Intent intent = new Intent(MainTabsActivity.this, MessageViewActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(getString(R.string.rss_extra), messageList);
        extras.putInt(getString(R.string.message_index), sermonIndex);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
