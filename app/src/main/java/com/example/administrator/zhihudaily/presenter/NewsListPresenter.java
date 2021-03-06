package com.example.administrator.zhihudaily.presenter;

import android.util.Log;

import com.example.administrator.zhihudaily.model.News;
import com.example.administrator.zhihudaily.model.NewsList;
import com.example.administrator.zhihudaily.model.ThemeList;
import com.example.administrator.zhihudaily.network.NetworkManager;
import com.example.administrator.zhihudaily.ui.INewsListView;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by shinoko on 2016/8/9.
 */
public class NewsListPresenter {

    INewsListView mView;

    public NewsListPresenter(INewsListView view){
        mView = view;
    }

    public void getLatestNews(){
        NetworkManager.builder().getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
//                        showProgress();
                    }
                })
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
                        mView.setOnRefreshing(false);
                        if (newsList.getStories() == null) {
//                            mTvLoadEmpty.setVisibility(View.VISIBLE);
                        } else {
                            mView.changeNewsList(addDate(newsList.getStories(),newsList.getDate()));
                            mView.changeNewsBanner(newsList.getTop_stories());

                            mView.setCurrentDate(newsList.getDate());
//                            mTvLoadEmpty.setVisibility(View.GONE);
                        }

                        if (newsList.getStories().size() < 8) {
                            getBeforeNews(newsList.getDate());
                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.setOnRefreshing(false);
//                        mLoadLatestSnackbar.show();
//                        mTvLoadError.setVisibility(View.VISIBLE);
//                        mTvLoadEmpty.setVisibility(View.GONE);
                    }
                });

    }

    public void getBeforeNews(final String date){
        NetworkManager.builder().getBeforeNews(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsList>() {
                    @Override
                    public void call(NewsList newsList) {
//                        mAutoLoadListener.setLoading(false);
//                        mLoadBeforeSnackbar.dismiss();

                        mView.addNewsListData(addDate(newsList.getStories(),newsList.getDate()));
                        mView.setCurrentDate(newsList.getDate());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
//                        mAutoLoadListener.setLoading(false);
//                        mLoadBeforeSnackbar.show();
                    }
                });

    }

    public void getThemes(){
        NetworkManager.builder().getThemes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ThemeList>() {
                    @Override
                    public void call(ThemeList themeList) {
                        mView.setDrawerData(themeList.getOthers());
                        Log.d("menu","menu get items");
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });

    }


    private List<News> addDate(List<News> list, String date){
        ArrayList<News> newList = new ArrayList<>(list);
        for (int i = 0; i < newList.size(); i++) {
            newList.get(i).setDate(date);
        }
        return newList;
    }







}
