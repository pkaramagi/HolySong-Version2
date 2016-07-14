package com.karamagi.holysongs.holysong;

/**
 * Created by Philip on 4/22/2016.
 */

public class HolySongItem {
    private  String title;
    private String id;
    private String url;


    public HolySongItem(String audioTitle, String id, String url) {
        this.title = audioTitle;
        this.id = id;
        this.url = url;


    }

    @Override
    public String toString() {
        return title;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
    public String getUrl() {
        return url;
    }

}
