package com.tecc0.libraryplay.api;

import java.util.List;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */
public class WeatherEntity {
    public String base;
    public List<Weather> weather;

    public class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }
}
