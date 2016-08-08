package com.example.administrator.zhihudaily.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.bean.News;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String KEY_NEWS = "key_news";

    private FragmentTransaction mFragmentTransaction;
    private NewsDetailFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragment = new NewsDetailFragment(3892357);
        mFragmentTransaction.add(R.id.news_detail, mFragment);
        mFragmentTransaction.commit();
    }

    public static void start(Context context, News news) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
//        intent.putExtra(KEY_NEWS, news);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        Log.e("", "start: !");
    }

    public void onNewsChange(){


    }
}
