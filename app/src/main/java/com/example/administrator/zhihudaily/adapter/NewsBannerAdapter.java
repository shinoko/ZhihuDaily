package com.example.administrator.zhihudaily.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.bean.BannerNews;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by shinoko on 2016/8/5.
 */
public class NewsBannerAdapter extends PagerAdapter{

    private Context mContext;
    private List<BannerNews> mBannerNewsList;

    public NewsBannerAdapter(Context context, List<BannerNews> list){
        mContext = context;
        mBannerNewsList = list;
    }


    @Override
    public int getCount() {
        return mBannerNewsList == null? 0 : mBannerNewsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final BannerNews bannerNews = mBannerNewsList.get(position);

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_banner_news, container, false);

        SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.banner_image);
        image.setImageURI(Uri.parse(bannerNews.getImage()));


        TextView bannerTitle = (TextView) view.findViewById(R.id.banner_title);
        bannerTitle.setText(bannerNews.getTitle());

        container.addView(view);

        return view;
    }

}
