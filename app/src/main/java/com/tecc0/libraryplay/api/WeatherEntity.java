package com.tecc0.libraryplay.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */
public class WeatherEntity {
//    public String base;
//    public String city;
//    public List<Weather> weather;
//
//    public class Weather {
//        public String  id;
//        public String main;
//        public String description;
//        public String icon;
//    }

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
        public ArrayList<Weather> weather;
        public Temp temp;
    }

    public class Weather {
        public String main;
        public String icon;
    }
    public class Temp {
        public float min;
        public float max;
    }
}
