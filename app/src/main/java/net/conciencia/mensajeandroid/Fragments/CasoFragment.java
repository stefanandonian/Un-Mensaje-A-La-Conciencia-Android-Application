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

public class CasoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    TextView caso_title_id;
    TextView caso_date;
    TextView caso_text;
    FloatingActionButton send_caso_in_email_to_friend_fab;

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

        send_caso_in_email_to_friend_fab = (FloatingActionButton) casoView.findViewById(R.id.caso_send_caso_in_email_to_friend_fab);
        send_caso_in_email_to_friend_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCasoInEmailToFriend();
            }
        });
        UpdateCasoTask updateCasoTask = new UpdateCasoTask();
        updateCasoTask.execute();
        return casoView;
    }

    private void sendCasoInEmailToFriend() {
        Intent emailToFriend = new Intent(Intent.ACTION_SEND);
        emailToFriend.setData(Uri.parse("mailto:"));
        emailToFriend.putExtra(Intent.EXTRA_SUBJECT, caso.getTitle() + " : " + caso.getDate());
        emailToFriend.setType("message/rfc822");
        emailToFriend.putExtra(Intent.EXTRA_TEXT, caso.getText());
        Intent chooser = Intent.createChooser(emailToFriend, getContext().getString(R.string.send_email_toast_message));
        startActivity(chooser);
    }

    private void updateCaso(boolean succesful) {
        if (succesful) {
            caso_title_id.setText(caso.getTitle() + " : " + caso.getId());
            caso_date.setText(caso.getDate());
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
