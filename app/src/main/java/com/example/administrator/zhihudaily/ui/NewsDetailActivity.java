package com.example.administrator.zhihudaily.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.model.BannerNews;
import com.example.administrator.zhihudaily.model.News;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String KEY_NEWS = "key_news";

    private FragmentTransaction mFragmentTransaction;
    private NewsDetailFragment mFragment;

    private Toolbar mToolbar;

    private int newsID = 8661316;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Intent intent = getIntent();
        newsID = intent.getIntExtra(KEY_NEWS,8661316);

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragment = new NewsDetailFragment(this,newsID);
        mFragmentTransaction.add(R.id.news_detail, mFragment);
        mFragmentTransaction.commit();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStop() {
        mToolbar.setAlpha(1.0f);
        mToolbar.getBackground().setAlpha(255);
        mToolbar.invalidate();

        Log.d("NewsDetailActivity"," on stop");

        super.onStop();
    }

    public static void start(Context context, News news) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(KEY_NEWS, news.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startFromBanner(Context context, BannerNews bannerNews) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(KEY_NEWS, bannerNews.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void onNewsChange(){


    }

    public Toolbar getToolbar(){
        return mToolbar;
    }
}
