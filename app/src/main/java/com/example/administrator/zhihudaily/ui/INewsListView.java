package com.example.administrator.zhihudaily.ui;

import com.example.administrator.zhihudaily.model.BannerNews;
import com.example.administrator.zhihudaily.model.News;

import java.util.List;

/**
 * Created by shinoko on 2016/8/9.
 */
public interface INewsListView {

    void setOnRefreshing(boolean onRefreshing);

    void changeNewsList(List<News> newsList);

    void changeNewsBanner(List<BannerNews> bannerNewsList);

    String getCurrentDate();

    void setCurrentDate(String date);

    void addNewsListData(List<News> listToAdd);




}
