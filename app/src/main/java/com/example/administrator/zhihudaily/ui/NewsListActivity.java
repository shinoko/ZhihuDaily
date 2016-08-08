package com.example.administrator.zhihudaily.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.adapter.NewsAdapter;
import com.example.administrator.zhihudaily.adapter.NewsBannerAdapter;
import com.example.administrator.zhihudaily.model.BannerNews;
import com.example.administrator.zhihudaily.model.News;

import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Toolbar mToolbar;

    private RecyclerView mNewsListView;
    private NewsAdapter mNewsAdapter;
    private LinearLayoutManager mLayoutManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private List<News> mNewsList;


    private View mHeader;
    private ViewPager mViewPager;
    private NewsBannerAdapter mBannerAdapter;
    private List<BannerNews> mBannerNewsList = new ArrayList<>();
    private LinearLayout mLLDotGroupParent;
    private LinearLayout mLLDotGroup;

    //banner当前位置
    private int mBannerPosition = 0;
    //小圆点位置
    private int preDotPosition = 0;

    private static final int SCROLL_TIME_OFFSET = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        initToolbar();
        initList();
        initBanner();

    }

    private void initToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void initList(){
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mNewsListView = (RecyclerView) findViewById(R.id.news_list_view);
        mNewsListView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mNewsListView.setLayoutManager(mLayoutManager);

        mNewsList = News.getTestList();
        mNewsAdapter = new NewsAdapter(mNewsList);
        mNewsListView.setAdapter(mNewsAdapter);
    }

    private void initBanner(){
        mHeader = View.inflate(this,R.layout.header,null);
        mNewsAdapter.setHeaderView(mHeader);

        mViewPager = (ViewPager) mHeader.findViewById(R.id.banner_viewpager);
        mBannerNewsList = BannerNews.getTestList();
        mBannerAdapter = new NewsBannerAdapter(this, mBannerNewsList);
        mViewPager.setAdapter(mBannerAdapter);

        mLLDotGroupParent = (LinearLayout) mHeader.findViewById(R.id.ll_dot_group_parent);
        mLLDotGroup = (LinearLayout) mHeader.findViewById(R.id.ll_dot_group);

        int size = mBannerNewsList.size();
        if(size > 1) {
            for (int i = 0; i < size; i++) {
                View dot = null;
                LinearLayout.LayoutParams params = null;
                // 每循环一次添加一个点到线行布局中
                dot = new View(this);
                dot.setBackgroundResource(R.drawable.dot_bg_selector);
                int width = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6,
                        getResources().getDisplayMetrics()));
                int height = width;
                params = new LinearLayout.LayoutParams(width, height);
                params.rightMargin = width;
                dot.setEnabled(false);
                dot.setLayoutParams(params);
                // 向线性布局中添加"点"
                mLLDotGroup.addView(dot);
            }
            mLLDotGroup.getChildAt(0).setEnabled(true);

        }

        //set listener
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mBannerPosition = position;

                //set lot indicator
                if (mBannerNewsList.size() <= 0) {
                    return;
                }

                // 取余后的索引，得到新的page的索引
                int newPositon = position % mBannerNewsList.size();

                for (int idx = 0; idx < mLLDotGroup.getChildCount(); idx++) {
                    mLLDotGroup.getChildAt(idx).setEnabled(false);
                }

                // 根据索引设置那个点被选中
                if (null != mLLDotGroup.getChildAt(newPositon)) {
                    mLLDotGroup.getChildAt(newPositon).setEnabled(true);
                }
                // 新索引赋值给上一个索引的位置
                preDotPosition = newPositon;
            }
        });

        mLLDotGroup.getChildAt(0).setEnabled(true);
        mViewPager.setCurrentItem(0);
    }



    private List<TextView> getTestTextList(){
        ArrayList<TextView> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            TextView v = new TextView(this);
            v.setText("标题标题标题标题标题标题标题标题标题标题"+i);
            list.add(v);
        }
        return list;
    }

    @Override
    public void onRefresh() {

    }


}
