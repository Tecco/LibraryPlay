package com.tecc0.libraryplay.data;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */
public class WeatherData {

    private String iconId;
    private float max;
    private float min;
    private String weather;

    public WeatherData(String iconId, float max, float min, String weather) {
        this.iconId = iconId;
        this.max = max;
        this.min = min;
        this.weather = weather;
    }

    public String getIconId() {
        return iconId;
    }

    public float getMax() {
        return max;
    }

    public float getMin() {
        return min;
    }

    public String getWeather() {
        return weather;
    }

}
