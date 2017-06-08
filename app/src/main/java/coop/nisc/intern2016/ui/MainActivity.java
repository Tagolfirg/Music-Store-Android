package coop.nisc.intern2016.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.common.collect.ImmutableList;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.importer.Importer;
import coop.nisc.intern2016.model.Album;
import coop.nisc.intern2016.model.Track;
import coop.nisc.intern2016.util.ParseAsset;

public final class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImmutableList<Album> albumResults = Importer.importAlbums(ParseAsset.parse(this,
                                                                                   "album_search_query_result.txt"));
        Log.d("ALBUM_INFO", albumResults.toString());
        ImmutableList<Track> trackResults = Importer.importTracks(ParseAsset.parse(this,
                                                                                   "track_search_query_result.txt"));
        Log.d("TRACK_INFO", trackResults.toString());
    }

}
