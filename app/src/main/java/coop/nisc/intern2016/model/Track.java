package coop.nisc.intern2016.model;

import android.support.annotation.NonNull;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Track {

    private final String artistName;
    private final String collectionName;
    private final int discCount;
    private final int discNumber;
    private final String primaryGenreName;
    private final String trackExplicitness;
    private final String trackName;
    private final int trackNumber;
    private final BigDecimal trackPrice;
    private final int trackTimeMillis;

    public Track(@NonNull String artistName,
                 @NonNull String collectionName,
                 int discCount,
                 int discNumber,
                 @NonNull String primaryGenreName,
                 @NonNull String trackExplicitness,
                 @NonNull String trackName,
                 int trackNumber,
                 double trackPrice,
                 int trackTimeMillis) {
        this.artistName = artistName;
        this.collectionName = collectionName;
        this.discCount = discCount;
        this.discNumber = discNumber;
        this.primaryGenreName = primaryGenreName;
        this.trackExplicitness = trackExplicitness;
        this.trackName = trackName;
        this.trackNumber = trackNumber;
        this.trackPrice = BigDecimal.valueOf(trackPrice);
        this.trackTimeMillis = trackTimeMillis;
    }

    @NonNull
    @Override
    public String toString() {
        return "\nTrack Title: " + trackName + "\t\tArtist: " + artistName +
                "\nAlbum Title: " + collectionName + "\t\tGenre: " + primaryGenreName +
                "\nTrack Number: " + trackNumber + "\t\tDisc Number: " + discNumber + "/" + discCount +
                "\nTime: " + getFormattedTrackDuration() + "\t\tPrice: $" + trackPrice.toString() +
                "\n" + getFormattedExplicitness();
    }

    @NonNull
    private String getFormattedTrackDuration() {
        return (trackTimeMillis < TimeUnit.HOURS.toMillis(1) ?
                new SimpleDateFormat("m:ss", Locale.getDefault()).format(new Date(trackTimeMillis)) :
                new SimpleDateFormat("H:mm:ss", Locale.getDefault()).format(new Date(trackTimeMillis)));
    }

    @NonNull
    private String getFormattedExplicitness() {
        return ("Explicit".equals(trackExplicitness) ? "Explicit" : "Not Explicit");
    }

}
