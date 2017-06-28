package coop.nisc.intern2016.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.model.Explicitness;
import coop.nisc.intern2016.model.Track;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

final class TrackDetailViewController {

    private final Context context;

    TrackDetailViewController(@NonNull View view,
                              @NonNull Track track) {
        context = view.getContext();

        ((TextView) view.findViewById(R.id.track_details_track_name))
                .setText(track.trackName);

        ((TextView) view.findViewById(R.id.track_details_album_title))
                .setText(track.collectionName);

        ((TextView) view.findViewById(R.id.track_details_artist_name))
                .setText(context.getString(R.string.by, track.artistName));

        ((TextView) view.findViewById(R.id.track_details_track_number))
                .setText(context.getString(R.string.track_number, track.trackNumber));

        ((TextView) view.findViewById(R.id.track_details_disc_x_of_y))
                .setText(context.getString(R.string.disc_x_of_y, track.discNumber, track.discCount));

        ((TextView) view.findViewById(R.id.track_details_track_duration))
                .setText(getFormattedTrackDuration(track.trackTimeMillis));

        ((TextView) view.findViewById(R.id.track_details_track_price))
                .setText(context.getString(R.string.price, track.trackPrice));

        ((TextView) view.findViewById(R.id.track_details_primary_genre))
                .setText(track.primaryGenreName);

        ((TextView) view.findViewById(R.id.track_details_track_explicitness))
                .setText(Explicitness.getType(track.trackExplicitness).getDisplayString(context));
    }

    @NonNull
    private String getFormattedTrackDuration(int trackTimeMillis) {
        String implicitFormat = (trackTimeMillis < TimeUnit.HOURS.toMillis(1) ?
                                 new SimpleDateFormat("m:ss", Locale.getDefault()).format(new Date(trackTimeMillis)) :
                                 new SimpleDateFormat("H:mm:ss",
                                                      Locale.getDefault()).format(new Date(trackTimeMillis)));
        String[] splitTime = implicitFormat.split(":");
        if (splitTime.length > 2) {
            return context.getString(R.string.hours_and_minutes, splitTime[0], splitTime[1]);
        } else if (splitTime.length > 1) {
            return context.getString(R.string.minutes_and_seconds, splitTime[0], splitTime[1]);
        } else {
            return "";
        }
    }

}