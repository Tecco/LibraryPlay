package com.tecc0.libraryplay.data;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tecc0.libraryplay.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */
public class WeatherAdapter extends ArrayAdapter<WeatherData> {

    private LayoutInflater inflater;
    private int id;

    public WeatherAdapter(Context context, int id, ArrayList<WeatherData> data) {
        super(context, 0, data);

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.id = id;
    }

    class ViewHolder {
        @Bind(R.id.weather_item_image_view) ImageView weatherImage;
        @Bind(R.id.weather_item_max_textView) TextView max;
        @Bind(R.id.weather_item_min_textView) TextView min;
        @Bind(R.id.weather_item_weather_textView) TextView weather;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;

        if (convertView == null) {
            convertView = inflater.inflate(id, parent, false);
            h = new ViewHolder(convertView);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        WeatherData weatherData = getItem(position);

        h.max.setText(" max:" + weatherData.getMax());
        h.min.setText(" min:" + weatherData.getMin());
        h.weather.setText(" weather:" + weatherData.getWeather());

        Picasso.with(getContext()).load("http://openweathermap.org/img/w/" + weatherData.getIconId() + ".png")
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.progress_animation)
                .into(h.weatherImage);

        return convertView;
    }

}
