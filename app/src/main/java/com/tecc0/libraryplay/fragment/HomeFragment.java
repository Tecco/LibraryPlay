package com.tecc0.libraryplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tecc0.libraryplay.R;

import static butterknife.ButterKnife.findById;

public class HomeFragment extends Fragment {

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");

        ListView lv = findById(view, R.id.listView);
        lv.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.study_libraries_name)));
        return view;
    }

}
