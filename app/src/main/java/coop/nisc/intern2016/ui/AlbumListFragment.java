package coop.nisc.intern2016.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import coop.nisc.intern2016.R;
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

    @Override
    public void onListItemClick(ListView listView,
                                View view,
                                int position,
                                long id) {
        super.onListItemClick(listView, view, position, id);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(AlbumDetailsFragment.TAG);
        if (fragment == null) {

            //position will always point to an album, not to a null
            //noinspection ConstantConditions
            fragment = AlbumDetailsFragment.create((Album) getArguments().getParcelableArrayList(
                    ARGUMENT_ALBUMS).get(position));
            fragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right,
                                         R.anim.exit_to_left,
                                         R.anim.enter_from_left,
                                         R.anim.exit_to_right)
                    .replace(R.id.fragment_container, fragment, AlbumDetailsFragment.TAG)
                    .addToBackStack(TAG)
                    .commit();
        }
    }

    private final class AlbumAdapter extends ArrayAdapter<Album> {

        AlbumAdapter(@NonNull Context context,
                     @NonNull ArrayList<Album> albumArrayList) {
            super(context, R.layout.list_item_album, albumArrayList);
        }

        @NonNull
        @Override
        public View getView(int position,
                            @Nullable View convertView,
                            @NonNull ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_album, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Album album = getItem(position);
            if (album != null) {
                viewHolder.collectionName.setText(album.getCollectionName());
                viewHolder.artistName.setText(album.getArtistName());
                viewHolder.releaseYear.setText(album.getReleaseYear());
            }
            return convertView;
        }

        private final class ViewHolder {

            final TextView collectionName;
            final TextView artistName;
            final TextView releaseYear;

            ViewHolder(@NonNull View view) {
                collectionName = (TextView) view.findViewById(R.id.album_title);
                artistName = (TextView) view.findViewById(R.id.artist_name);
                releaseYear = (TextView) view.findViewById(R.id.release_year);
            }

        }

    }

}


