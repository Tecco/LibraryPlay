package com.tecc0.libraryplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.tecc0.libraryplay.R;
import com.tecc0.libraryplay.api.FlickrApi;
import com.tecc0.libraryplay.api.FlickrEntity;
import com.tecc0.libraryplay.data.GalleryAdapter;
import com.tecc0.libraryplay.data.GalleryData;

import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GalleryFragment extends Fragment {

    private static String FLICKR_API_KEY = "2d2e76fc5a867664d3df6fded18f9c6e";

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

        getFlickrApi(view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void getFlickrApi(final View v) {// JSONのパーサー
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();

        // add log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // RestAdapterの生成
        Retrofit adapter = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/rest/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        // 非同期処理の実行
        adapter.create(FlickrApi.class).get(FLICKR_API_KEY, "flickr.photos.search", "cat", "json", 1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FlickrEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MainActivity", "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "Error : " + e.toString());
                    }

                    @Override
                    public void onNext(FlickrEntity flickr) {
                        if (flickr != null) {
                            // TODO: from API

                            ArrayList<GalleryData> gallerydata = new ArrayList<>();

                            for (int i = 0; i < 10; i++) {
                                FlickrEntity.Photo p = flickr.photos.photo.get(i);
                                gallerydata.add(new GalleryData(i, p.title, String.format("http://c2.staticflickr.com/%s/%s/%s_%s.jpg", p.farm, p.server, p.id, p.secret, p.owner), p.owner));
                            }

                            ArrayAdapter<GalleryData> adapter = new GalleryAdapter(getActivity(), R.layout.gallery_item, gallerydata);
                            gridView.setAdapter(adapter);
                        }
                    }
                });

    }

}
