package coop.nisc.intern2016.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Track {

    private final String trackName;
    private final String artistName;
    private final String albumName;
    private final String genre;
    private final String discNumber;
    private final int trackNumber;
    private final int trackDuration;
    private final double price;
    private final boolean explicit;

    public Track(@NonNull String trackName,
                 @NonNull String artistName,
                 @NonNull String albumName,
                 @Nullable String genre,
                 @Nullable String discNumber,
                 int trackNumber,
                 int trackDuration,
                 double price,
                 boolean explicit) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.albumName = albumName;
        this.genre = genre;
        this.discNumber = discNumber;
        this.trackNumber = trackNumber;
        this.trackDuration = trackDuration;
        this.price = price;
        this.explicit = explicit;
    }

    @NonNull
    @Override
    public String toString() {
        return "\nTrack Title: " + trackName + "\t\tArtist: " + artistName +
                "\nAlbum: " + albumName + "\t\tGenre: " + genre +
                "\nTrack Number: " + trackNumber + "\t\tDisc Number: " + discNumber +
                "\nTime: " + getFormattedTrackDuration() + "\t\tPrice: $" + String.format("%.2f", price) +
                "\nExplicit? " + explicit;
    }

    @NonNull
    private String getFormattedTrackDuration() {
        String printableTime;
        if (trackDuration < TimeUnit.HOURS.toMillis(1)) {
            printableTime = new SimpleDateFormat("m:ss", Locale.getDefault()).format(new Date(trackDuration));
        } else {
            printableTime = new SimpleDateFormat("H:mm:ss", Locale.getDefault()).format(new Date(trackDuration));
        }
        return printableTime;
    }

}
