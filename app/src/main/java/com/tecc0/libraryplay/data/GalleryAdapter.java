package com.tecc0.libraryplay.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tecc0.libraryplay.R;

import java.util.ArrayList;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */
public class GalleryAdapter extends ArrayAdapter<GalleryData> {

    private LayoutInflater inflater;
    private int id;
    private ArrayList<GalleryData> data;

    public GalleryAdapter(Context context, int id , ArrayList<GalleryData> data) {
        super(context, id, data);

        this.id = id;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;

        if (convertView == null) {
            convertView = inflater.inflate(id, parent, false);
            h = new ViewHolder();
            // TODO: create item layout
            //h.imageId = (TextView) convertView.findViewById(R.id.name);
            //h.galleryImage = (TextView) convertView.findViewById(R.id.likes);
            //h.createdDate = (TextView) convertView.findViewById(R.id.comments);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        GalleryData i = getItem(position);

        h.imageId.setText(i.getId());
        h.createdDate.setText(i.getDate());
        Picasso.with(getContext()).load(i.getLink()).into(h.galleryImage);

        return convertView;
    }

    private class ViewHolder {
        private TextView imageId;
        private ImageView galleryImage;
        private TextView createdDate;
    }

}
