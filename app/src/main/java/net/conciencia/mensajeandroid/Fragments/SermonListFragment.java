package net.conciencia.mensajeandroid.Fragments;

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

import net.conciencia.mensajeandroid.Adapters.SermonListAdapter;
import net.conciencia.mensajeandroid.R;
import net.conciencia.mensajeandroid.ContentLoaders.RSSInterface;
import net.conciencia.mensajeandroid.Objects.Sermon;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link SermonListInteraction}
 * interface.
 */
public class SermonListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

    private SermonListInteraction mListener;
    boolean isRefreshing;

    SwipeRefreshLayout fragmentSwipeRefreshLayout;
    ListView mListView;
    ListAdapter mListAdapter;
    RSSInterface rssInterface;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SermonListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rssInterface = new RSSInterface();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sermon_list, container, false);

        mListView = (ListView)view.findViewById(R.id.lv_sermon_list);

        fragmentSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.messagelist_SwipeRefreshLayout);
        fragmentSwipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SermonListInteraction) {
            mListener = (SermonListInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SermonListInteraction");
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
        fragmentSwipeRefreshLayout.setRefreshing(isRefreshing);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Sermon chosenSermon = (Sermon)parent.getAdapter().getItem(position);
        mListener.onSermonSelected(rssInterface, /*chosenSermon*/ position);
    }

    @Override
    public void onRefresh() {
        isRefreshing = true;
        fragmentSwipeRefreshLayout.setRefreshing(true);
        AsyncTask<Void, Void, Boolean> refreshSermons = new UpdateSermonsTask();
        refreshSermons.execute();
    }

    public void onRefreshComplete(boolean status){
        fragmentSwipeRefreshLayout.setRefreshing(false);
        isRefreshing = false;
        mListAdapter = new SermonListAdapter(getContext(), rssInterface.getSermons());
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface SermonListInteraction {
        void onSermonSelected(RSSInterface rssInterface, int sermonIndex);
    }

    public class UpdateSermonsTask extends AsyncTask<Void, Void, Boolean>{
        @Override
        protected Boolean doInBackground(Void... params) {
            return rssInterface.loadSermonsFromWeb();
        }

        @Override
        protected void onPostExecute(Boolean successful) {
            super.onPostExecute(successful);
            onRefreshComplete(successful);
        }
    }
}