package coop.nisc.intern2016.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.google.common.collect.ImmutableList;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.importer.Importer;
import coop.nisc.intern2016.model.Album;
import org.json.JSONException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ImmutableList<Album> albumResults = Importer.getAlbums(Importer.importAlbum(getAssets().open(
                    "album_search_query_result.txt")));
            Log.d("ALBUM_INFO", albumResults.toString());
        } catch (IOException e) {
            Log.e("IO_EXCEPTION", e.toString());
        } catch (JSONException e) {
            Log.e("JSON_EXCEPTION", e.toString());
        }
    }

}
