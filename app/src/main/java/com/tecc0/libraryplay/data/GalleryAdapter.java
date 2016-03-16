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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */
public class GalleryAdapter extends ArrayAdapter<GalleryData> {

    private LayoutInflater inflater;
    private int id;

    public GalleryAdapter(Context context, int id , ArrayList<GalleryData> data) {
        super(context, 0, data);

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.id = id;
    }

    class ViewHolder {
        @Bind(R.id.gallery_item_id_textView) TextView imageId;
        @Bind(R.id.gallery_item_user_name_textView) TextView userName;
        @Bind(R.id.gallery_item_image_view) ImageView galleryImage;
        @Bind(R.id.gallery_item_created_date_textView) TextView createdDate;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;

        if (convertView == null) {
            convertView = inflater.inflate(id, parent, false);
            h = new ViewHolder(convertView);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        GalleryData i = getItem(position);

        h.imageId.setText(String.valueOf(i.getImageId()));
        h.userName.setText(i.getUserName());
        h.createdDate.setText(i.getDate());
        Picasso.with(getContext()).load(i.getLink())
                .placeholder(R.drawable.ic_menu_camera)
                .error(R.drawable.ic_menu_camera)
                .into(h.galleryImage);

        return convertView;
    }

}
