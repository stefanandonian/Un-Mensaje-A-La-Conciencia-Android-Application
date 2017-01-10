package net.conciencia.mensajeandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import net.conciencia.mensajeandroid.Adapters.MensajeCasoAdapter;
import net.conciencia.mensajeandroid.Objects.ParcelableMessageList;
import net.conciencia.mensajeandroid.R;
import net.conciencia.mensajeandroid.Fragments.MessageListFragment;

public class MainTabsActivity extends AppCompatActivity implements MessageListFragment.MessageListInteraction {

    ViewPager mensaje_caso_pager;
    TabLayout mensaje_caso_tablayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        setVisualElements();
    }

    private void setVisualElements() {
        mensaje_caso_pager = (ViewPager)findViewById(R.id.mensaje_caso_pager);
        mensaje_caso_pager.setAdapter(new MensajeCasoAdapter(getSupportFragmentManager(), this));
        mensaje_caso_tablayout = (TabLayout)findViewById(R.id.mensaje_caso_tablayout);
        mensaje_caso_tablayout.setupWithViewPager(mensaje_caso_pager);
    }


    @Override
    public void onSermonSelected(ParcelableMessageList messageList, int sermonIndex) {
        Intent intent = new Intent(MainTabsActivity.this, MessageViewActivity.class);
        Bundle extras = new Bundle();
        System.out.println("Item size " + messageList.getSize());
        extras.putParcelable(getString(R.string.rss_extra), messageList);
        extras.putInt(getString(R.string.message_index), sermonIndex);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
