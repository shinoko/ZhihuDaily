package com.example.administrator.zhihudaily.network;

import com.example.administrator.zhihudaily.model.NewsDetail;
import com.example.administrator.zhihudaily.model.NewsList;
import com.example.administrator.zhihudaily.util.ContextUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by shinoko on 2016/8/8.
 */
public class NetworkManager {

    public static final String BASE_URL = "http://news-at.zhihu.com/api/4/";
    private static OkHttpClient mOkHttpClient;
    private final NewsService mService;


    //短缓存有效期为1分钟
    public static final int CACHE_STALE_SHORT = 60;
    //长缓存有效期为7天
    public static final int CACHE_STALE_LONG = 60 * 60 * 24 * 7;

    public static final String CACHE_CONTROL_AGE = "Cache-Control: public, max-age=";

    public static NetworkManager builder(){
        return new NetworkManager();
    }

    private NetworkManager() {
        initOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mService = retrofit.create(NewsService.class);
    }

    private void initOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (mOkHttpClient == null) {
            synchronized (NetworkManager.class) {
                if (mOkHttpClient == null) {

                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(ContextUtil.getContext().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 100);

                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(15, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
    }


    public Observable<NewsList> getLatestNews(){
        return mService.getLatestNews();
    }

    public Observable<NewsList> getBeforeNews(String date){
        return mService.getBeforeNews(date);
    }

    public Observable<NewsDetail> getNewsDetail(int id) {
        return mService.getNewsDetail(id);
    }


}
