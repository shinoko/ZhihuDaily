package com.example.administrator.zhihudaily.util;

import android.content.Context;

/**
 * Created by shinoko on 2016/8/8.
 */
public class ContextUtil {
    private static Context context;
    public static void init(Context appContext){
        context = appContext.getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
