package net.conciencia.mensajeandroid.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.conciencia.mensajeandroid.ContentLoaders.CasoLoader;
import net.conciencia.mensajeandroid.Objects.CasoDelSemana;
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

    CasoLoader casoLoader;
    CasoDelSemana casoDelSemana;

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
                sendEmail();
            }
        });
        internetButton = (Button) casoView.findViewById(R.id.caso_email_button);
        internetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage("http://www.message2conscience.com/cases.aspx");
            }
        });

        casoLoader = new CasoLoader();
        UpdateCasoTask updateCasoTask = new UpdateCasoTask();
        updateCasoTask.execute();

        return casoView;
    }

    // Needs more implementation
    private void sendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"Recipient"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Message Body");
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending...", "");
        } catch (Exception ex) {
            //Toast.makeText(InformationFragment.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void openWebPage(String uri) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(browserIntent);
    }

    private void updateCaso(boolean succesful) {
        if (succesful) {
            caso_title_id.setText(casoDelSemana.getTitle() + " : " + casoDelSemana.getDate());
            caso_date.setText(casoDelSemana.getDate());
            caso_text.setText(casoDelSemana.getText());
        }
    }

    public class UpdateCasoTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            casoDelSemana = casoLoader.getCaso();
            if (casoDelSemana != null)
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
