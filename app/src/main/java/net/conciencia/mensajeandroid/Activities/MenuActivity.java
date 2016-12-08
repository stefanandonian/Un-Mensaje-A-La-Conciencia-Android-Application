package net.conciencia.mensajeandroid.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import net.conciencia.mensajeandroid.R;

/**
 * Created by stefanandonian on 12/3/16.
 */

public abstract class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.sermonview_toolbar);
        setSupportActionBar(toolbar);
    }

}
