package coop.nisc.intern2016.importer;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.common.collect.ImmutableList;
import coop.nisc.intern2016.model.Album;
import coop.nisc.intern2016.model.Track;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class Importer {

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
    private static final String TRACK = "track";
    private static final String TRACK_NAME = "trackName";
    private static final String SONG = "song";
    private static final String KIND = "kind";
    private static final String TRACK_PRICE = "trackPrice";
    private static final String TRACK_NUMBER = "trackNumber";
    private static final String DISC_NUMBER = "discNumber";
    private static final String DISC_COUNT = "discCount";
    private static final String TRACK_DURATION = "trackTimeMillis";

    private Importer() {
    }

    @NonNull
    public static ImmutableList<Album> importAlbums(@NonNull String json) {
        final ImmutableList.Builder<Album> albums = ImmutableList.builder();
        try {
            final JSONArray albumArray = new JSONObject(json).getJSONArray(RESULTS);
            final int resultCount = albumArray.length();
            for (int index = 0; index < resultCount; index++) {
                final JSONObject album = albumArray.getJSONObject(index);
                if (album.getString(WRAPPER_TYPE).equals(COLLECTION)) {
                    if (album.getString(COLLECTION_TYPE).equals(ALBUM)) {
                        albums.add(new Album(album.getString(COLLECTION_NAME),
                                             album.getString(ARTIST_NAME),
                                             album.getString(COLLECTION_ID),
                                             album.getString(PRIMARY_GENRE_NAME),
                                             album.getString(RELEASE_DATE).substring(0, 4),
                                             album.getString(COUNTRY),
                                             album.getInt(TRACK_COUNT),
                                             album.getDouble(COLLECTION_PRICE),
                                             album.getString(COLLECTION_EXPLICITNESS).equals(EXPLICIT)));
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("JSON_EXCEPTION", "Error importing albums", e);
        }
        return albums.build();
    }

    @NonNull
    public static ImmutableList<Track> importTracks(@NonNull String json) {
        final ImmutableList.Builder<Track> tracks = ImmutableList.builder();
        try {
            final JSONArray songs = new JSONObject(json).getJSONArray(RESULTS);
            final int resultCount = songs.length();
            for (int index = 0; index < resultCount; index++) {
                final JSONObject song = songs.getJSONObject(index);
                if (song.getString(WRAPPER_TYPE).equals(TRACK)) {
                    if (song.getString(KIND).equals(SONG)) {
                        tracks.add(new Track(song.getString(TRACK_NAME),
                                             song.getString(ARTIST_NAME),
                                             song.getString(COLLECTION_NAME),
                                             song.getString(PRIMARY_GENRE_NAME),
                                             song.getString(DISC_NUMBER) + "/" + song.getString(DISC_COUNT),
                                             song.getInt(TRACK_NUMBER),
                                             song.getInt(TRACK_DURATION),
                                             song.getDouble(TRACK_PRICE),
                                             song.getString(COLLECTION_EXPLICITNESS).equals(EXPLICIT)));
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("JSON_EXCEPTION", "Error importing tracks", e);
        }
        return tracks.build();
    }

}
