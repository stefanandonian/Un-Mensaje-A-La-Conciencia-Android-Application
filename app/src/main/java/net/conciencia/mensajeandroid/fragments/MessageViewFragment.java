package net.conciencia.mensajeandroid.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.conciencia.mensajeandroid.objects.Message;
import net.conciencia.mensajeandroid.objects.MessageList;
import net.conciencia.mensajeandroid.R;

public class MessageViewFragment extends Fragment {

    MessageList messages;
    int messageIndex;

    private static final String ARG_MESSAGE_INDEX = "section_number";
    private static final String ARG_MESSAGES = "parcelable_messages";

    TextView titleTextView;
    TextView dateTextView;
    TextView message_contentsTextView;

    FloatingActionButton video_fab;
    FloatingActionButton audio_fab;
    FloatingActionButton send_mensaje_in_email_to_friend_fab;

    public static MessageViewFragment newInstance(int sermonIndex, MessageList messages) {
        MessageViewFragment fragment = new MessageViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MESSAGE_INDEX, sermonIndex);
        args.putParcelable(ARG_MESSAGES, messages);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_message_view, container, false);
        setFloatingActionButtons(rootView);
        getMessageIndexAndMessagesFromArguments();
        setTextViewsAndFillWithMessageData(rootView, messages.getMessage(messageIndex));
        return rootView;
    }

    private void getMessageIndexAndMessagesFromArguments() {
        messageIndex = getArguments().getInt(ARG_MESSAGE_INDEX);
        messages = getArguments().getParcelable(ARG_MESSAGES);
    }

    private void setTextViewsAndFillWithMessageData(View rootView, Message message) {
        setTextViews(rootView);
        fillTextViewsWithMessageData(message);
    }

    private void setTextViews(View rootView) {
        titleTextView = (TextView) rootView.findViewById(R.id.message_view_title);
        dateTextView = (TextView) rootView.findViewById(R.id.message_view_date);
        message_contentsTextView = (TextView) rootView.findViewById(R.id.message_view_message_text);
    }

    private void fillTextViewsWithMessageData(Message message) {
        titleTextView.setText(message.getTitle());
        dateTextView.setText(message.getPubDate());
        message_contentsTextView.setText(Html.fromHtml(message.getText()).toString());
    }

    private void setFloatingActionButtons(View rootView) {
        setAudioFloatingActionButton(rootView);
        setVideoFloatingActionButton(rootView);
        setEmailFloatingActionButton(rootView);
    }

    private void setAudioFloatingActionButton(View rootView) {
        audio_fab = (FloatingActionButton) rootView.findViewById(R.id.message_view_audio_fab);
        audio_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });
        audio_fab.setEnabled(true);
    }

    private void setVideoFloatingActionButton(View rootView) {
        video_fab = (FloatingActionButton) rootView.findViewById(R.id.message_view_video_fab);
        video_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideo();
            }
        });
        video_fab.setEnabled(true);
    }

    private void setEmailFloatingActionButton(View rootView) {
        send_mensaje_in_email_to_friend_fab = (FloatingActionButton) rootView.findViewById(R.id.message_view_email_fab);
        send_mensaje_in_email_to_friend_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { sendMensajeInEmailToFriend(); } });
    }

    private void playAudio() {
        playMedia(messages.getMessage(messageIndex).getAudioURL());
    }

    private void playVideo() {
        playMedia(messages.getMessage(messageIndex).getVideoURL());
    }

    private void playMedia(String url) {
        getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    private void sendMensajeInEmailToFriend() {
        Intent emailToFriend = new Intent(Intent.ACTION_SEND);
        emailToFriend.setData(Uri.parse("mailto:"));
        emailToFriend.putExtra(Intent.EXTRA_SUBJECT, messages.getMessage(messageIndex).getTitle());
        emailToFriend.setType("message/rfc822");
        emailToFriend.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(messages.getMessage(messageIndex).getText()));
        Intent chooser = Intent.createChooser(emailToFriend, getContext().getString(R.string.email_chooser_message));
        startActivity(chooser);
    }
}
