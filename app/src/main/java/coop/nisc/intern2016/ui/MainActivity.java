package coop.nisc.intern2016.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.importer.Importer;
import coop.nisc.intern2016.model.Album;
import coop.nisc.intern2016.model.MusicUrl;
import coop.nisc.intern2016.model.Track;
import coop.nisc.intern2016.networking.MusicNetworkCall;

import java.util.ArrayList;

public final class MainActivity extends AppCompatActivity implements ActionMode.Callback,
                                                                     AlbumListFragment.AlbumSelectedCallback {

    private static final String STATE_SEARCHING = "searching";
    private static final String STATE_LOOKING = "looking";
    private static final String STATE_ACTION_MODE = "actionMode";
    private static final String STATE_QUERY = "query";
    private static final String STATE_SEARCH_CURSOR_START = "searchCursorStart";
    private static final String STATE_SEARCH_CURSOR_END = "searchCursorEnd";

    private ActionMode actionMode;
    private AlbumListFragment albumListFragment;
    private AlbumDetailsFragment albumDetailsFragment;
    private SearchTask searchTask;
    private LookupTask lookupTask;
    private String query = "";
    private int searchCursorStart;
    private int searchCursorEnd;
    private boolean inActionMode;
    private boolean searching;
    private boolean looking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            searching = savedInstanceState.getBoolean(STATE_SEARCHING);
            looking = savedInstanceState.getBoolean(STATE_LOOKING);
            inActionMode = savedInstanceState.getBoolean(STATE_ACTION_MODE);
            query = savedInstanceState.getString(STATE_QUERY, query);
            searchCursorStart = savedInstanceState.getInt(STATE_SEARCH_CURSOR_START);
            searchCursorEnd = savedInstanceState.getInt(STATE_SEARCH_CURSOR_END);
        }
        findViewById(R.id.fragment_container).setOnClickListener(v -> exitActionMode());
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerFragments();
        if (searching) {
            executeSearch();
        } else if (albumDetailsFragment != null) {
            Album album = albumDetailsFragment.album;
            if (looking) {
                executeLookup(album);
            }
            getAlbumArtwork(album);
        }
        if (albumListFragment != null) {
            albumListFragment.setAlbumSelectedCallback(this);
            hideNoResultsView();
        }
        if (inActionMode) {
            actionMode = startSupportActionMode(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (searching) {
            searchTask.cancel(true);
        }
        if (looking) {
            lookupTask.cancel(true);
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
        outState.putBoolean(STATE_LOOKING, looking);
        outState.putBoolean(STATE_ACTION_MODE, inActionMode);
        outState.putString(STATE_QUERY, query);
        outState.putInt(STATE_SEARCH_CURSOR_START, query.length());
        outState.putInt(STATE_SEARCH_CURSOR_END, query.length());
    }

    @Override
    public void onAlbumClicked(Album album) {
        exitActionMode();
        performLookup(album);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode,
                                      Menu menu) {
        mode.setCustomView(setupToolbarSearchText());
        getMenuInflater().inflate(R.menu.search, menu);
        setSoftKeyboardShown(this, true);
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
        setSoftKeyboardShown(this, false);
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
        toolbarSearchText.requestFocus();
        toolbarSearchText.setSelection(searchCursorStart, searchCursorEnd);

        setSoftKeyboardShown(this, true);
        return toolbarSearchText;
    }

    private void getAlbumArtwork(Album album) {
        if (album.artwork == null) {
            new GetAlbumArtTask().execute(album);
        }
    }

    private void registerFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        albumListFragment = (AlbumListFragment) fragmentManager.findFragmentByTag(AlbumListFragment.TAG);
        albumDetailsFragment = (AlbumDetailsFragment) fragmentManager.findFragmentByTag(AlbumDetailsFragment.TAG);
    }

    private void setSoftKeyboardShown(Activity activity,
                                      boolean shown) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        if (shown) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } else {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void performSearch() {
        if (searching) {
            searchTask.cancel(true);
        }
        executeSearch();
        actionMode.finish();
        hideNoResultsView();
        query = "";
    }

    private void executeSearch() {
        searchTask = new SearchTask();
        searchTask.execute();
    }

    private void performLookup(@NonNull Album album) {
        if (looking && lookupTask != null) {
            lookupTask.cancel(true);
        }
        albumListFragment.showAlbumDetailFragment(album);
        if (!(album.tracks != null && album.tracks.size() > 0)) {
            executeLookup(album);
        }
        getAlbumArtwork(album);
    }

    private void executeLookup(@NonNull Album album) {
        lookupTask = new LookupTask();
        lookupTask.execute(album);
    }

    private void showAlbumListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        albumListFragment = new AlbumListFragment();
        albumListFragment.setAlbumSelectedCallback(this);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, albumListFragment, AlbumListFragment.TAG)
                .commit();
    }

    private void setEmptyText(@NonNull String message) {
        if (albumListFragment == null) {
            ((TextView) findViewById(R.id.no_results)).setText(message);
        } else {
            albumListFragment.setEmptyText(message);
        }
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

        private static final String TAG = "SearchTask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showAlbumListFragment();
            searching = true;
        }

        @Override
        protected ArrayList<Album> doInBackground(Void... params) {
            try {
                return Importer.importAlbums(MusicNetworkCall.downloadUrl(new MusicUrl(query,
                                                                                       MusicUrl.ALBUM).toString()));
            } catch (MusicNetworkCall.unsuccessfulResponseException exception) {
                Log.e(TAG, "doInBackground: Error while getting albums " + exception.getLocalizedMessage());
                setEmptyText(exception.getLocalizedMessage());
                return new ArrayList<>();
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Album> albums) {
            super.onPostExecute(albums);
            albumListFragment.setAlbums(albums);
            searching = false;
        }

    }

    private final class GetAlbumArtTask extends AsyncTask<Album, Void, Bitmap> {

        private static final String TAG = "GetAlbumArtTask";

        @Override
        protected Bitmap doInBackground(Album... params) {
            Album album = params[0];
            try {
                return MusicNetworkCall.downloadAlbumArt(album.artworkUrl60);
            } catch (MusicNetworkCall.unsuccessfulResponseException e) {
                Log.e(TAG, "Error while getting albums " + e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            registerFragments();
            albumDetailsFragment.setArtwork(bitmap);
        }
    }

    private final class LookupTask extends AsyncTask<Album, Void, ArrayList<Track>> {

        private static final String TAG = "LookupTask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            looking = true;
        }

        @Override
        protected ArrayList<Track> doInBackground(Album... params) {
            Album album = params[0];
            if (hasData(album.tracks)) {
                return album.tracks;
            }
            ArrayList<Track> tracks = new ArrayList<>();
            try {
                tracks = Importer.importTracks(MusicNetworkCall.downloadUrl(
                        new MusicUrl(String.valueOf(album.collectionId), MusicUrl.SONG).toString()));
                album.tracks = tracks;
            } catch (MusicNetworkCall.unsuccessfulResponseException exception) {
                Log.e(TAG, "doInBackground: Error while getting tracks " + exception.getLocalizedMessage());
            }
            return tracks;
        }

        @Override
        protected void onPostExecute(ArrayList<Track> tracks) {
            super.onPostExecute(tracks);
            registerFragments();
            if (albumDetailsFragment != null) {
                albumDetailsFragment.setTracks(null, tracks);
            }
            looking = false;
        }

        private boolean hasData(@Nullable ArrayList<Track> tracks) {
            return (tracks != null && tracks.size() > 0);
        }

    }

}