package com.example.administrator.zhihudaily.presenter;

import com.example.administrator.zhihudaily.model.NewsDetail;
import com.example.administrator.zhihudaily.network.NetworkManager;
import com.example.administrator.zhihudaily.ui.INewsDetailView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

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

        NetworkManager.builder().getNewsDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NewsDetail>() {
                    @Override
                    public void call(NewsDetail newsDetail) {
                        if (newsDetail != null){
                            mView.setTitle(newsDetail.getTitle());
                            mView.setImageSource(newsDetail.getImage_source());
                            mView.setTitleImage(newsDetail.getImage());

                            mView.setWebView(newsDetail);
                        }


                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {



                    }
                });
    }
}
