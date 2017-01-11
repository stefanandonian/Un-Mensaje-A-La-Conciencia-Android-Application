package net.conciencia.mensajeandroid.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import net.conciencia.mensajeandroid.adapters.MessageListAdapter;
import net.conciencia.mensajeandroid.content_loaders.MessageLoader;
import net.conciencia.mensajeandroid.objects.MessageList;
import net.conciencia.mensajeandroid.R;

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the {@link MessageListInteraction} interface.
 */
public class MessageListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private MessageListInteraction mListener;
    boolean isRefreshing;

    SwipeRefreshLayout messageListSwipeRefreshLayout;
    ListView messageListView;
    ListAdapter mListAdapter;
    MessageList messageList;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MessageListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_message_list, container, false);
        messageListView = (ListView)rootView.findViewById(R.id.lv_message_list);
        setOnRefreshLayout(rootView);
        onRefresh();
        return rootView;
    }

    private void setOnRefreshLayout(View rootView) {
        messageListSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.messagelist_SwipeRefreshLayout);
        messageListSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MessageListInteraction) {
            mListener = (MessageListInteraction) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement MessageListInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        messageListSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mListener.onMessageSelected(messageList, position);
    }

    @Override
    public void onRefresh() {
        isRefreshing = true;
        messageListSwipeRefreshLayout.setRefreshing(true);
        AsyncTask<Void, Void, Boolean> refreshSermons = new UpdateMessageTask();
        refreshSermons.execute();
    }

    public void onRefreshComplete(){
        messageListSwipeRefreshLayout.setRefreshing(false);
        isRefreshing = false;
        mListAdapter = new MessageListAdapter(getContext(), this.messageList);
        messageListView.setAdapter(mListAdapter);
        messageListView.setOnItemClickListener(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment. If confused as to why, see the Android Training lesson
     * about communicating between fragments for more information.
     * http://developer.android.com/training/basics/fragments/communicating.html
     */
    public interface MessageListInteraction {
        void onMessageSelected(MessageList messageList, int sermonIndex);
    }

    public class UpdateMessageTask extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
            MessageLoader messageLoader = new MessageLoader(getContext());
            if ((messageList = messageLoader.getMessageList()) != null)
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
