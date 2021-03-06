package com.tecc0.libraryplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.ksoichiro.android.observablescrollview.ObservableListView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.tecc0.libraryplay.R;
import com.tecc0.libraryplay.api.Flickr;
import com.tecc0.libraryplay.api.FlickrApi;
import com.tecc0.libraryplay.data.GalleryAdapter;
import com.tecc0.libraryplay.data.GalleryData;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GalleryFragment extends Fragment implements ObservableScrollViewCallbacks{

    // 良い方法が思いつかないです かなしい
    private static int num = 0;

    @Bind(R.id.gallery_listview) ObservableListView listView;

    public GalleryFragment() {

    }

    public static GalleryFragment create() {
        return new GalleryFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        ButterKnife.bind(this, view);

        // TODO: こゆのはぬるぽになるので気をつけよう私
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Picasso");
        getFlickrApi();

        listView.setScrollViewCallbacks(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    private void getFlickrApi() {// JSONのパーサー
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
        adapter.create(FlickrApi.class).getFlickr(getString(R.string.flickr_key), "flickr.photos.search", "cat", "json", 1)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Flickr>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MainActivity", "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "Error : " + e.toString());
                    }

                    @Override
                    public void onNext(Flickr flickr) {
                        if (flickr != null) {

                            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("total:" + flickr.photos.total + "  pages:" + flickr.photos.pages);

                            // Observable使わないやつ
//                            ArrayList<GalleryData> galleryList = new ArrayList<>();
//
//                            Photos info = flickr.photos;
//                            galleryList.add(new GalleryData(0, " total:" + info.total, null, " pages:" + info.pages));
//                            for (int i = 1; i < 100; i++) {
//                                Photo p = flickr.photos.photo.get(i);
//                                galleryList.add(new GalleryData(i, p.title, String.format("http://c2.staticflickr.com/%s/%s/%s_%s.jpg", p.farm, p.server, p.id, p.secret, p.owner), p.owner));
//                            }

                            List<GalleryData> galleryList = Observable.from(flickr.photos.photo)
                                    .map(p -> {
                                                num++;
                                                return new GalleryData(num, p.title, String.format("http://c2.staticflickr.com/%s/%s/%s_%s.jpg", p.farm, p.server, p.id, p.secret, p.owner), p.owner);
                                            }
                                    )
                                    .toList().toBlocking().single();

                            ArrayAdapter<GalleryData> adapter = new GalleryAdapter(getActivity(), R.layout.gallery_item, galleryList);
                            listView.setAdapter(adapter);
                        }
                    }
                });

    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (scrollState == ScrollState.UP) {
            if (ab.isShowing()) {
                ab.hide();
            }
        } else if (scrollState == ScrollState.DOWN) {
            if (!ab.isShowing()) {
                ab.show();
            }
        }
    }
}
