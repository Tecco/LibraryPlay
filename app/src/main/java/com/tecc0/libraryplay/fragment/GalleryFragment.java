package com.tecc0.libraryplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tecc0.libraryplay.R;

import static butterknife.ButterKnife.findById;

public class GalleryFragment extends Fragment {

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

        ListView lv = findById(view, R.id.listView);
        lv.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.study_libraries_name)));
        return view;
    }

}