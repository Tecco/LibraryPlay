package com.tecc0.libraryplay.api;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface WeatherApi {

    @GET("/data/2.5/{name}")
    Observable<WeatherEntity> get(@Path("name") String name, @Query("q") String q);

}
