package coop.nisc.intern2016.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.model.Album;
import coop.nisc.intern2016.model.Track;

public final class AlbumDetailsFragment extends Fragment implements AlbumDetailViewController.OnClickListener {

    public static final String TAG = "AlbumDetailsFragment";

    private static final String ARGUMENT_ALBUM = "album";

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
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getString(R.string.album_details_fragment_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.album_details, parent, false);
        //album is never null
        //noinspection ConstantConditions
        AlbumDetailViewController controller = new AlbumDetailViewController(root, album);
        controller.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(@NonNull Track track) {
        showTrackDetailsFragment(track);
    }

    private void showTrackDetailsFragment(@NonNull Track track) {
        FragmentManager fragmentManager = getFragmentManager();
        //position will always point to an album, not to a null
        //noinspection ConstantConditions
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

}