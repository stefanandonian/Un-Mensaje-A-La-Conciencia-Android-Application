package net.conciencia.mensajeandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity implements SermonListFragment.SermonListInteraction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    @Override
    public void onSermonSelected(RSSInterface rssInterface, int sermonIndex) {
        //todo
        //Toast.makeText(this, String.format("Sermon %s (%s) selected", sermon.getTitle(), sermon.getPubDate()), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(HomePageActivity.this, SermonViewActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(Constants.RSS_EXTRA, rssInterface);
        extras.putInt(Constants.SERMON_INDEX, sermonIndex);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
