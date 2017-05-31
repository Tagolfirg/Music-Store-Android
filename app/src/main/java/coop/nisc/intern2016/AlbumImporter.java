package coop.nisc.intern2016;

import android.support.annotation.NonNull;
import coop.nisc.intern2016.model.Album;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

final class AlbumImporter {

    private String fileContents;

    AlbumImporter(@NonNull InputStream inputStream) throws IOException, JSONException {

        String line;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        this.fileContents = stringBuilder.toString();

        bufferedReader.close();
    }

    @NonNull
    Album[] getAlbums(int numberOfResults) throws IOException, JSONException {


        JSONObject Json = new JSONObject(this.fileContents);

        final int resultCount = Json.getInt("resultCount");
        JSONArray songInfo = Json.getJSONArray("results");

        final int INDEX_LIMIT = (numberOfResults < resultCount) ? numberOfResults : resultCount;
        Album[] albums = new Album[INDEX_LIMIT];

        for (int index = 0; index < INDEX_LIMIT; index++) {

            JSONObject song = songInfo.getJSONObject(index);
            if (song.getString("collectionType").equals("Album")) {

                final int albumID = song.getInt("collectionId");
                final String albumName = song.getString("collectionName");
                final String artistName = song.getString("artistName");
                final int releaseDate =  Integer.valueOf(song.getString("releaseDate").substring(0, 4));
                final int trackTotal = song.getInt("trackCount");
                final String genre = song.getString("primaryGenreName");
                final float price = song.getInt("collectionPrice");
                final String country = song.getString("country");
                final boolean isExplicit = (!song.getString("collectionExplicitness").equals("notExplicit"));

                albums[index] = new Album(albumID, albumName, artistName, releaseDate, trackTotal, genre, price, country, isExplicit);
            }
        }
    return albums;
    }
}