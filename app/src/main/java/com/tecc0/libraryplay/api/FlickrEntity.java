package com.tecc0.libraryplay.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */
public class FlickrEntity {

    @SerializedName("photos")
    public Photos photos;

    public class Photos {
        public String  page;
        public String pages;
        public String total;

        public List<Photo> photo;
    }

    public class Photo {
        public String id;
        public String owner;
        public String title;
    }
}
