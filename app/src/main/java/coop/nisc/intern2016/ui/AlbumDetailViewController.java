package coop.nisc.intern2016.ui;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.model.Album;

final class AlbumDetailViewController {

    AlbumDetailViewController(@NonNull View view,
                              @NonNull Album album) {
        TextView collectionName = (TextView) view.findViewById(R.id.album_detail_collection_name);
        TextView artistName = (TextView) view.findViewById(R.id.album_detail_artist);
        TextView releaseYear = (TextView) view.findViewById(R.id.album_detail_release_date);
        TextView explicitness = (TextView) view.findViewById(R.id.album_detail_explicitness);
        collectionName.setText(album.getCollectionName());
        artistName.setText(album.getArtistName());
        releaseYear.setText(album.getReleaseYear());
        explicitness.setText(album.getExplicitness());
    }

}