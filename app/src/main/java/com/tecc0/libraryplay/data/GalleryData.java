package com.tecc0.libraryplay.data;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */
public class GalleryData {

    private String id;
    private String date;
    private String link;
    private String userName;

    public GalleryData(String id, String date, String link, String userName) {
        this.id = id;
        this.date = date;
        this.link = link;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }

    public String getUserName() {
        return userName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
