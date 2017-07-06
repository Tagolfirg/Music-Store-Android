package coop.nisc.intern2016.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import coop.nisc.intern2016.R;

public final class DefaultFragment extends Fragment {

    public static final String TAG = "DefaultFragment";

    @NonNull
    public static DefaultFragment create() {
        //noinspection deprecation
        return new DefaultFragment();
    }

    @Deprecated
    public DefaultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.no_results, container, false);
    }

}
