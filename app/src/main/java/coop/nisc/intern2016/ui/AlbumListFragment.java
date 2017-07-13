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

    private static final String ARGUMENT_ALBUMS = "albums";

    private ArrayList<Album> albums;
    private OnClickListener onClickListener;

    static AlbumListFragment create(ArrayList<Album> albums) {
        Bundle arguments = new Bundle();
        arguments.putParcelableArrayList(ARGUMENT_ALBUMS, albums);

        AlbumListFragment fragment = new AlbumListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (savedInstanceState != null) {
            albums = savedInstanceState.getParcelableArrayList(ARGUMENT_ALBUMS);
        }
        if (albums == null && arguments != null) {
            albums = arguments.getParcelableArrayList(ARGUMENT_ALBUMS);
        }
        if (albums != null) {
            setListAdapter(new AlbumAdapter(getContext(), albums));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText(getString(R.string.no_results));
    }


    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.album_list_fragment_title));
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onListItemClick(ListView listView,
                                View view,
                                int position,
                                long id) {
        showAlbumDetailFragment(albums.get(position));
        if (onClickListener != null) {
            onClickListener.onClick();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARGUMENT_ALBUMS, albums);
    }

    public void setAlbums(ArrayList<Album> albums) {
        this.albums = albums;
        setListAdapter(new AlbumAdapter(getContext(), albums));
    }

    private void showAlbumDetailFragment(@NonNull Album album) {
        FragmentManager fragmentManager = getFragmentManager();

        //position will always point to an album, not to a null
        //noinspection ConstantConditions
        Fragment fragment = AlbumDetailsFragment.create(album);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right,
                                     R.anim.exit_to_left,
                                     R.anim.enter_from_left,
                                     R.anim.exit_to_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(TAG)
                .commit();
    }

    private static final class AlbumAdapter extends ArrayAdapter<Album> {

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
                viewHolder.collectionName.setText(album.collectionName);
                viewHolder.artistName.setText(album.artistName);
                viewHolder.releaseYear.setText(getYear(album.releaseDate));
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

        @NonNull
        private String getYear(@NonNull String releaseDate) {
            if (releaseDate.length() > 3) {
                return releaseDate.substring(0, 4);
            }
            return "";
        }

    }

    interface OnClickListener {

        void onClick();

    }

}


