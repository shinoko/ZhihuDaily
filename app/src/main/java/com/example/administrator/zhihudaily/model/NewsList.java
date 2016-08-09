package com.example.administrator.zhihudaily.model;

import java.util.List;

/**
 * Created by shinoko on 2016/8/9.
 */
public class NewsList {
    String date;
    List<News> stories;
    List<BannerNews> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<News> getStories() {
        return stories;
    }

    public void setStories(List<News> stories) {
        this.stories = stories;
    }

    public List<BannerNews> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<BannerNews> top_stories) {
        this.top_stories = top_stories;
    }
}
