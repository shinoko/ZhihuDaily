package com.example.administrator.zhihudaily.ui;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.adapter.ThemeAdapter;
import com.example.administrator.zhihudaily.model.BannerNews;
import com.example.administrator.zhihudaily.model.News;
import com.example.administrator.zhihudaily.model.ThemeItem;
import com.example.administrator.zhihudaily.presenter.ThemeListPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


public class ThemeListFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener, INewsListView{

    private static final String KEY_THEME_ID = "key_theme_id";
    private static final String KEY_HEADER_TITLE = "key_header_title";
    private static final String KEY_HEADER_IMAGE_URL = "key_header_image_url";


    private NewsListActivity mActivity;

    private int mThemeId;
    private String mHeaderTitle;
    private String mHeaderImageUrl;

    private ThemeListPresenter mPresenter;

    private View rootView;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mNewsListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View mHeader;
    private TextView mHeaderTitleView;
    private SimpleDraweeView mHeaderImageView;

    private ThemeAdapter mAdapter;

    private boolean isLoading = false;



    public ThemeListFragment() {
        // Required empty public constructor
    }

    public static ThemeListFragment newInstance(int themeId, String headerTitle, String headerImageUrl) {
        ThemeListFragment fragment = new ThemeListFragment();

        Bundle args = new Bundle();
        args.putInt(KEY_THEME_ID,themeId);
        args.putString(KEY_HEADER_TITLE, headerTitle);
        args.putString(KEY_HEADER_IMAGE_URL, headerImageUrl);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = (NewsListActivity) this.getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_theme_list, container, false);

        mThemeId = getArguments().getInt(KEY_THEME_ID,0);
        mHeaderTitle = getArguments().getString(KEY_HEADER_TITLE);
        mHeaderImageUrl = getArguments().getString(KEY_HEADER_IMAGE_URL);

        initList();

        return rootView;
    }

    @Override
    public void onRefresh() {
        mPresenter.getThemeLatestStories(mThemeId);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void changeNewsList(List<News> newsList) {
        mAdapter.changeData(newsList);
    }

    @Override
    public void addNewsListData(List<News> listToAdd) {
        mAdapter.addData(listToAdd);
    }

    private void initList(){
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.cyan);

        mNewsListView = (RecyclerView) rootView.findViewById(R.id.news_list_view);
        mNewsListView.setHasFixedSize(true);
        mNewsListView.addOnScrollListener(new ThemeListLoadingListener());

        mLayoutManager = new LinearLayoutManager(this.getContext());
        mNewsListView.setLayoutManager(mLayoutManager);

        mHeader = View.inflate(this.getContext(),R.layout.layout_theme_header,null);
        mHeaderTitleView = (TextView) mHeader.findViewById(R.id.theme_header_title);
        mHeaderTitleView.setText(mHeaderTitle);
        mHeaderImageView = (SimpleDraweeView) mHeader.findViewById(R.id.theme_header_image);
        mHeaderImageView.setImageURI(mHeaderImageUrl);
    }


    @Override
    public void setOnRefreshing(boolean onRefreshing) {

    }

    @Override
    public void changeNewsBanner(List<BannerNews> bannerNewsList) {

    }

    @Override
    public String getCurrentDate() {
        return null;
    }

    @Override
    public void setCurrentDate(String date) {

    }

    @Override
    public void setDrawerData(List<ThemeItem> themeItemList) {

    }



    class ThemeListLoadingListener extends RecyclerView.OnScrollListener{
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            //滑动到底部，加载更多数据
            int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
            if (lastVisibleItemPosition + 1 == mAdapter.getItemCount()) {

                boolean isRefreshing = mSwipeRefreshLayout.isRefreshing();
                if (isRefreshing) {
                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    return;
                }
                if (!isLoading) {
                    isLoading = true;

                    final int lastId = mAdapter.getLastNewsId();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPresenter.getThemeBeforeStories(mThemeId,lastId);
                            isLoading = false;
                        }
                    }, 1000);
                }
            }
        }
    }

}
