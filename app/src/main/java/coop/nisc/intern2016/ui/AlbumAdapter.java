package coop.nisc.intern2016.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import coop.nisc.intern2016.model.Album;

import java.util.ArrayList;

final class AlbumAdapter extends BaseAdapter {

    private final ArrayList<Album> albumArrayList;
    private final int layout;
    private final Context context;

    AlbumAdapter(@NonNull Context context,
                 @NonNull ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
        layout = android.R.layout.two_line_list_item;
    }

    @Override
    public int getCount() {
        return albumArrayList.size();
    }

    @NonNull
    @Override
    public Object getItem(int position) {
        return albumArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position,
                        @Nullable View convertView,
                        @NonNull ViewGroup parent) {
        final albumViewHolder albumViewHolder = new albumViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, parent, false);
        }
        albumViewHolder.collectionName = (TextView) convertView.findViewById(android.R.id.text1);
        albumViewHolder.artistName = (TextView) convertView.findViewById(android.R.id.text2);
        convertView.setTag(albumViewHolder);
        albumViewHolder.collectionName.setText(albumArrayList.get(position).getCollectionName());
        albumViewHolder.artistName.setText(albumArrayList.get(position).getArtistName());
        return convertView;
    }

    private class albumViewHolder {

        TextView collectionName;
        TextView artistName;
    }

}
