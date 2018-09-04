package com.example.snow.mystudyview.myview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.snow.mystudyview.util.MeasureUtil;

public class T04OvalView extends View {
    private RectF mRect;
    private RectF mRect1;
    private RectF mRect2;
    private RectF mRect3;
    private RectF mRect4;
    private RectF mRect5;
    private RectF mRect6;
    private RectF mRect7;
    private Paint mPaint;
    private float width,height;//屏幕宽高
    private Context mContext;
    private float centerX;
    private float centerY;

    public T04OvalView(Context context) {
        this(context,null);
    }

    public T04OvalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        width = MeasureUtil.getScreenSize((Activity) mContext)[0];
        height = MeasureUtil.getScreenSize((Activity) mContext)[1];

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(5);

        mRect = new RectF(100,100,250,200);
        centerX = mRect.centerX();
        centerY = mRect.centerY();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setRect(Color.RED,2);
        canvas.drawOval(mRect,mPaint);
        setRect(Color.BLUE,3);
         canvas.drawOval(mRect,mPaint);
        invalidate();
    }

    public void setRect(int color,int i){
        mPaint.setColor(color);
        centerX = width/i;
        centerY = height/i;
    }
}
