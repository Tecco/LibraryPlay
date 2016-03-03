package com.tecc0.libraryplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tecc0.libraryplay.R;
import com.tecc0.libraryplay.data.GalleryAdapter;
import com.tecc0.libraryplay.data.GalleryData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GalleryFragment extends Fragment {

    @Bind(R.id.gallery_listview) ListView gridView;

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
        ButterKnife.bind(this, view);

        // TODO: from API
        ArrayList<GalleryData> gallerydata = new ArrayList<>();
        gallerydata.add(new GalleryData(1,"2015-01-01","http://tecc0.com/images/portfolio/android/tamagoya.png","nobita"));
        gallerydata.add(new GalleryData(2,"2015-01-02","http://tecc0.com/images/portfolio/android/tamagoya.png","shizuka"));

        ArrayAdapter<GalleryData> adapter = new GalleryAdapter(getActivity(), R.layout.gallery_item, gallerydata);
        gridView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
