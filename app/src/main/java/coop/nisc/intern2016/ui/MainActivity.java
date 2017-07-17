package coop.nisc.intern2016.ui;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.importer.Importer;
import coop.nisc.intern2016.model.Album;
import coop.nisc.intern2016.util.ParseAsset;

import java.util.ArrayList;

public final class MainActivity extends AppCompatActivity implements ActionMode.Callback,
                                                                     AlbumListFragment.AlbumSelectedCallback {

    private static final String TAG = "MainActivity";
    private static final String STATE_SEARCHING = "searching";
    private static final String STATE_ACTION_MODE = "actionMode";
    private static final String ARGUMENT_QUERY = "query";

    private ActionMode actionMode;
    private AlbumListFragment albumListFragment;
    private SearchTask searchTask;
    private String query = "";
    private boolean inActionMode;
    private boolean searching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            searching = savedInstanceState.getBoolean(STATE_SEARCHING);
            inActionMode = savedInstanceState.getBoolean(STATE_ACTION_MODE);
            query = savedInstanceState.getString(ARGUMENT_QUERY, query);
        }
        findViewById(R.id.fragment_container).setOnClickListener(v -> exitActionMode());
    }

    @Override
    protected void onResume() {
        super.onResume();
        albumListFragment = (AlbumListFragment) getSupportFragmentManager().findFragmentByTag(AlbumListFragment.TAG);
        if (albumListFragment != null) {
            albumListFragment.setAlbumSelectedCallback(this);
            hideNoResultsView();
        }
        if (inActionMode) {
            actionMode = startSupportActionMode(this);
        }
        if (searching) {
            executeSearch();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (searching) {
            searchTask.cancel(false);
        }
        if (albumListFragment != null) {
            albumListFragment.setAlbumSelectedCallback(null);
        }
        if (inActionMode) {
            exitActionMode();
            inActionMode = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search:
                actionMode = startSupportActionMode(this);
                return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SEARCHING, searching);
        outState.putBoolean(STATE_ACTION_MODE, inActionMode);
        outState.putString(ARGUMENT_QUERY, query);
    }

    @Override
    public void onAlbumClicked() {
        exitActionMode();
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode,
                                      Menu menu) {
        mode.setCustomView(setupToolbarSearchText());
        getMenuInflater().inflate(R.menu.search, menu);
        inActionMode = true;
        return true;
    }


    @Override
    public boolean onPrepareActionMode(ActionMode mode,
                                       Menu menu) {
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode,
                                       MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search:
                performSearch();
                return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        query = ((EditText) mode.getCustomView()).getText().toString();
        inActionMode = false;
    }

    @SuppressLint("InflateParams")
    @NonNull
    private View setupToolbarSearchText() {
        EditText toolbarSearchText = (EditText) getLayoutInflater().inflate(R.layout.view_toolbar_search_text, null);
        toolbarSearchText.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    performSearch();
                    return true;
                }
            }
            return false;
        });
        toolbarSearchText.setText(query);
        return toolbarSearchText;
    }

    private void performSearch() {
        if (searching) {
            searchTask.cancel(true);
        }
        executeSearch();
        actionMode.finish();
        hideNoResultsView();
    }

    private void executeSearch() {
        searchTask = new SearchTask();
        searchTask.execute();
    }

    private void showAlbumListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        albumListFragment = (new AlbumListFragment());
        albumListFragment.setAlbumSelectedCallback(this);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, albumListFragment, AlbumListFragment.TAG)
                .commit();
    }

    private void hideNoResultsView() {
        (findViewById(R.id.no_results)).setVisibility(View.GONE);
    }

    private void exitActionMode() {
        if (actionMode != null) {
            actionMode.finish();
        }
    }

    private final class SearchTask extends AsyncTask<Void, Void, ArrayList<Album>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showAlbumListFragment();
            searching = true;
        }

        @Override
        protected ArrayList<Album> doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Background task was interrupted");
            }
            return Importer.importAlbums(ParseAsset.parse(getApplicationContext(), "album_search_query_result.txt"));
        }

        @Override
        protected void onPostExecute(ArrayList<Album> albums) {
            super.onPostExecute(albums);
            albumListFragment.setAlbums(albums);
            searching = false;
        }

    }

}