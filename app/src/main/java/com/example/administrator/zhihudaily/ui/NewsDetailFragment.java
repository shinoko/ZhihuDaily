package com.example.administrator.zhihudaily.ui;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private RelativeLayout mTitleSection;
    private TextView mTitle;
    private TextView mImageSource;
    private SimpleDraweeView mTitleImage;

    private WebView mWebView;

    private INewsDetailPresenter mPresenter;



    public NewsDetailFragment(int id) {
        newsID = id;

        mPresenter = new NewsDetailPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_detail, container, false);

        mTitleSection = (RelativeLayout) rootView.findViewById(R.id.news_title_section);
        mTitle = (TextView) rootView.findViewById(R.id.news_title);
        mImageSource = (TextView) rootView.findViewById(R.id.news_image_source);
        mTitleImage = (SimpleDraweeView) rootView.findViewById(R.id.news_image);

        mWebView = (WebView) rootView.findViewById(R.id.webview_news);

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
