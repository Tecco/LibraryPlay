package com.tecc0.libraryplay.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.tecc0.libraryplay.R;
import com.tecc0.libraryplay.api.DayWeather;
import com.tecc0.libraryplay.api.WeatherApi;
import com.tecc0.libraryplay.api.Weather;
import com.tecc0.libraryplay.data.WeatherAdapter;
import com.tecc0.libraryplay.data.WeatherData;

import java.util.ArrayList;
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
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RetrofitFragment extends Fragment {

    private static int WEATHER_API_GET_COUNT = 15;

    @Bind(R.id.retrofit_listview) ListView listView;

    public RetrofitFragment() {

    }

    public static RetrofitFragment create() {
        RetrofitFragment fragment = new RetrofitFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_retrofit, container, false);
        ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Retrofit");
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
        adapter.create(WeatherApi.class).getWeather("daily", getString(R.string.weather_key), "TOKYO", WEATHER_API_GET_COUNT)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Weather>() {
                    @Override
                    public void onCompleted() {
                        Log.d("MainActivity", "onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("MainActivity", "Error : " + e.toString());
                    }

                    @Override
                    public void onNext(Weather weather) {
                        if (weather != null) {

                            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(weather.city.name + " : " + weather.city.country);

                            // 練習
//                            List<WeatherData> weatherList1 = Observable.from(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
//                                    .map(i -> {
//                                        DayWeather w = weather.dayWeatherList.get(i);
//                                        return new WeatherData(w.weather.get(0).icon, w.temp.max, w.temp.min, w.weather.get(0).main);
//                                    })
//                                    .toList().toBlocking().single();
//
//                            List<WeatherData> weatherList2 = Observable.just(0,1,2,3,4,5,6,7,8,9)
//                                    .map(i -> {
//                                        DayWeather w = weather.dayWeatherList.get(i);
//                                        return new WeatherData(w.weather.get(0).icon, w.temp.max, w.temp.min, w.weather.get(0).main);
//                                    })
//                                    .toList().toBlocking().single();
//
                            List<WeatherData> weatherList = Observable.range(0, WEATHER_API_GET_COUNT)
                                    .map(i -> {
                                        DayWeather w = weather.dayWeatherList.get(i);
                                        return new WeatherData(w.weather.get(0).icon, w.temp.max, w.temp.min, w.weather.get(0).main);
                                    })
                                    .toList().toBlocking().single();

                            // Observable使わないやつ
//                            for (int i = 0; i < 16; i++) {
//                                DayWeather w = weather.dayWeatherList.get(i);
//                                weatherList.add(new WeatherData(w.weather.get(0).icon, w.temp.max, w.temp.min, w.weather.get(0).main));
//                            }
//
                            ArrayAdapter<WeatherData> adapter = new WeatherAdapter(getActivity(), R.layout.weather_item, weatherList);
                            listView.setAdapter(adapter);
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
