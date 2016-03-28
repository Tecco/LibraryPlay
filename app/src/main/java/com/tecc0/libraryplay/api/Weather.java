package com.tecc0.libraryplay.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */
public class Weather {

    @SerializedName("city")
    public City city;

    @SerializedName("list")
    public ArrayList<DayWeather> dayWeatherList;

}
