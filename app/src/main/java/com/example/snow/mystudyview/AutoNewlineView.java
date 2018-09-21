package com.example.snow.mystudyview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chong on 2017/8/4.
 * 流失布局，添加textview的热门搜索类似功能
 */

public class AutoNewlineView extends LinearLayout {

    private List<String> mDatas = new ArrayList<>();

    private int mWide;
    private int mHide;
    private int mGravity = Gravity.CENTER;

    private int mChildPaddingLeft = 10;
    private int mChildPaddingRight = 10;
    private int mChildPaddingTop = 5;
    private int mChildPaddingBottom = 5;

    private int mChildMarginLeft = 5;
    private int mChildMarginRight = 5;
    private int mChildMarginTop = 0;
    private int mChildMarginBottom = 0;

    private int mMarginLeft = 5;
    private int mMarginRight = 5;
    private int mMarginTop = 5;
    private int mMarginBottom = 5;

    private int mPaddingLeft = 5;
    private int mPaddingRight = 5;
    private int mPaddingTop = 5;
    private int mPaddingBottom = 5;

    private GradientDrawable mGradientDrawable;
    private int mSize = 12;
    private int mColor = Color.WHITE;

    private OnChildClickListener mOnChildClickListener;

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnChildClickListener != null)mOnChildClickListener.onChildClick(v, ((int) v.getTag()));
        }
    };


    private boolean isRun;

    public AutoNewlineView(Context context) {
        this(context,null);
    }

    public AutoNewlineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mGradientDrawable = new GradientDrawable();
        int roundRadius = 30;
        int fillColor = Color.parseColor("#e2d2d2");
        mGradientDrawable.setColor(fillColor);
        mGradientDrawable.setCornerRadius(roundRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWide = w;
    }

    public AutoNewlineView setAdapder(String[] adapder){
        return setAdapder(Arrays.asList(adapder));
    }

    public AutoNewlineView setAdapder(List<String> adapder){
        mDatas = adapder;
        return this;
    }

    public void notifyDataSetChanged(){
        create();
    }

    public AutoNewlineView setChildGravity(int gravity){
        mGravity = gravity;
        return this;
    }

    public AutoNewlineView setChildBackgroundResource(@DrawableRes int resid){
        setChildBackground(getResources().getDrawable(resid));
        return this;
    }

    public AutoNewlineView setChildBackground(Drawable background){
        mGradientDrawable = (GradientDrawable) background;
        return this;
    }


    public AutoNewlineView setChildPadding(int left, int top, int right, int bottom){
        mChildPaddingLeft = left;
        mChildPaddingRight = right;
        mChildPaddingBottom = bottom;
        mChildPaddingTop = top;
        return this;
    }

    public AutoNewlineView setChildMargin(int left, int top, int right, int bottom){
        mChildMarginLeft = left;
        mChildMarginRight = right;
        mChildMarginBottom = bottom;
        mChildMarginTop = top;
        return this;
    }

    public AutoNewlineView setMargin(int left, int top, int right, int bottom){
        mMarginLeft = left;
        mMarginRight = right;
        mMarginBottom = bottom;
        mMarginTop = top;
        return this;
    }

    public AutoNewlineView setPaddings(int left, int top, int right, int bottom){
        mPaddingLeft = left;
        mPaddingRight = right;
        mPaddingBottom = bottom;
        mPaddingTop = top;
        return this;
    }

    public AutoNewlineView setTextSize(int size){
        mSize = size;
        return this;
    }

    public AutoNewlineView setTextColor(String color){
        return setTextColor(Color.parseColor(color));
    }
    public AutoNewlineView setTextColor(int color){
        mColor = color;
        return this;
    }

    public AutoNewlineView setOnChildClickListener(OnChildClickListener onChildClickListener){
        mOnChildClickListener = onChildClickListener;
        return this;
    }

    public void create(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                createView();
            }
        },200);
    }

    public interface OnChildClickListener{
        void onChildClick(View v, int position);
    }


    private void createView() {
        removeAllViews();
        int viewWide = mWide;
        setOrientation(LinearLayout.VERTICAL);
        setPadding(mPaddingLeft,mPaddingTop,mPaddingRight,mPaddingBottom);
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        for (int i = 0; i < mDatas.size(); i++) {
            TextView textView = new TextView(getContext());
            textView.setGravity(mGravity);
            textView.setBackgroundDrawable(mGradientDrawable);
            textView.setPadding(mChildPaddingLeft, mChildPaddingTop, mChildPaddingRight, mChildPaddingBottom);
            textView.setTextSize(mSize);
            textView.setTextColor(mColor);
            textView.setTag(i);
            LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams1.leftMargin = mChildMarginLeft;
            layoutParams1.rightMargin = mChildMarginRight;
            layoutParams1.topMargin = mChildMarginTop;
            layoutParams1.bottomMargin = mChildMarginBottom;
            textView.setLayoutParams(layoutParams1);
            textView.setOnClickListener(mOnClickListener);
            textView.setText(mDatas.get(i));
            TextPaint paint = textView.getPaint();
            int textWidth = (int) paint.measureText(mDatas.get(i));
            if (viewWide - textWidth - mChildPaddingLeft - mChildPaddingRight - mMarginLeft - mMarginRight > 0) {
                linearLayout.addView(textView);
                viewWide = viewWide - textWidth - mChildPaddingLeft - mChildPaddingRight - mMarginLeft - mMarginRight;
            } else {
                viewWide = mWide - textWidth - mChildPaddingLeft - mChildPaddingRight - mMarginLeft - mMarginRight;
                linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(textView);
            }
            if (linearLayout.getLayoutParams() == null) addView(linearLayout);
            LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
            layoutParams.setMargins(mMarginLeft, mMarginTop, mMarginRight, mMarginBottom);
        }
    }
}
