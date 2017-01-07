package net.conciencia.mensajeandroid.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.conciencia.mensajeandroid.ContentLoaders.CasoLoader;
import net.conciencia.mensajeandroid.Objects.Caso;
import net.conciencia.mensajeandroid.R;

/**
 * Created by stefanandonian on 12/12/16.
 */

public class CasoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    TextView caso_title_id;
    TextView caso_date;
    TextView caso_text;
    FloatingActionButton send_email_to_Linda_fab;

    Caso caso;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View casoView = layoutInflater.inflate(R.layout.tab_fragment_caso, container, false);
        caso_title_id = (TextView) casoView.findViewById(R.id.caso_title_id);
        caso_date = (TextView) casoView.findViewById(R.id.caso_date);
        caso_text = (TextView) casoView.findViewById(R.id.caso_text);

        send_email_to_Linda_fab = (FloatingActionButton) casoView.findViewById(R.id.caso_send_email_to_Linda_fab);
        send_email_to_Linda_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailToLinda();
            }
        });
        UpdateCasoTask updateCasoTask = new UpdateCasoTask();
        updateCasoTask.execute();
        return casoView;
    }

    // Needs more implementation
    private void sendEmailToLinda() {
        Intent email = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "linda@conciencia.net", null));
        startActivity(Intent.createChooser(email, "Send mail..."));
    }

    private void updateCaso(boolean succesful) {
        if (succesful) {
            caso_title_id.setText(caso.getTitle() + " : " + caso.getId());
            caso_date.setText(caso.getDate().toString());
            caso_text.setText(caso.getText());
        }
    }

    @Override
    public void onRefresh() {
        UpdateCasoTask updateCaso = new UpdateCasoTask();
        updateCaso.execute();
    }


    public class UpdateCasoTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            CasoLoader casoLoader = new CasoLoader();
            caso = casoLoader.getCaso();
            if (caso != null)
                return true;
            return false;
        }

        @Override
        protected void onPostExecute(Boolean successful) {
            super.onPostExecute(successful);
            updateCaso(successful);
        }
    }

}
