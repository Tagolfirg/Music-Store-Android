package coop.nisc.intern2016.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;

public final class Album implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @NonNull
        public Album createFromParcel(@NonNull Parcel in) {
            return new Album(in);
        }

        @NonNull
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @NonNull public final String artistName;
    @NonNull public final String collectionExplicitness;
    @NonNull public final String collectionId;
    @NonNull public final String collectionName;
    private final BigDecimal collectionPrice;
    private final String country;
    private final String primaryGenreName;
    @NonNull public final String releaseDate;
    private final int trackCount;

    @NonNull public ArrayList<Track> tracks = new ArrayList<>();

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

    private Album(@NonNull Parcel parcel) {
        artistName = parcel.readString();
        collectionExplicitness = parcel.readString();
        collectionId = parcel.readString();
        collectionName = parcel.readString();
        collectionPrice = BigDecimal.valueOf(parcel.readDouble());
        country = parcel.readString();
        primaryGenreName = parcel.readString();
        releaseDate = parcel.readString();
        trackCount = parcel.readInt();

        // The type is guaranteed to be ArrayList<Track>
        // noinspection unchecked
        tracks = parcel.readArrayList(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest,
                              int flags) {
        dest.writeString(artistName);
        dest.writeString(collectionExplicitness);
        dest.writeString(collectionId);
        dest.writeString(collectionName);
        dest.writeDouble(collectionPrice.doubleValue());
        dest.writeString(country);
        dest.writeString(primaryGenreName);
        dest.writeString(releaseDate);
        dest.writeInt(trackCount);
        dest.writeList(tracks);
    }

}