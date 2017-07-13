package coop.nisc.intern2016.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.importer.Importer;
import coop.nisc.intern2016.model.Album;
import coop.nisc.intern2016.util.ParseAsset;

import java.util.ArrayList;

public final class MainActivity extends AppCompatActivity implements ActionMode.Callback,
                                                                     AlbumListFragment.OnClickListener {

    private static final String TAG = "MainActivity";
    private static final String STATE_SEARCHING = "searching";
    private static final String STATE_ACTION_MODE = "actionMode";

    private ActionMode actionMode;
    private AlbumListFragment albumListFragment;
    private ArrayList<Album> albums;
    private SearchTask searchTask;
    private boolean inActionMode;
    private boolean searching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            searching = savedInstanceState.getBoolean(STATE_SEARCHING);
            inActionMode = savedInstanceState.getBoolean(STATE_ACTION_MODE);
        }
        AlbumListFragment fragment = (AlbumListFragment) getSupportFragmentManager().findFragmentByTag(AlbumListFragment.TAG);
        if (fragment == null) {
            showAlbumListFragment(new ArrayList<>());
        } else {
            albumListFragment = fragment;
        }
        findViewById(R.id.fragment_container).setOnClickListener(v -> {
            if (actionMode != null) {
                actionMode.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (searching) {
            executeSearch();
        }
        if (inActionMode) {
            actionMode = startSupportActionMode(this);
        }
        if (albumListFragment != null) {
            albumListFragment.setOnClickListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (searching) {
            searchTask.cancel(false);
        }
        if (albumListFragment != null) {
            albumListFragment.setOnClickListener(null);
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
        outState.putBoolean(STATE_SEARCHING, searching);
        outState.putBoolean(STATE_ACTION_MODE, inActionMode);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick() {
        if (actionMode != null) {
            actionMode.finish();
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode,
                                      Menu menu) {
        mode.setCustomView(setupToolbarSearchText());
        getMenuInflater().inflate(R.menu.search, menu);
        setKeyboardVisible(true);
        inActionMode = true;
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode,
                                       Menu menu) {
        return false;
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
        setKeyboardVisible(false);
        inActionMode = false;
    }

    @SuppressLint("InflateParams")
    private View setupToolbarSearchText() {
        EditText toolbarSearchText = (EditText) getLayoutInflater().inflate(R.layout.toolbar_search_text, null);
        toolbarSearchText.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    performSearch();
                    return true;
                }
            }
            return false;
        });
        toolbarSearchText.requestFocus();
        return toolbarSearchText;
    }

    private void performSearch() {
        if (searching) {
            searchTask.cancel(true);
        }
        executeSearch();
        actionMode.finish();
    }

    private void executeSearch() {
        searchTask = new SearchTask();
        searchTask.execute();
    }

    public void showAlbumListFragment(@Nullable ArrayList<Album> albums) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        albumListFragment = (albums != null ? AlbumListFragment.create(albums) : new AlbumListFragment());
        albumListFragment.setOnClickListener(this);
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, albumListFragment, AlbumListFragment.TAG)
                .commit();
    }

    private void setKeyboardVisible(boolean visible) {
        View view = getCurrentFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null) {
            if (visible) {
                inputMethodManager.showSoftInput(view, 0);
            } else {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private final class SearchTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showAlbumListFragment(null);
            searching = true;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "Background task was interrupted");
            }
            albums = Importer.importAlbums(ParseAsset.parse(getApplicationContext(), "album_search_query_result.txt"));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            albumListFragment.setAlbums(albums);
            searching = false;
        }

    }

}