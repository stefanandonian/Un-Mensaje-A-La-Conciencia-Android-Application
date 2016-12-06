package net.conciencia.mensajeandroid.Activities;

import android.os.Bundle;

import net.conciencia.mensajeandroid.R;

/**
 * Created by stefanandonian on 11/22/16.
 */

// DONT DO, SHOULD BE IMPLEMENTED AS A FRAGMENT
public class CaseOfTheWeekActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.case_of_the_week_layout);
    }
}
