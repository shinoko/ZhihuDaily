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
import com.example.administrator.zhihudaily.ui.NewsDetailActivity;
import com.example.administrator.zhihudaily.util.ContextUtil;
import com.example.administrator.zhihudaily.util.DateUtil;

import java.util.List;

/**
 * Created by shinoko on 2016/8/5.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private static final int ITEM_NEWS = 0;
    private static final int ITEM_NEWS_DATE = 1;
    private static final int ITEM_HEADER = 2;

    private List<News> mNewsList;
    private View mHeaderView;

    public NewsAdapter(){}

    public NewsAdapter(List<News> newsList){
        mNewsList = newsList;
    }

    public void setDataList(List<News> list){
        mNewsList = list;
    }

    public void setHeaderView(View headerView){
        mHeaderView = headerView;
    }

    public View getHeaderView(){
        return mHeaderView;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_NEWS){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,
                    parent, false);
            return new NewsViewHolder(view);
        }else if(viewType == ITEM_NEWS_DATE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_date,
                    parent, false);
            return new NewsDateViewHolder(view);
        }else{
            return new NewsViewHolder(mHeaderView);
        }
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        if(getItemViewType(position) == ITEM_HEADER){
            return;
        }else{
            final News news = mNewsList.get(position-1);

            if(holder instanceof NewsDateViewHolder){
                ((NewsDateViewHolder) holder).date.setText(DateUtil.getFormateDate(news.getDate()));
            }

            holder.newsTitle.setText(news.getTitle());

            if(news.getImages() != null){
                holder.newsImage.setImageURI(Uri.parse(news.getImages().get(0)));
            }

            holder.cardView.setOnClickListener(getListener(news));

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_HEADER;
        }else if (position == 1){
            return ITEM_NEWS_DATE;
        }
        String currentDate = mNewsList.get(position-1).getDate();
        int preIndex = position - 2;
        return mNewsList.get(preIndex).getDate().equals(currentDate) ? ITEM_NEWS : ITEM_NEWS_DATE;
    }

    @Override
    public int getItemCount() {
        return mNewsList == null? 1 : mNewsList.size()+1;
    }


    private View.OnClickListener getListener(final News news){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDetailActivity.start(ContextUtil.getContext(), news);
            }
        };
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

    class NewsViewHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public TextView newsTitle;
        public ImageView newsImage;

        public NewsViewHolder(View rootView) {
            super(rootView);

            if(rootView == mHeaderView){
                return;
            }

            cardView = (CardView) rootView.findViewById(R.id.cv_item);
            newsTitle = (TextView) rootView.findViewById(R.id.tv_title);
            newsImage = (ImageView) rootView.findViewById(R.id.iv_news);
        }

    }

    class NewsDateViewHolder extends NewsViewHolder{

        public TextView date;

        public NewsDateViewHolder(View rootView) {
            super(rootView);
            date = (TextView) rootView.findViewById(R.id.news_date);
        }
    }


}
