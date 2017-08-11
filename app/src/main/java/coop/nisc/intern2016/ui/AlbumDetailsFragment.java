package coop.nisc.intern2016.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.model.Album;
import coop.nisc.intern2016.model.Track;

import java.util.ArrayList;

public final class AlbumDetailsFragment extends Fragment {

    public static final String TAG = "AlbumDetailsFragment";

    private static final String ARGUMENT_ALBUM = "album";

    private boolean firstTime = true;

    Album album;

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
        firstTime = (savedInstanceState == null);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_album_details, parent, false);
        if (firstTime && album.tracks != null && album.tracks.size() == 0) {
            setTracks(root, null);
        } else {
            setTracks(root, album.tracks);
        }
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,
                                    MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        (menu.findItem(R.id.search)).setVisible(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.album_details_fragment_title));
    }

    private void showTrackDetailsFragment(@NonNull Track track) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = TrackDetailsFragment.create(track);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right,
                                     R.anim.exit_to_left,
                                     R.anim.enter_from_left,
                                     R.anim.exit_to_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(TAG)
                .commit();
    }

    void setTracks(@Nullable View root,
                   @Nullable ArrayList<Track> tracks) {
        album.tracks = tracks;
        setupTracksViews(root);
    }

    void setArtwork(Bitmap bitmap) {
        album.artwork = bitmap;
        setupTracksViews(null);
    }

    private void setupTracksViews(@Nullable View root) {
        if (root == null) {
            root = getView();
        }
        //This is always called after onCreateView, so root is never null
        //noinspection ConstantConditions
        AlbumDetailViewController controller = new AlbumDetailViewController(root, album);
        controller.setOnClickListener(this::showTrackDetailsFragment);
    }

}