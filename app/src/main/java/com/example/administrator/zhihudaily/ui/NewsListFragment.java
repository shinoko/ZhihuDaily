package com.example.administrator.zhihudaily.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class NewsListFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, INewsListView{

    private NewsListActivity mActivity;

    private View rootView;
    private NewsListPresenter mPresenter;

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


    private Toolbar mToolbar;

    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment newInstance() {
        NewsListFragment fragment = new NewsListFragment();
//        Bundle args = new Bundle();
//        String param1 = "";
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (NewsListActivity) this.getActivity();

        mPresenter = new NewsListPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news_list, container, false);

        initList();
        initBanner();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        });

        return rootView;
    }

    private void initList(){
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.cyan);

        mNewsListView = (RecyclerView) rootView.findViewById(R.id.news_list_view);
        mNewsListView.setHasFixedSize(true);
        mNewsListView.addOnScrollListener(new NewsListLoadingListener());

        mLayoutManager = new LinearLayoutManager(this.getContext());
        mNewsListView.setLayoutManager(mLayoutManager);


        //**************************************************8
//        mNewsList = News.getTestList();
        mNewsAdapter = new NewsAdapter();
        mNewsListView.setAdapter(mNewsAdapter);

    }

    private void initBanner(){
        mHeader = View.inflate(this.getContext(),R.layout.layout_home_header,null);
        mNewsAdapter.setHeaderView(mHeader);

        mViewPager = (ViewPager) mHeader.findViewById(R.id.banner_viewpager);

        //**************************************************
//        mBannerNewsList = BannerNews.getTestList();
        mBannerAdapter = new NewsBannerAdapter(this.getContext());
        mViewPager.setAdapter(mBannerAdapter);

        mLLDotGroupParent = (LinearLayout) mHeader.findViewById(R.id.ll_dot_group_parent);
        mLLDotGroup = (LinearLayout) mHeader.findViewById(R.id.ll_dot_group);

//        setBannerIndex();

//        mViewPager.setCurrentItem(0);
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
                dot = new View(this.getContext());
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
    }

    @Override
    public void onRefresh() {
        mPresenter.getLatestNews();
        setBannerIndex();
        mSwipeRefreshLayout.setRefreshing(false);
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
                    mActivity.setTitleText(title);
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
