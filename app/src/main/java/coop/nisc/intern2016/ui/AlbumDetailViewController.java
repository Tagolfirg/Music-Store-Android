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
        collectionName.setText(album.getCollectionName());

        TextView artistName = (TextView) view.findViewById(R.id.album_detail_artist);
        artistName.setText(album.getArtistName());

        TextView releaseYear = (TextView) view.findViewById(R.id.album_detail_release_date);
        releaseYear.setText(album.getReleaseYear());

        TextView explicitness = (TextView) view.findViewById(R.id.album_detail_explicitness);
        explicitness.setText(album.getExplicitness());
    }

}