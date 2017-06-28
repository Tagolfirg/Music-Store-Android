package coop.nisc.intern2016.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.model.Track;

public final class TrackDetailsFragment extends Fragment {

    private static final String ARGUMENT_TRACK = "track";

    private Track track;

    @Deprecated
    public TrackDetailsFragment() {
    }

    @NonNull
    public static TrackDetailsFragment create(@NonNull Track track) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(ARGUMENT_TRACK, track);

        //noinspection deprecation
        TrackDetailsFragment trackDetailsFragment = new TrackDetailsFragment();
        trackDetailsFragment.setArguments(arguments);
        return trackDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        track = getArguments().getParcelable(ARGUMENT_TRACK);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getString(R.string.track_details_fragment_title));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup parent,
                             Bundle savedInstanceState) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.track_details, parent, false);
        new TrackDetailViewController(root, track);
        return root;
    }

}