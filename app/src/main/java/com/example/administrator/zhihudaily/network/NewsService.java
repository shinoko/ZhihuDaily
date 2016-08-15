package com.example.administrator.zhihudaily.network;

import com.example.administrator.zhihudaily.model.NewsDetail;
import com.example.administrator.zhihudaily.model.NewsList;
import com.example.administrator.zhihudaily.model.ThemeList;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by shinoko on 2016/8/8.
 */
public interface NewsService {
    @Headers(NetworkManager.CACHE_CONTROL_AGE + NetworkManager.CACHE_STALE_SHORT)
    @GET("stories/latest")
    Observable<NewsList> getLatestNews();

    @Headers(NetworkManager.CACHE_CONTROL_AGE + NetworkManager.CACHE_STALE_LONG)
    @GET("stories/before/{date}")
    Observable<NewsList> getBeforeNews(@Path("date") String date);

    @Headers(NetworkManager.CACHE_CONTROL_AGE + NetworkManager.CACHE_STALE_LONG)
    @GET("story/{id}")
    Observable<NewsDetail> getNewsDetail(@Path("id") int id);

    @Headers(NetworkManager.CACHE_CONTROL_AGE + NetworkManager.CACHE_STALE_LONG)
    @GET("themes")
    Observable<ThemeList> getThemes();
}
