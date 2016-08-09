package com.example.administrator.zhihudaily.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.model.BannerNews;
import com.example.administrator.zhihudaily.ui.NewsDetailActivity;
import com.example.administrator.zhihudaily.util.ContextUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shinoko on 2016/8/5.
 */
public class NewsBannerAdapter extends PagerAdapter{

    private Context mContext;
    private List<BannerNews> mBannerNewsList;
    private List<View> mViewList;

    private int currentPage = 0;

//    public NewsBannerAdapter(Context context, List<BannerNews> list){
//        mContext = context;
//        mBannerNewsList = list;
//
//        getViewList();
//        notifyDataSetChanged();
//    }

    public NewsBannerAdapter(Context context){
        mContext = context;
    }

    public void setDataList(List<BannerNews> list){
        mBannerNewsList = list;
        getViewList();
    }

    private void getViewList(){
        mViewList = new ArrayList<>();

        for (int i = 0; i < mBannerNewsList.size(); i++) {
            View view = View.inflate(mContext, R.layout.item_banner_news,null);

            TextView tv = (TextView) view.findViewById(R.id.banner_title);
            tv.setText(mBannerNewsList.get(i).getTitle());

            SimpleDraweeView imageView = (SimpleDraweeView) view.findViewById(R.id.banner_image);
            imageView.setImageURI(Uri.parse(mBannerNewsList.get(i).getImage()));

            view.setOnClickListener(getListener(mBannerNewsList.get(i)));

            mViewList.add(view);
        }

    }

    @Override
    public int getCount() {
        return mViewList == null? 0 : mViewList.size();
//        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.d("now position",String.valueOf(position));

        container.addView(mViewList.get(position));

        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        currentPage = position;
        Log.e("current ",position+"");
    }

    public int getCurrentPage(){
        return currentPage;
    }


    public void changeData(List<BannerNews> newsList) {
        mBannerNewsList = newsList;
        getViewList();
        notifyDataSetChanged();
    }

    private View.OnClickListener getListener(final BannerNews news){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDetailActivity.startFromBanner(ContextUtil.getContext(), news);
            }
        };

    }


}
