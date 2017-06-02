package coop.nisc.intern2016.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class Album {

    private final String albumName;
    private final String artistName;
    private final String genre;
    private final String albumId;
    private final String year;
    private final String country;
    private final int trackTotal;
    private final double price;
    private final boolean explicit;

    public Album(
            @NonNull String albumName,
            @NonNull String artistName,
            @NonNull String albumId,
            @Nullable String genre,
            @Nullable String year,
            @Nullable String country,
            int trackTotal,
            double price,
            boolean explicit) {

        this.albumName = albumName;
        this.artistName = artistName;
        this.genre = genre;
        this.albumId = albumId;
        this.year = year;
        this.country = country;
        this.trackTotal = trackTotal;
        this.price = price;
        this.explicit = explicit;
        }

    @NonNull
    @Override
    public String toString() {

        return "\nAlbum: " + albumName + "\t\tArtist: " + artistName +
                "\nAlbumID: " + albumId + "\t\tGenre: " + genre +
                "\nYear: " + year + "\t\tCountry: " + country +
                "\nTrack Total: " + trackTotal + "\t\tPrice: $" + String.format("%.2f", price) +
                "\nExplicit? " + explicit;
    }

}
