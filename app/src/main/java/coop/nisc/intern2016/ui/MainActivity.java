package coop.nisc.intern2016.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.importer.Importer;
import coop.nisc.intern2016.util.ParseAsset;

public final class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String ARGUMENT_TOOLBAR = "toolbar";
    private static final String ARGUMENT_SEARCHABLE = "searchable";

    private ViewSwitcher viewSwitcher;
    private Toolbar defaultToolbar;
    private Toolbar searchToolbar;
    private EditText searchText;
    private boolean searchable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int toolbarId = 0;
        if (savedInstanceState != null) {
            searchable = savedInstanceState.getBoolean(ARGUMENT_SEARCHABLE);
            toolbarId = savedInstanceState.getInt(ARGUMENT_TOOLBAR);
        }
        setupViews(searchable);
        configureToolbarState(toolbarId);
    }


    private void setupViews(boolean searchable) {
        viewSwitcher = (ViewSwitcher) findViewById(R.id.view_switcher);

        searchText = (EditText) findViewById(R.id.search_edit_text);

        defaultToolbar = (Toolbar) findViewById(R.id.default_toolbar);
        defaultToolbar.inflateMenu(R.menu.default_toolbar);
        if (!searchable) {
            disableSearch();
        } else {
            showDefaultFragment();
        }

        searchToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        searchToolbar.inflateMenu(R.menu.search_toolbar);

        setToolbarListeners();
    }

    private void disableSearch() {
        defaultToolbar.getMenu().findItem(R.id.search_button).setVisible(false);
    }


    private void setToolbarListeners() {
        defaultToolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);

        searchToolbar.setOnMenuItemClickListener(this::onOptionsItemSelected);
        searchToolbar.setNavigationOnClickListener(v -> onBackPressed());

        searchText.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    performSearch();
                    return true;
                }
            }
            return false;
        });
    }

    private void configureToolbarState(int toolbarId) {
        if (viewSwitcher.getNextView().getId() == toolbarId) {
            viewSwitcher.showNext();
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, viewSwitcher.getCurrentView().toString());
        if (viewSwitcher.getCurrentView().equals(defaultToolbar)) {
            super.onBackPressed();
        } else {
            switchToolbar();
        }
    }

    private void switchToolbar() {
        viewSwitcher.showNext();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.search_button:
                switchToolbar();
                searchText.setText("");
                return true;
            case R.id.perform_search_button:
                performSearch();
                return true;
        }
        return false;
    }

    private void performSearch() {
        hideSoftKeyboard();
        switchToolbar();
        disableSearch();
        showAlbumDetailsFragment(searchText.getText().toString());
        searchable = false;
    }

    private void hideSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(DefaultFragment.TAG);
        if (fragment == null) {
            fragment = DefaultFragment.create();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, DefaultFragment.TAG)
                    .commit();
        }
    }

    private void showAlbumDetailsFragment(@NonNull String query) {
        Log.d(TAG, query);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(AlbumListFragment.TAG);
        if (fragment == null) {
            fragment = AlbumListFragment.create(Importer.importAlbums(ParseAsset.parse(this,
                                                                                       "album_search_query_result.txt")));
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment, AlbumListFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        ((TextView) findViewById(R.id.activity_title)).setText(title);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(ARGUMENT_TOOLBAR, viewSwitcher.getCurrentView().getId());
        outState.putBoolean(ARGUMENT_SEARCHABLE, searchable);
        super.onSaveInstanceState(outState);
    }
}