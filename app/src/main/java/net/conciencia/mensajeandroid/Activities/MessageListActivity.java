package net.conciencia.mensajeandroid.Activities;

import android.content.Intent;
import android.os.Bundle;

import net.conciencia.mensajeandroid.Constants;
import net.conciencia.mensajeandroid.R;
import net.conciencia.mensajeandroid.ContentLoaders.RSSInterface;
import net.conciencia.mensajeandroid.Fragments.SermonListFragment;

public class MessageListActivity extends BaseActivity implements SermonListFragment.SermonListInteraction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
    }


    @Override
    public void onSermonSelected(RSSInterface rssInterface, int sermonIndex) {
        //todo
        //Toast.makeText(this, String.format("Sermon %s (%s) selected", sermon.getTitle(), sermon.getPubDate()), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MessageListActivity.this, SermonViewActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(Constants.RSS_EXTRA, rssInterface);
        extras.putInt(Constants.SERMON_INDEX, sermonIndex);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
