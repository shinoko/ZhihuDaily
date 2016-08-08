package com.example.administrator.zhihudaily.presenter;

import com.example.administrator.zhihudaily.ui.INewsDetailView;

/**
 * Created by shinoko on 2016/8/8.
 */
public class NewsDetailPresenter implements INewsDetailPresenter{

    INewsDetailView mView;

    public NewsDetailPresenter(INewsDetailView view){
        mView = view;
    }

    public void initView(){
        int id = mView.getNewsID();

        mView.setTitle("标题！");
        mView.setImageSource("《图片来源》");
        mView.setTitleImage("");
        mView.setContent("！！！！！！！！！！！！！");
    }
}
