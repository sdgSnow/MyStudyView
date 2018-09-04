package com.example.snow.mystudyview.myview.beisaier;

import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.snow.mystudyview.R;
import com.example.snow.mystudyview.util.CommonUtil;

public class GooViewListener implements View.OnTouchListener, MessageDragView.OnDisappearListener {
    private WindowManager mWm;
    private WindowManager.LayoutParams mParams;
    private MessageDragView mMessageDragView;
    private View point;
    private int number;
    private final Context mContext;

    private Handler mHandler;

    public GooViewListener(Context mContext, View point) {
        this.mContext = mContext;
        this.point = point;
        Object tag = point.getTag();
        Log.i("GooViewListener","tag = " + tag.toString());
        this.number = (Integer) point.getTag();
        mMessageDragView = new MessageDragView(mContext);
        mWm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        mParams.format = PixelFormat.TRANSLUCENT;
        mHandler = new Handler(mContext.getMainLooper());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        if (action == MotionEvent.ACTION_DOWN) {
            // 当按下时，将自定义View添加到WindowManager中
            ViewParent parent = v.getParent();
            // 请求其父级View不拦截Touch事件
            parent.requestDisallowInterceptTouchEvent(true);
            point.setVisibility(View.INVISIBLE);
            // 初始化当前点击的item的信息，数字及坐标
            mMessageDragView.setStatusBarHeight(CommonUtil.getStatusBarHeight(v));
            mMessageDragView.setNumber(number);
            mMessageDragView.initCenter(event.getRawX(), event.getRawY());
            mMessageDragView.setOnDisappearListener(this);
            // 执行添加方法
            mWm.addView(mMessageDragView, mParams);
        }
        // 将所有touch事件转交给GooView处理
        mMessageDragView.onTouchEvent(event);
        return true;
    }

    @Override
    public void onDisappear(PointF mDragCenter) {
        if (mWm != null && mMessageDragView.getParent() != null) {
            mWm.removeView(mMessageDragView);
            point.setVisibility(View.INVISIBLE);
            // 播放气泡爆炸动画
            ImageView imageView = new ImageView(mContext);
            imageView.setImageResource(R.drawable.anim_bubble_pop);
            AnimationDrawable mAnimDrawable = (AnimationDrawable) imageView.getDrawable();
            final BubbleLayout bubbleLayout = new BubbleLayout(mContext);
            int centerY = (int) mDragCenter.y - CommonUtil.getStatusBarHeight(mMessageDragView);
            bubbleLayout.setCenter((int) mDragCenter.x, centerY);
            bubbleLayout.addView(imageView, new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT));
            mWm.addView(bubbleLayout, mParams);
            mAnimDrawable.start();
            // 动画播放结束后，删除该bubbleLayout
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mWm.removeView(bubbleLayout);
                }
            }, 501);
        }
    }

    @Override
    public void onReset(boolean isOutOfRange) {
        // 当气泡弹回时，去除该View，等下次ACTION_DOWN的时候再添加
        point.setVisibility(View.VISIBLE);
        if (mWm != null && mMessageDragView.getParent() != null) {
            mWm.removeView(mMessageDragView);
        }
    }
}
