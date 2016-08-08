package com.example.administrator.zhihudaily.ui;

/**
 * Created by shinoko on 2016/8/8.
 */
public interface INewsDetailView {

    void setTitle(String title);
    void setImageSource(String imageSource);
    void setTitleImage(String imgURI);
    void setContent(String content);
    int getNewsID();
}
