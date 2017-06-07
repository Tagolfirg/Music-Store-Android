package coop.nisc.intern2016.model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.math.BigDecimal;

public final class Album {

    private static final String TAG = "Album";

    private final String artistName;
    private final String collectionExplicitness;
    private final String collectionId;
    private final String collectionName;
    private final BigDecimal collectionPrice;
    private final String country;
    private final String primaryGenreName;
    private final String releaseDate;
    private final int trackCount;

    public Album(@NonNull String artistName,
                 @NonNull String collectionExplicitness,
                 @NonNull String collectionId,
                 @NonNull String collectionName,
                 double collectionPrice,
                 @NonNull String country,
                 @NonNull String primaryGenreName,
                 @NonNull String releaseDate,
                 int trackCount) {
        this.artistName = artistName;
        this.collectionExplicitness = collectionExplicitness;
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.collectionPrice = BigDecimal.valueOf(collectionPrice);
        this.country = country;
        this.primaryGenreName = primaryGenreName;
        this.releaseDate = releaseDate;
        this.trackCount = trackCount;
        }

    @NonNull
    @Override
    public String toString() {
        return "\nAlbum: " + collectionName + "\t\tArtist: " + artistName +
                "\nAlbumID: " + collectionId + "\t\tGenre: " + primaryGenreName +
                "\nYear: " + formatDateToYear() + "\t\tCountry: " + country +
                "\nTrack Total: " + trackCount + "\t\tPrice: $" + collectionPrice.toString() +
                "\n" + getFormattedExplicitness();
    }

    private String formatDateToYear() {
        try {
            return releaseDate.substring(0, 4);
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, "Date is not formatted correctly", e);
            return "";
        }
    }

    @NonNull
    private String getFormattedExplicitness() {
        return ("Explicit".equals(collectionExplicitness) ? "Explicit" : "Not Explicit");
    }

}
