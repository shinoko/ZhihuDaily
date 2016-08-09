package com.example.administrator.zhihudaily.ui;

import com.example.administrator.zhihudaily.model.NewsDetail;

/**
 * Created by shinoko on 2016/8/8.
 */
public interface INewsDetailView {

    void setTitle(String title);
    void setImageSource(String imageSource);
    void setTitleImage(String imgURI);
    void setWebView(NewsDetail newsDetail);
    int getNewsID();
}
