package coop.nisc.intern2016.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import coop.nisc.intern2016.model.Album;

import java.util.ArrayList;

public final class AlbumListFragment extends ListFragment {

    public static final String TAG = "AlbumListFragment";

    private static final String ARGUMENT_ALBUMS = "albumList";

    @Deprecated
    public AlbumListFragment() {
    }

    @NonNull
    public static AlbumListFragment create(@NonNull ArrayList<Album> albumArrayList) {
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ARGUMENT_ALBUMS, albumArrayList);

        //noinspection deprecation
        AlbumListFragment fragment = new AlbumListFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Album> albums = getArguments().getParcelableArrayList(ARGUMENT_ALBUMS);

        //albums is never null
        //noinspection ConstantConditions
        setListAdapter(new AlbumAdapter(getContext(), albums));
    }

    private final class AlbumAdapter extends ArrayAdapter<Album> {

        AlbumAdapter(@NonNull Context context,
                     @NonNull ArrayList<Album> albumArrayList) {
            super(context, android.R.layout.two_line_list_item, albumArrayList);
        }

        @NonNull
        @Override
        public View getView(int position,
                            @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(android.R.layout.two_line_list_item, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Album album = getItem(position);
            if (album != null) {
                viewHolder.collectionName.setText(album.getCollectionName());
                viewHolder.artistName.setText(album.getArtistName());
            }
            return convertView;
        }

        private final class ViewHolder {

            final TextView collectionName;
            final TextView artistName;

            ViewHolder(@NonNull View convertView) {
                collectionName = (TextView) convertView.findViewById(android.R.id.text1);
                artistName = (TextView) convertView.findViewById(android.R.id.text2);
            }

        }

    }

}

