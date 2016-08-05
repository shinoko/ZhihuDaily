package com.example.administrator.zhihudaily.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.administrator.zhihudaily.bean.News;
import com.example.administrator.zhihudaily.adapter.NewsAdapter;
import com.example.administrator.zhihudaily.adapter.NewsBannerAdapter;
import com.example.administrator.zhihudaily.R;

public class NewsListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private Toolbar mToolbar;

    private RecyclerView mNewsListView;
    private RecyclerView.Adapter mNewsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    private ViewPager mViewPager;
    private NewsBannerAdapter mBannerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mNewsListView = (RecyclerView) findViewById(R.id.news_list_view);
        mNewsListView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mNewsListView.setLayoutManager(mLayoutManager);

        mNewsAdapter = new NewsAdapter(News.getTestList());
        mNewsListView.setAdapter(mNewsAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

//        mViewPager = (ViewPager) findViewById(R.id.banner_viewpager);
//        mBannerAdapter = new NewsBannerAdapter(this,BannerNews.getTestList());
//        mViewPager.setAdapter(mBannerAdapter);

    }


    @Override
    public void onRefresh() {

    }
}
