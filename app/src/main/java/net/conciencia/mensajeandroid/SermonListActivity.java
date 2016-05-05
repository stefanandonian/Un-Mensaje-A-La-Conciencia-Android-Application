package net.conciencia.mensajeandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SermonListActivity extends AppCompatActivity implements SermonFragment.SermonListInteraction {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sermon_list);
    }

    @Override
    public void onSermonSelected(RSSInterface rssInterface, Sermon sermon) {
        //todo
        Toast.makeText(this, String.format("Sermon %s (%s) selected", sermon.getTitle(), sermon.getPubDate()), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
    }
}
