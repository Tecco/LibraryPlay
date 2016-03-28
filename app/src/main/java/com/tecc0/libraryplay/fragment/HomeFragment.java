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

    public static HomeFragment create() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");

        ListView lv = findById(view, R.id.listView);
        lv.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_checked, getResources().getStringArray(R.array.study_libraries_name)));
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // なんかでまとめるかも
        lv.setItemChecked(0, true);
        lv.setItemChecked(1, false);
        lv.setItemChecked(2, true);
        lv.setItemChecked(3, true);
        lv.setItemChecked(4, true);
        lv.setItemChecked(5, true);
        lv.setItemChecked(6, true);
        lv.setItemChecked(7, true);

        return view;
    }

}
