package com.example.administrator.zhihudaily.ui;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shinoko on 2016/8/10.
 */
public class DetailScrollView extends NestedScrollView {

    private Toolbar mToolbar;
    private View headView;
    private AppBarLayout mAppBarLayout;

    private int height = 0;
    private int mSlop = 10;


    public DetailScrollView(Context context) {
        super(context);
    }

    public DetailScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DetailScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setToolbar(Toolbar toolbar){
        mToolbar = toolbar;
    }

    public void setHeadView(View view){
        headView = view;
    }

    public void setAppBarLayout(AppBarLayout layout){
        mAppBarLayout = layout;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        float headHeight = headView.getMeasuredHeight() - mToolbar.getMeasuredHeight();
//        Log.e("headHeight ",headHeight+"");
//        Log.e("t ",t+"");
//
//        int alpha = 255- (int) (((float) t / headHeight) * 255);
//
//        if (alpha >= 255)
//            alpha = 255;
//        if (alpha <= mSlop)
//            alpha = 0;
//
//        float alphaf = alpha / 255;
//
//        Log.e("alpha ",alpha+"");
//        Log.e("alphaf ",alphaf+"");
//
////        mToolbar.getBackground().setAlpha(alpha);
//        mToolbar.setAlpha(alphaf);
//        mAppBarLayout.setAlpha(alphaf);

        super.onScrollChanged(l, t, oldl, oldt);
    }
}
