package com.tecc0.libraryplay.data;

/**
 * Created by makoto.nishimoto on 2016/03/03.
 */
public class GalleryData {

    private int imageId;
    private String date;
    private String link;
    private String userName;

    public GalleryData(int imageId, String date, String link, String userName) {
        this.imageId = imageId;
        this.date = date;
        this.link = link;
        this.userName = userName;
    }

    public int getImageId() {
        return imageId;
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

    public void setImageId(int imageId) {
        this.imageId = imageId;
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
