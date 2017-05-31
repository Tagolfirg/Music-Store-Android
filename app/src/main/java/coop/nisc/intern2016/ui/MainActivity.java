package coop.nisc.intern2016.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import coop.nisc.intern2016.importer.AlbumImporter;
import coop.nisc.intern2016.R;
import org.json.JSONException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            List albumResults = AlbumImporter.getAlbums(AlbumImporter.importAlbum(getAssets().open("1.txt")));
            Log.d("ALBUM_INFO", albumResults.toString());
        } catch (IOException e) {
            Log.e("IO_EXCEPTION", e.toString());
        } catch (JSONException e) {
            Log.e("JSON_EXCEPTION", e.toString());
        }
    }
}