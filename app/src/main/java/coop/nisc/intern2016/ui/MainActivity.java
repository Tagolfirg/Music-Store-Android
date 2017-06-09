package coop.nisc.intern2016.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.importer.Importer;
import coop.nisc.intern2016.util.ParseAsset;

public final class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Log.d(TAG, Importer.importTracks(ParseAsset.parse(this, "track_search_query_result.txt")).toString());
        AlbumFragment fragment = AlbumFragment.create(Importer.importAlbums(ParseAsset.parse(this,
                                                                                             "album_search_query_result.txt")));
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
