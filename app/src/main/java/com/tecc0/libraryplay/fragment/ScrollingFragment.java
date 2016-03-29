package com.tecc0.libraryplay.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tecc0.libraryplay.R;

public class ScrollingFragment extends Fragment {

    public ScrollingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.fragment_coodinator, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.coordinator_fab);
        fab.setOnClickListener(view1 -> Snackbar.make(view1, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        return view;
    }

    public static ScrollingFragment create() {
        return new ScrollingFragment();
    }

}
