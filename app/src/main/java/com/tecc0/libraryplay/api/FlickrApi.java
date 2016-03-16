package com.tecc0.libraryplay.api;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface FlickrApi {

    @GET("/services/rest/")
    Observable<FlickrEntity> get(@Query("api_key") String apiKey, @Query("method") String method, @Query("text") String word, @Query("format") String format, @Query("nojsoncallback") int num);

}
