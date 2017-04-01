package net.conciencia.mensajeandroid.fragments;

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
import android.webkit.WebView;
import android.widget.TextView;

import net.conciencia.mensajeandroid.content_loaders.CasoLoader;
import net.conciencia.mensajeandroid.objects.Caso;
import net.conciencia.mensajeandroid.R;

public class CasoFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    TextView caso_title_id;
    TextView caso_date;
    //TextView caso_text;
    WebView caso_text;
    FloatingActionButton send_caso_in_email_to_friend_fab;
    SwipeRefreshLayout casoSwipeRefreshLayout;
    Caso caso;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View casoView = layoutInflater.inflate(R.layout.tab_fragment_caso, container, false);
        setTextViews(casoView);
        setEmailFloatingActionButton(casoView);
        setOnRefreshLayout(casoView);
        onRefresh();
        return casoView;
    }

    private void setOnRefreshLayout(View casoView) {
        casoSwipeRefreshLayout = (SwipeRefreshLayout) casoView.findViewById(R.id.caso_swipe_refresh_layout);
        casoSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void setTextViews(View casoView) {
        caso_title_id = (TextView) casoView.findViewById(R.id.caso_title_id);
        caso_date = (TextView) casoView.findViewById(R.id.caso_date);
        caso_text = (WebView) casoView.findViewById(R.id.caso_webview);
    }

    private void setEmailFloatingActionButton(View casoView) {
        send_caso_in_email_to_friend_fab = (FloatingActionButton) casoView.findViewById(R.id.caso_send_caso_in_email_to_friend_fab);
        send_caso_in_email_to_friend_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { sendCasoInEmailToFriend(); } });
    }

    private void sendCasoInEmailToFriend() {
        Intent emailToFriend = new Intent(Intent.ACTION_SEND);
        emailToFriend.setData(Uri.parse("mailto:"));
        emailToFriend.putExtra(Intent.EXTRA_SUBJECT, caso.getTitle() + " : " + caso.getDate());
        emailToFriend.setType("message/rfc822");
        emailToFriend.putExtra(Intent.EXTRA_TEXT, caso.getText());
        Intent chooser = Intent.createChooser(emailToFriend, getContext().getString(R.string.email_chooser_message));
        startActivity(chooser);
    }

    @Override
    public void onRefresh() {
        casoSwipeRefreshLayout.setRefreshing(true);
        UpdateCasoTask updateCaso = new UpdateCasoTask();
        updateCaso.execute();
    }

    private void onRefreshComplete() {
        casoSwipeRefreshLayout.setRefreshing(false);
        updateCasoTextViews();
    }

    private void updateCasoTextViews() {
        caso_title_id.setText(caso.getTitle() + " : " + caso.getId());
        caso_date.setText(caso.getDate());
        caso_text.loadData(caso.getText(),"text/html", "utf-8");
    }

    private void updateCasoText() {
        /*String casoText = "<html><body>"
                + "<p align=\"justify\">"
                + caso.getText()
                + "</p>"
                + "</body></html>";
        caso_text.loadData(casoText,"text/html", "utf-8");
        */
    }

    public class UpdateCasoTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            CasoLoader casoLoader = new CasoLoader(getContext());
            caso = casoLoader.getCaso();
            if (caso != null)
                return true;
            return false;
        }

        @Override
        protected void onPostExecute(Boolean successful) {
            super.onPostExecute(successful);
            onRefreshComplete();
        }
    }

}
