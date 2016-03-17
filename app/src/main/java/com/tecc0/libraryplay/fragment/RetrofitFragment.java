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

public class RetrofitFragment extends Fragment {

    @Bind(R.id.retrofit_listview) ListView listView;

    private static String API_KEY = "957cf0f0d7a9310682ba9197463f2c91";
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
        adapter.create(WeatherApi.class).getWeather("daily", API_KEY, "TOKYO", 16)
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
                            ArrayList<WeatherData> weatherList = new ArrayList<>();

                            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(weather.city.name + " : " + weather.city.country);
                            for (int i = 0; i < 16; i++) {
                                DayWeather w = weather.dayWeatherList.get(i);
                                weatherList.add(new WeatherData(w.weather.get(0).icon, w.temp.max, w.temp.min, w.weather.get(0).main));
                            }

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
