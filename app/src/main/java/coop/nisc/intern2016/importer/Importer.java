package coop.nisc.intern2016.importer;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.common.collect.ImmutableList;
import coop.nisc.intern2016.model.Album;
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

    private Importer() {
    }

    @NonNull
    public static ImmutableList<Album> importAlbums(@NonNull String Json) {
        final ImmutableList.Builder<Album> albums = ImmutableList.builder();
        try {
            final JSONArray songInfo = new JSONObject(Json).getJSONArray(RESULTS);
            final int resultCount = songInfo.length();
            for (int index = 0; index < resultCount; index++) {
                final JSONObject song = songInfo.getJSONObject(index);
                if (song.getString(WRAPPER_TYPE).equals(COLLECTION)) {
                    if (song.getString(COLLECTION_TYPE).equals(ALBUM)) {
                        albums.add(new Album(song.getString(COLLECTION_NAME),
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
        } catch (JSONException e) {
            Log.e("JSON_EXCEPTION", e.toString());
        }
        return albums.build();
    }

}
