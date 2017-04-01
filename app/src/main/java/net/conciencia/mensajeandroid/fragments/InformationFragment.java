package net.conciencia.mensajeandroid.fragments;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.conciencia.mensajeandroid.R;

public class InformationFragment extends Fragment {

    private FloatingActionButton send_email_to_Info_fab;
    private FloatingActionButton facebook_fab;
    private FloatingActionButton conciencia_webpage_fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View informationView = inflater.inflate(R.layout.tab_fragment_information, container, false);
        setUpFloatingActionButtons(informationView);
        return informationView;
    }

    private void setUpFloatingActionButtons(View informationView) {
        setUpConcienciaWebpageFloatingActionButton(informationView);
        setUpEmailFloatingActionButton(informationView);
        setUpFacebookFloatingActionButton(informationView);
    }

    private void setUpFacebookFloatingActionButton(View informationView) {
        facebook_fab = (FloatingActionButton) informationView.findViewById(R.id.info_facebook_fab);
        facebook_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(getString(R.string.conciencia_facebook_url));
            }
        });
    }

    private void setUpEmailFloatingActionButton(View informationView) {
        send_email_to_Info_fab = (FloatingActionButton) informationView.findViewById(R.id.send_email_to_Info_fab);
        send_email_to_Info_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmailToUnMensajeALaConciencia();
            }
        });

    }

    private void setUpConcienciaWebpageFloatingActionButton(View informationView) {
        conciencia_webpage_fab = (FloatingActionButton) informationView.findViewById(R.id.info_conciencia_webpage_fab);
        conciencia_webpage_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(getString(R.string.conciencia_website_url));
            }
        });
    }

    private void sendEmailToUnMensajeALaConciencia() {
        Intent emailConcienciaHoy = new Intent(Intent.ACTION_SEND);
        emailConcienciaHoy.setData(Uri.parse("mailto:"));
        emailConcienciaHoy.putExtra(Intent.EXTRA_EMAIL, new String[]{ getString(R.string.conciencia_email_address)});
        emailConcienciaHoy.setType("message/rfc822");
        startActivity(Intent.createChooser(emailConcienciaHoy, getString(R.string.email_chooser_message)));
    }

    private void openWebPage(String uri) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(browserIntent);
    }

}
