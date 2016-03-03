package com.tecc0.libraryplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import com.tecc0.libraryplay.R;
import com.tecc0.libraryplay.data.GalleryAdapter;
import com.tecc0.libraryplay.data.GalleryData;

import java.util.ArrayList;

import butterknife.Bind;

import static butterknife.ButterKnife.findById;

public class GalleryFragment extends Fragment {

    @Bind(R.id.gallery_gridview) GridView gridView;

    public GalleryFragment() {

    }

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        // TODO: from API
        //ArrayList<GalleryData> gallerydata =
        // TODO: item layout
        //ArrayAdapter<GalleryData> adapter = new GalleryAdapter(getContext(), R.layout.item, itemDataList);
        // TODO: setAdapter

        return view;
    }

}
