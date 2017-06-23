package coop.nisc.intern2016.ui;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.importer.Importer;
import coop.nisc.intern2016.model.Album;
import coop.nisc.intern2016.model.Track;
import coop.nisc.intern2016.util.ParseAsset;

import java.util.ArrayList;

final class AlbumDetailViewController {

    private static final String TRACKS = " Tracks";

    AlbumDetailViewController(@NonNull View view,
                              @NonNull Album album) {
        TextView collectionName = (TextView) view.findViewById(R.id.album_detail_collection_name);
        collectionName.setText(album.getCollectionName());

        TextView artistName = (TextView) view.findViewById(R.id.album_detail_artist);
        artistName.setText(album.getArtistName());

        TextView releaseYear = (TextView) view.findViewById(R.id.album_detail_release_date);
        releaseYear.setText(album.getReleaseYear());

        TextView explicitness = (TextView) view.findViewById(R.id.album_detail_explicitness);
        explicitness.setText(album.getExplicitness());

        ArrayList<Track> tracks = Importer.importTracks(ParseAsset.parse(view.getContext(),
                                                                         "track_search_query_result.txt"));

        TextView trackCount = (TextView) view.findViewById(R.id.album_detail_track_count);
        trackCount.setText(String.valueOf(tracks.size() + TRACKS));

        createTrackListViews((LinearLayout) view.findViewById(R.id.album_detail_track_list), tracks);
    }

    private void createTrackListViews(@NonNull LinearLayout parent,
                                      @NonNull ArrayList<Track> tracks) {
        for (Track track : tracks) {
            View trackView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_track, parent, false);
            new TrackViewController(trackView, track);
            parent.addView(trackView);
        }
    }

    private final class TrackViewController {

        final TextView trackNumber;
        final TextView trackName;
        final TextView trackDuration;

        TrackViewController(@NonNull View view,
                            @NonNull Track track) {
            trackNumber = (TextView) view.findViewById(R.id.track_list_track_number);
            trackName = (TextView) view.findViewById(R.id.track_list_track_title);
            trackDuration = (TextView) view.findViewById(R.id.track_list_track_duration);
            setTextFields(track);
        }

        private void setTextFields(@NonNull Track track) {
            trackName.setText(track.getTrackName());
            trackDuration.setText(track.getFormattedTrackDuration());
            trackNumber.setText(String.valueOf(track.getTrackNumber()));
        }

    }

}