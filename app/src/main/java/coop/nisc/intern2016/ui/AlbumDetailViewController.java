package coop.nisc.intern2016.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.model.Album;
import coop.nisc.intern2016.model.Explicitness;
import coop.nisc.intern2016.model.Track;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

final class AlbumDetailViewController {

    private final Context context;

    private OnClickListener listener;

    AlbumDetailViewController(@NonNull View view,
                              @NonNull Album album) {
        context = view.getContext();

        ((TextView) view.findViewById(R.id.album_detail_collection_name))
                .setText(album.collectionName);

        ((TextView) view.findViewById(R.id.album_detail_artist))
                .setText(context.getString(R.string.by, album.artistName));

        ((TextView) view.findViewById(R.id.album_detail_explicitness))
                .setText(Explicitness.getType(album.collectionExplicitness).getDisplayString(context));

        ((TextView) view.findViewById(R.id.album_detail_year_and_tracks))
                .setText(context.getResources().getQuantityString(
                        R.plurals.year_and_track_count,
                        album.trackCount,
                        getYear(album.releaseDate),
                        album.trackCount));

        View loadingIndicator = view.findViewById(R.id.loading_indicator);
        View noResults = view.findViewById(R.id.no_track_results);
        ViewGroup parent = (ViewGroup) view.findViewById(R.id.album_detail_track_list);

        if (album.tracks == null) {
            loadingIndicator.setVisibility(View.VISIBLE);
            noResults.setVisibility(View.GONE);
        } else if (album.tracks.size() == 0) {
            loadingIndicator.setVisibility(View.GONE);
            noResults.setVisibility(View.VISIBLE);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            noResults.setVisibility(View.GONE);
            createTrackListViews(parent, album.tracks).forEach(parent::addView);
        }
    }

    @NonNull
    private String getYear(@NonNull String releaseDate) {
        if (releaseDate.length() > 3) {
            return releaseDate.substring(0, 4);
        }
        return "";
    }

    @NonNull
    private ArrayList<View> createTrackListViews(@NonNull ViewGroup parent,
                                                 @NonNull ArrayList<Track> tracks) {
        ArrayList<View> views = new ArrayList<>();
        for (Track track : tracks) {
            final View trackView = LayoutInflater.from(context).inflate(R.layout.list_item_track, parent, false);
            trackView.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onClick(track);
                }
            });
            new TrackViewController(trackView, track);
            views.add(trackView);
        }
        return views;
    }

    void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    private final class TrackViewController {

        TrackViewController(@NonNull View view,
                            @NonNull Track track) {
            ((TextView) view.findViewById(R.id.track_list_track_title))
                    .setText(track.trackName);

            ((TextView) view.findViewById(R.id.track_list_track_number))
                    .setText(String.valueOf(track.trackNumber));

            ((TextView) view.findViewById(R.id.track_list_track_duration))
                    .setText(getFormattedTrackDuration(track.trackTimeMillis));
        }

    }

    @NonNull
    private String getFormattedTrackDuration(int trackTimeMillis) {
        return (trackTimeMillis < TimeUnit.HOURS.toMillis(1) ?
                new SimpleDateFormat("m:ss", Locale.getDefault()).format(new Date(trackTimeMillis)) :
                new SimpleDateFormat("H:mm:ss", Locale.getDefault()).format(new Date(trackTimeMillis)));
    }

    interface OnClickListener {

        void onClick(@NonNull Track track);

    }

}