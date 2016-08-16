package com.example.administrator.zhihudaily.adapter;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.zhihudaily.R;
import com.example.administrator.zhihudaily.model.News;

import java.util.List;

/**
 * Created by shinoko on 2016/8/16.
 */
public class ThemeAdapter extends RecyclerView.Adapter<ThemeAdapter.ThemeNewsViewHolder>{

    private static final int ITEM_NEWS = 0;
    private static final int ITEM_HEADER = 2;

    private View mHeaderView;

    private List<News> mNewsList;


    @Override
    public ThemeNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_NEWS){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent, false);
            return new ThemeNewsViewHolder(view);
        }else{
            return new ThemeNewsViewHolder(mHeaderView);
        }
    }

    @Override
    public void onBindViewHolder(ThemeNewsViewHolder holder, int position) {
        final News news = mNewsList.get(position-1);

        holder.newsTitle.setText(news.getTitle());

        if(news.getImages() != null){
            holder.newsImage.setImageURI(Uri.parse(news.getImages().get(0)));
        } else {
            holder.newsImage.setVisibility(View.GONE);
        }

        holder.cardView.setOnClickListener(getListener(news));

    }

    @Override
    public int getItemCount() {
        return mNewsList == null? 1 : mNewsList.size() + 1;
    }

    public void setHeaderView(View headerView){
        mHeaderView = headerView;
    }

    public void changeData(List<News> newsList) {
        mNewsList = newsList;
        notifyDataSetChanged();
    }

    public void addData(List<News> newsList) {
        if (mNewsList == null) {
            changeData(newsList);
        } else {
            mNewsList.addAll(newsList);
            notifyDataSetChanged();
        }
    }

    public int getLastNewsId(){
        return mNewsList.get(mNewsList.size()-1).getId();
    }


    private View.OnClickListener getListener(final News news){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NewsDetailActivity.start(ContextUtil.getContext(), news);
            }
        };
    }



    public class ThemeNewsViewHolder extends RecyclerView.ViewHolder{

        public CardView cardView;
        public TextView newsTitle;
        public ImageView newsImage;

        public ThemeNewsViewHolder(View rootView) {
            super(rootView);

            if(itemView == mHeaderView){
                return;
            }

            cardView = (CardView) rootView.findViewById(R.id.cv_item);
            newsTitle = (TextView) rootView.findViewById(R.id.tv_title);
            newsImage = (ImageView) rootView.findViewById(R.id.iv_news);
        }
    }
}
