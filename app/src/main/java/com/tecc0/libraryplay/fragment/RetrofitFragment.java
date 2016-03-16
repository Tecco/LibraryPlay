package com.tecc0.libraryplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.tecc0.libraryplay.R;
import com.tecc0.libraryplay.api.WeatherApi;
import com.tecc0.libraryplay.api.WeatherEntity;

import java.util.Date;

import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitFragment extends Fragment {

    private static String API_KEY = "957cf0f0d7a9310682ba9197463f2c91";
    public RetrofitFragment() {

    }

    public static RetrofitFragment newInstance() {
        RetrofitFragment fragment = new RetrofitFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_retrofit, container, false);
        ButterKnife.bind(this, view);

        getWeatherApi(view);

        return view;
    }

    private void getWeatherApi(final View v) {// JSONのパーサー
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
                .baseUrl("http://api.openweathermap.org")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        // 非同期処理の実行
        adapter.create(WeatherApi.class).get("weather", API_KEY, "Tokyo,jp")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherEntity>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MainActivity", "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "Error : " + e.toString());
                    }

                    @Override
                    public void onNext(WeatherEntity weather) {
                        if (weather != null) {
                            ((TextView) v.findViewById(R.id.retrofit_textview)).setText(weather.weather.get(0).main);
                        }
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    
}
