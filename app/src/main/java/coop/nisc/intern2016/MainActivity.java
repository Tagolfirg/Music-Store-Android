package coop.nisc.intern2016;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Music Store App");

        try {
            AlbumImporter albumImporter = new AlbumImporter(getAssets().open("1.txt"));
            Log.d("ALBUM_INFO", Arrays.toString(albumImporter.getAlbums(5)));
        } catch (IOException e) {
            Log.e("IO_EXCEPTION", e.toString());
        } catch (JSONException e) {
            Log.e("JSON_EXCEPTION", e.toString());
        }
    }
}