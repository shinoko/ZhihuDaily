package com.example.administrator.zhihudaily.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.bean.BannerNews;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shinoko on 2016/8/5.
 */
public class NewsBannerAdapter extends PagerAdapter{

    private Context mContext;
    private List<BannerNews> mBannerNewsList;

    private List<View> mViewList;

    public NewsBannerAdapter(Context context, List<BannerNews> list){
        mContext = context;
        mBannerNewsList = list;

        getViewList();
    }

    private void getViewList(){
        mViewList = new ArrayList<>();

        for (int i = 0; i < mBannerNewsList.size(); i++) {
            View view = View.inflate(mContext, R.layout.item_banner_news,null);

            TextView tv = (TextView) view.findViewById(R.id.banner_title);
            tv.setText(mBannerNewsList.get(i).getTitle());
            mViewList.add(view);
        }

    }

//    public NewsBannerAdapter(Context context, List<TextView> list){
//        mContext = context;
//        viewList = list;
//
//        Log.d("tag",String.valueOf(list.size()));
//    }


    @Override
    public int getCount() {
//        return mBannerNewsList == null? 0 : mBannerNewsList.size();
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        Log.d("now position",String.valueOf(position));


//        final BannerNews bannerNews = mBannerNewsList.get(position);
//
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_banner_news, container, false);
//
//        SimpleDraweeView image = (SimpleDraweeView) view.findViewById(R.id.banner_image);
////        image.setImageURI(Uri.parse(bannerNews.getImage()));
//
//
//        TextView bannerTitle = (TextView) view.findViewById(R.id.banner_title);
//        bannerTitle.setText(bannerNews.getTitle());

        container.addView(mViewList.get(position));

        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

}
