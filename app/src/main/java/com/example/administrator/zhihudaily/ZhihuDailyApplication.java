package com.example.administrator.zhihudaily;

import android.app.Application;

import com.example.administrator.zhihudaily.util.ContextUtil;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by shinoko on 2016/8/5.
 */
public class ZhihuDailyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        ContextUtil.init(this);
    }
}
