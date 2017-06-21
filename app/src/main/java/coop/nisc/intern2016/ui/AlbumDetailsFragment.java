package coop.nisc.intern2016.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.model.Album;

public final class AlbumDetailsFragment extends Fragment {

    public static final String TAG = "AlbumDetailsFragment";

    private static final String ARGUMENT_ALBUM = "currentAlbum";
    private Album album;

    @Deprecated
    public AlbumDetailsFragment() {
    }

    @NonNull
    public static AlbumDetailsFragment create(@NonNull Album album) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_ALBUM, album);

        //noinspection deprecation
        AlbumDetailsFragment albumDetailsFragment = new AlbumDetailsFragment();
        albumDetailsFragment.setArguments(arguments);
        return albumDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        album = getArguments().getParcelable(ARGUMENT_ALBUM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.album_details, parent, false);

        //album is never null
        //noinspection ConstantConditions
        new AlbumDetailViewController(root, album);
        return root;
    }

}