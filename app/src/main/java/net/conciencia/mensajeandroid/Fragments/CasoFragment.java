package net.conciencia.mensajeandroid.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.conciencia.mensajeandroid.ContentLoaders.CasoLoader;
import net.conciencia.mensajeandroid.Objects.Caso;
import net.conciencia.mensajeandroid.R;

/**
 * Created by stefanandonian on 12/12/16.
 */

public class CasoFragment extends Fragment {

    TextView caso_title_id;
    TextView caso_date;
    TextView caso_text;
    Button emailButton;
    Button internetButton;

    Caso caso;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View casoView = layoutInflater.inflate(R.layout.fragment_caso, container, false);

        caso_title_id = (TextView) casoView.findViewById(R.id.caso_title_id);
        caso_date = (TextView) casoView.findViewById(R.id.caso_date);
        caso_text = (TextView) casoView.findViewById(R.id.caso_text);

        emailButton = (Button) casoView.findViewById(R.id.caso_email_button);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailToLinda();
            }
        });
        internetButton = (Button) casoView.findViewById(R.id.caso_internet_button);
        internetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage("http://www.message2conscience.com/cases.aspx");
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

    private void openWebPage(String uri) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(browserIntent);
    }

    private void updateCaso(boolean succesful) {
        if (succesful) {
            caso_title_id.setText(caso.getTitle() + " : " + caso.getId());
            caso_date.setText(caso.getDate().toString());
            caso_text.setText(caso.getText());
        }
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
