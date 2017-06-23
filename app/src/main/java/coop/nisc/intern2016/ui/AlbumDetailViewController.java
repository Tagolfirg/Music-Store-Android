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
        trackCount.setText(String.valueOf(tracks.size() + " Tracks"));

        getTrackListViews((LinearLayout) view.findViewById(R.id.album_detail_track_list), tracks);
    }

    private void getTrackListViews(@NonNull LinearLayout parent,
                                   @NonNull ArrayList<Track> tracks) {
        ViewHolder viewHolder;
        for (Track track : tracks) {
            View trackView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_track, parent, false);
            viewHolder = new ViewHolder(trackView);
            viewHolder.trackName.setText(track.getTrackTitle());
            viewHolder.trackDuration.setText(track.getFormattedTrackDuration());
            viewHolder.trackNumber.setText(String.valueOf(track.getTrackNumber()));
            parent.addView(trackView);
        }
    }

    private final class ViewHolder {

        final TextView trackNumber;
        final TextView trackName;
        final TextView trackDuration;

        ViewHolder(@NonNull View view) {
            trackNumber = (TextView) view.findViewById(R.id.track_list_track_number);
            trackName = (TextView) view.findViewById(R.id.track_list_track_title);
            trackDuration = (TextView) view.findViewById(R.id.track_list_track_duration);
        }

    }

}