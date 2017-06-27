package coop.nisc.intern2016.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.math.BigDecimal;

public final class Track implements Parcelable {

    public static final Creator<Track> CREATOR = new Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    public final String artistName;
    public final String collectionName;
    public final int discCount;
    public final int discNumber;
    public final String primaryGenreName;
    public final String trackExplicitness;
    public final String trackName;
    public final int trackNumber;
    public final BigDecimal trackPrice;
    public final int trackTimeMillis;

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

    private Track(@NonNull Parcel in) {
        artistName = in.readString();
        collectionName = in.readString();
        discCount = in.readInt();
        discNumber = in.readInt();
        primaryGenreName = in.readString();
        trackExplicitness = in.readString();
        trackName = in.readString();
        trackNumber = in.readInt();
        trackPrice = BigDecimal.valueOf(in.readDouble());
        trackTimeMillis = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest,
                              int flags) {
        dest.writeString(artistName);
        dest.writeString(collectionName);
        dest.writeInt(discCount);
        dest.writeInt(discNumber);
        dest.writeString(primaryGenreName);
        dest.writeString(trackExplicitness);
        dest.writeString(trackName);
        dest.writeInt(trackNumber);
        dest.writeDouble(trackPrice.doubleValue());
        dest.writeInt(trackTimeMillis);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
