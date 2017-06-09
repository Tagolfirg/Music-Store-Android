package coop.nisc.intern2016.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import coop.nisc.intern2016.R;
import coop.nisc.intern2016.model.Album;

import java.util.ArrayList;

public class AlbumFragment extends ListFragment {

    private static final String ALBUM_LIST = "ALBUM_LIST";

    @NonNull
    public static AlbumFragment create(@NonNull ArrayList<Album> albumArrayList) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ALBUM_LIST, albumArrayList);
        AlbumFragment fragment = new AlbumFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public AlbumFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        ArrayList<Album> albumArrayList = getArguments().getParcelableArrayList(ALBUM_LIST);
        ((ListView) view.findViewById(android.R.id.list)).setAdapter(
                new AlbumAdapter(getContext(), albumArrayList != null ? albumArrayList : new ArrayList<Album>())
        );
        return view;
    }

}
