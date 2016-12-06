package net.conciencia.mensajeandroid.Fragments;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import net.conciencia.mensajeandroid.R;

public class InformationFragment extends Fragment {

    Button emailButton;
    Button facebookButton;
    Button concienciaDotNetButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View informationView = inflater.inflate(R.layout.fragment_information, container, false);

        emailButton = (Button) informationView.findViewById(R.id.concienciaEmailButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

        facebookButton = (Button) informationView.findViewById(R.id.facebookButton);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage("https://www.facebook.com/mensajeAconciencia/");
            }
        });

        concienciaDotNetButton = (Button) informationView.findViewById(R.id.concienciaWebPage);
        concienciaDotNetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage("http://www.conciencia.net/");
            }
        });
        return informationView;
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

}
