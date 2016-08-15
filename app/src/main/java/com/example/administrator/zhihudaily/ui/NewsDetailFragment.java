package com.example.administrator.zhihudaily.ui;


import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.model.NewsDetail;
import com.example.administrator.zhihudaily.presenter.INewsDetailPresenter;
import com.example.administrator.zhihudaily.presenter.NewsDetailPresenter;
import com.example.administrator.zhihudaily.util.HtmlUtil;
import com.facebook.drawee.view.SimpleDraweeView;


public class NewsDetailFragment extends Fragment implements INewsDetailView{

    private int newsID = 3892357;

    private NewsDetailActivity mActivity;

    private AppBarLayout mAppBarLayout;

    private RelativeLayout mTitleSection;
    private TextView mTitle;
    private TextView mImageSource;
    private SimpleDraweeView mTitleImage;

    private NestedScrollView mScrollView;
    private WebView mWebView;

    private INewsDetailPresenter mPresenter;



    public NewsDetailFragment(NewsDetailActivity activity, int id) {
        newsID = id;
        mActivity = activity;
        mPresenter = new NewsDetailPresenter(this);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_detail, container, false);

        mAppBarLayout = (AppBarLayout) rootView.findViewById(R.id.app_bar);
//        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                Log.d("appbar ","offset:"+verticalOffset);
//                int maxScroll = appBarLayout.getTotalScrollRange();
//                Log.d("appbar ","max offset:"+maxScroll);
//
//                float percentage = (float) (maxScroll-Math.abs(verticalOffset)) / (float) maxScroll;
//                int percentagei = (int) (percentage * 255);
//                Log.d("appbar ","percentage:"+percentage);
//                Log.d("appbar ","percentagei:"+percentagei);
//                mActivity.getToolbar().setAlpha(percentage);
//                mActivity.getToolbar().getBackground().setAlpha(percentagei);
//
//            }
//        });



        mTitleSection = (RelativeLayout) rootView.findViewById(R.id.news_title_section);
        mTitle = (TextView) rootView.findViewById(R.id.news_title);
        mImageSource = (TextView) rootView.findViewById(R.id.news_image_source);
        mTitleImage = (SimpleDraweeView) rootView.findViewById(R.id.news_image);

        mWebView = (WebView) rootView.findViewById(R.id.webview_news);
        mScrollView = (NestedScrollView) rootView.findViewById(R.id.scrollView);

        mPresenter.initView();

        return rootView;
    }

    public int getNewsID(){
        return newsID;
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setImageSource(String imageSource) {
        mImageSource.setText(imageSource);
    }

    @Override
    public void setTitleImage(String imgURI) {
        mTitleImage.setImageURI(Uri.parse(imgURI));
    }


    @Override
    public void setWebView(NewsDetail newsDetail) {

        StringBuffer stringBuffer = HtmlUtil.handleHtml(newsDetail.getBody(),
                newsDetail.getCss(), newsDetail.getJs());
        mWebView.setDrawingCacheEnabled(true);
        mWebView.loadDataWithBaseURL("file:///android_asset/", stringBuffer.toString(), "text/html", "utf-8", null);

    }

}
