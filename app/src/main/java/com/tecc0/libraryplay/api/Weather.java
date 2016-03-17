package com.tecc0.libraryplay.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */
public class Weather {

    @SerializedName("city")
    public City city;

    @SerializedName("list")
    public ArrayList<DayWeather> list;

    public class City {
        public String id;
        public String name;
        public String country;
    }

    public class DayWeather {
        public int dt;
        public ArrayList<Forecast> weather;
        public Temp temp;
    }

    public class Forecast {
        public String main;
        public String icon;
    }

    public class Temp {
        public float min;
        public float max;
    }
}
