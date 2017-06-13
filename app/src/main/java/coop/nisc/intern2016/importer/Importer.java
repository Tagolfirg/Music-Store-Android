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
    private static final String WRAPPER_TYPE = "wrapperType";

    // Album keys
    private static final String ALBUM = "Album";
    private static final String COLLECTION = "collection";
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
    private static final String TRACK = "track";
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
            final JSONArray albumArray = new JSONObject(json).getJSONArray(RESULTS);
            final int resultCount = albumArray.length();
            for (int index = 0; index < resultCount; index++) {
                final JSONObject album = albumArray.getJSONObject(index);
                if (COLLECTION.equals(album.getString(WRAPPER_TYPE))) {
                    if (ALBUM.equals(album.getString(COLLECTION_TYPE))) {
                        albums.add(new Album(album.getString(ARTIST_NAME),
                                             album.getString(COLLECTION_EXPLICITNESS),
                                             album.getString(COLLECTION_ID),
                                             album.getString(COLLECTION_NAME),
                                             album.getDouble(COLLECTION_PRICE),
                                             album.getString(COUNTRY),
                                             album.getString(PRIMARY_GENRE_NAME),
                                             album.getString(RELEASE_DATE),
                                             album.getInt(TRACK_COUNT)));
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error importing albums", e);
        }
        return albums;
    }

    @NonNull
    public static ImmutableList<Track> importTracks(@NonNull String json) {
        ImmutableList.Builder<Track> tracks = ImmutableList.builder();
        try {
            final JSONArray songs = new JSONObject(json).getJSONArray(RESULTS);
            final int resultCount = songs.length();
            for (int index = 0; index < resultCount; index++) {
                final JSONObject song = songs.getJSONObject(index);
                if (TRACK.equals(song.getString(WRAPPER_TYPE))) {
                    if (SONG.equals(song.getString(KIND))) {
                        tracks.add(new Track(song.getString(ARTIST_NAME),
                                             song.getString(COLLECTION_NAME),
                                             song.getInt(DISC_COUNT),
                                             song.getInt(DISC_NUMBER),
                                             song.getString(PRIMARY_GENRE_NAME),
                                             song.getString(TRACK_EXPLICITNESS),
                                             song.getString(TRACK_NAME),
                                             song.getInt(TRACK_NUMBER),
                                             song.getDouble(TRACK_PRICE),
                                             song.getInt(TRACK_DURATION)));
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error importing tracks", e);
        }
        return tracks.build();
    }

}