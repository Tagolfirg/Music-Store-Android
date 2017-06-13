package coop.nisc.intern2016.importer;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.common.collect.ImmutableList;
import coop.nisc.intern2016.model.Album;
import coop.nisc.intern2016.model.Track;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class Importer {

    private static final String TAG = "Importer";

    // Shared keys
    private static final String ARTIST_NAME = "artistName";
    private static final String COLLECTION_NAME = "collectionName";
    private static final String PRIMARY_GENRE_NAME = "primaryGenreName";
    private static final String RESULTS = "results";

    // Album keys
    private static final String ALBUM = "Album";
    private static final String COLLECTION_EXPLICITNESS = "collectionExplicitness";
    private static final String COLLECTION_ID = "collectionId";
    private static final String COLLECTION_PRICE = "collectionPrice";
    private static final String COLLECTION_TYPE = "collectionType";
    private static final String COUNTRY = "country";
    private static final String RELEASE_DATE = "releaseDate";
    private static final String TRACK_COUNT = "trackCount";

    // Track keys
    private static final String DISC_COUNT = "discCount";
    private static final String DISC_NUMBER = "discNumber";
    private static final String KIND = "kind";
    private static final String SONG = "song";
    private static final String TRACK_DURATION = "trackTimeMillis";
    private static final String TRACK_EXPLICITNESS = "trackExplicitness";
    private static final String TRACK_NAME = "trackName";
    private static final String TRACK_NUMBER = "trackNumber";
    private static final String TRACK_PRICE = "trackPrice";

    private Importer() {
    }

    @NonNull
    public static ArrayList<Album> importAlbums(@NonNull String json) {
        final ArrayList<Album> albums = new ArrayList<>();
        try {
            final JSONArray jsonArray = new JSONObject(json).getJSONArray(RESULTS);
            final int resultCount = jsonArray.length();
            for (int index = 0; index < resultCount; index++) {
                final JSONObject album = jsonArray.getJSONObject(index);
                if (ALBUM.equals(album.optString(COLLECTION_TYPE))) {
                    albums.add(new Album(album.optString(ARTIST_NAME),
                                         album.getString(COLLECTION_EXPLICITNESS),
                                         album.optString(COLLECTION_ID),
                                         album.optString(COLLECTION_NAME),
                                         album.optInt(COLLECTION_PRICE),
                                         album.optString(COUNTRY),
                                         album.optString(PRIMARY_GENRE_NAME),
                                         album.optString(RELEASE_DATE),
                                         album.optInt(TRACK_COUNT)));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error importing albums", e);
        }
        Log.d(TAG, String.valueOf(albums.size()));
        return albums;
    }

    @NonNull
    public static ImmutableList<Track> importTracks(@NonNull String json) {
        ImmutableList.Builder<Track> tracks = ImmutableList.builder();
        try {
            final JSONArray jsonArray = new JSONObject(json).getJSONArray(RESULTS);
            final int resultCount = jsonArray.length();
            for (int index = 0; index < resultCount; index++) {
                final JSONObject song = jsonArray.getJSONObject(index);
                if (SONG.equals(song.optString(KIND))) {
                    tracks.add(new Track(song.optString(ARTIST_NAME),
                                         song.optString(COLLECTION_NAME),
                                         song.optInt(DISC_COUNT),
                                         song.optInt(DISC_NUMBER),
                                         song.optString(PRIMARY_GENRE_NAME),
                                         song.getString(TRACK_EXPLICITNESS),
                                         song.optString(TRACK_NAME),
                                         song.optInt(TRACK_NUMBER),
                                         song.optDouble(TRACK_PRICE),
                                         song.getInt(TRACK_DURATION)));
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error importing tracks", e);
        }
        return tracks.build();
    }

}