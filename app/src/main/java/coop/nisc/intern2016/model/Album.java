package coop.nisc.intern2016.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class Album {
    private final int albumID;
    private final int year;
    private final int trackTotal;
    private final String albumName;
    private final String artist;
    private final String genre;
    private final String country;
    private final double price;
    private final boolean Explicit;

    public Album (int albumID,
           @NonNull String albumName,
           @NonNull String artist,
           int year,
           int trackTotal,
           @Nullable String genre,
           double price,
           @Nullable String country,
           boolean Explicit) {
            this.albumID = albumID;
            this.albumName = albumName;
            this.artist = artist;
            this.year = year;
            this.trackTotal = trackTotal;
            this.genre = genre;
            this.price = price;
            this.country = country;
            this.Explicit = Explicit;
        }

    @NonNull
    @Override
    public String toString() {
        return  "\nAlbum: " + this.albumName + "\t\tAlbumID: " + this.albumID +
                "\nArtist: " + this.artist + "\t\tYear: " + this.year +
                "\nGenre: " + this.genre + "\t\tTrack Total: " + this.trackTotal +
                "\nPrice: $" + String.format("%.2f", this.price) + "\t\tCountry: " + this.country +
                "\nExplicit? " + this.Explicit;
    }
}