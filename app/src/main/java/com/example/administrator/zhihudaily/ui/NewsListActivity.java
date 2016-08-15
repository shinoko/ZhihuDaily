package com.example.administrator.zhihudaily.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.adapter.NewsAdapter;
import com.example.administrator.zhihudaily.adapter.NewsBannerAdapter;
import com.example.administrator.zhihudaily.model.BannerNews;
import com.example.administrator.zhihudaily.model.News;
import com.example.administrator.zhihudaily.model.ThemeItem;
import com.example.administrator.zhihudaily.presenter.NewsListPresenter;

import java.util.ArrayList;
import java.util.List;

public class NewsListActivity extends AppCompatActivity implements INewsListView {

    private NewsListPresenter mPresenter;


    private Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;


    //********** News List相关 **********
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mNewsListView;
    private NewsAdapter mNewsAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
//    private List<News> mNewsList;

    private boolean isLoading = false;


    //********** Banner相关 **********
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

    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        mPresenter = new NewsListPresenter(this);

        initToolbar();
//        initList();
//        initBanner();
        initDrawer();

//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                onRefresh();
//            }
//        });



        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment homeFragment = NewsListFragment.newInstance();
        fragmentTransaction.add(R.id.news_content_layout,homeFragment);
        fragmentTransaction.commit();


    }

    private void initToolbar(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.home_page);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(mToolbar);
    }

    private void initList(){
//        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
//        mSwipeRefreshLayout.setOnRefreshListener(this);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);

        mNewsListView = (RecyclerView) findViewById(R.id.news_list_view);
        mNewsListView.setHasFixedSize(true);
        mNewsListView.addOnScrollListener(new NewsListLoadingListener());

        mLayoutManager = new LinearLayoutManager(this);
        mNewsListView.setLayoutManager(mLayoutManager);


        //**************************************************8
//        mNewsList = News.getTestList();
        mNewsAdapter = new NewsAdapter();
        mNewsListView.setAdapter(mNewsAdapter);

    }

    private void initBanner(){
        mHeader = View.inflate(this,R.layout.layout_header,null);
        mNewsAdapter.setHeaderView(mHeader);

        mViewPager = (ViewPager) mHeader.findViewById(R.id.banner_viewpager);

        //**************************************************
//        mBannerNewsList = BannerNews.getTestList();
        mBannerAdapter = new NewsBannerAdapter(this);
        mViewPager.setAdapter(mBannerAdapter);

        mLLDotGroupParent = (LinearLayout) mHeader.findViewById(R.id.ll_dot_group_parent);
        mLLDotGroup = (LinearLayout) mHeader.findViewById(R.id.ll_dot_group);

//        setBannerIndex();

//        mViewPager.setCurrentItem(0);
    }

    private void initDrawer(){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        mPresenter.getThemes();
        Log.d("menu","init menu");
    }

    private void setBannerIndex(){
        if(mLLDotGroup.getChildCount() > 0){
            mLLDotGroup.removeAllViews();
        }

        int size = mBannerNewsList.size();
        Log.e("banner size:",size+"");
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
            mLLDotGroup.getChildAt(mBannerAdapter.getCurrentPage()).setEnabled(true);
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

        if(mLLDotGroup.getChildCount()>0){
            mLLDotGroup.getChildAt(mBannerAdapter.getCurrentPage()).setEnabled(true);
        }
    }

    public void setTitleText(String titleText){
        mToolbar.setTitle(titleText);
    }

    @Override
    public void setOnRefreshing(boolean onRefreshing) {
    }

    @Override
    public void changeNewsList(List<News> newsList) {
        mNewsAdapter.changeData(newsList);
    }

    @Override
    public void changeNewsBanner(List<BannerNews> bannerNewsList) {
        mBannerNewsList = bannerNewsList;
        mBannerAdapter.changeData(bannerNewsList);
        setBannerIndex();
    }

    @Override
    public String getCurrentDate() {
        return currentDate;
    }

    @Override
    public void setCurrentDate(String date) {
        currentDate = date;
    }

    @Override
    public void addNewsListData(List<News> listToAdd) {
        mNewsAdapter.addData(listToAdd);
    }

    @Override
    public void setDrawerData(List<ThemeItem> themeItemList) {
        mNavigationView.getMenu().add("首页");
        for(ThemeItem item:themeItemList){
            mNavigationView.getMenu().add(item.getName());
            Log.d("menu","add "+item.getName());
        }
        mNavigationView.getMenu().notify();
    }

    class NewsListLoadingListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            //改变toolbar标题内容
            View view = recyclerView.findChildViewUnder(recyclerView.getMeasuredWidth() / 2, 5);
            if (view != null) {
                RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                if(viewHolder instanceof NewsAdapter.NewsViewHolder){
                    String title = ((NewsAdapter.NewsViewHolder) viewHolder).dateInfo;
                    setTitleText(title);
                }
            }

            //滑动到底部，加载更多数据
            int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            if (lastVisibleItemPosition + 1 == mNewsAdapter.getItemCount()) {

                boolean isRefreshing = mSwipeRefreshLayout.isRefreshing();
                if (isRefreshing) {
                    mNewsAdapter.notifyItemRemoved(mNewsAdapter.getItemCount());
                    return;
                }
                if (!isLoading) {
                    isLoading = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.getBeforeNews(currentDate);
                            isLoading = false;
                        }
                    }, 1000);
                }
            }
        }
    }
}
