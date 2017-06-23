package coop.nisc.intern2016.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.importer.Importer;
import coop.nisc.intern2016.util.ParseAsset;

public final class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(AlbumListFragment.TAG);
        if (fragment == null) {
            fragment = AlbumListFragment.create(Importer.importAlbums(ParseAsset.parse(this,
                                                                                       "album_search_query_result.txt")));
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, AlbumListFragment.TAG)
                    .commit();
        }
    }

}
