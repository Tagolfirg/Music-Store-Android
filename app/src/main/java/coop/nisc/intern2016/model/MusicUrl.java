package coop.nisc.intern2016.model;

import android.net.Uri;

public final class MusicUrl {

    private static final String SCHEME = "https";
    private static final String AUTHORITY = "itunes.apple.com";
    private static final String PATH_SEARCH = "search";
    private static final String PATH_LOOKUP = "lookup";
    private static final String PARAMETER_TERM = "term";
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_ENTITY = "entity";
    private static final String PARAMETER_LIMIT = "limit";
    private static final String LOOKUP_LIMIT = "200";
    private static final String SEARCH_LIMIT = "50";

    public static final String SONG = "song";
    public static final String ALBUM = "album";

    private final String address;

    public MusicUrl(String term,
                    String entityType) {
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY);
        if (SONG.equals(entityType)) {
            address = uriBuilder.path(PATH_LOOKUP)
                    .appendQueryParameter(PARAMETER_ID, term)
                    .appendQueryParameter(PARAMETER_ENTITY, entityType)
                    .appendQueryParameter(PARAMETER_LIMIT, LOOKUP_LIMIT)
                    .build().toString();
        } else if (ALBUM.equals(entityType)) {
            address = uriBuilder.path(PATH_SEARCH)
                    .appendQueryParameter(PARAMETER_TERM, term)
                    .appendQueryParameter(PARAMETER_ENTITY, entityType)
                    .appendQueryParameter(PARAMETER_LIMIT, SEARCH_LIMIT)
                    .build().toString();
        } else {
            address = new Uri.Builder().build().toString();
        }
    }

    @Override
    public String toString() {
        return address;
    }

}
