package coop.nisc.intern2016.importer;

import android.support.annotation.NonNull;
import com.google.common.collect.ImmutableList;
import coop.nisc.intern2016.model.Album;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public final class Importer {

    private static final String RESULT_COUNT = "resultCount";
    private static final String RESULTS = "results";
    private static final String WRAPPER_TYPE = "wrapperType";
    private static final String COLLECTION = "collection";
    private static final String COLLECTION_TYPE = "collectionType";
    private static final String ALBUM = "Album";

    private static final String COLLECTION_NAME = "collectionName";
    private static final String ARTIST_NAME = "artistName";
    private static final String COLLECTION_ID = "collectionId";
    private static final String PRIMARY_GENRE_NAME = "primaryGenreName";
    private static final String RELEASE_DATE = "releaseDate";
    private static final String TRACK_COUNT = "trackCount";
    private static final String COLLECTION_PRICE = "collectionPrice";
    private static final String COUNTRY = "country";
    private static final String COLLECTION_EXPLICITNESS = "collectionExplicitness";
    private static final String EXPLICIT = "explicit";

    @NonNull
    public static String importAlbum(@NonNull InputStream inputStream) throws IOException, JSONException {

        String line;

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder stringBuilder = new StringBuilder();

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        bufferedReader.close();

        return stringBuilder.toString();
    }

    @NonNull
    public static ImmutableList<Album> getAlbums(@NonNull String Json) throws IOException, JSONException {

        JSONObject JsonObject = new JSONObject(Json);

        final int resultCount = JsonObject.getInt(RESULT_COUNT);
        JSONArray songInfo = JsonObject.getJSONArray(RESULTS);

        ArrayList<Album> albums = new ArrayList<>();

        for (int index = 0; index < resultCount; index++) {

            JSONObject song = songInfo.getJSONObject(index);
            if (song.getString(WRAPPER_TYPE).equals(COLLECTION)) {
                if (song.getString(COLLECTION_TYPE).equals(ALBUM)) {

                    albums.add(new Album(
                            song.getString(COLLECTION_NAME),
                            song.getString(ARTIST_NAME),
                            song.getString(COLLECTION_ID),
                            song.getString(PRIMARY_GENRE_NAME),
                            song.getString(RELEASE_DATE).substring(0, 4),
                            song.getString(COUNTRY),
                            song.getInt(TRACK_COUNT),
                            song.getDouble(COLLECTION_PRICE),
                            song.getString(COLLECTION_EXPLICITNESS).equals(EXPLICIT)));
                }
            }
        }
    return ImmutableList.copyOf(albums);
    }

}
