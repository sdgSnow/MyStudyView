package com.example.snow.mystudyview.myview.beisaier;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.snow.mystudyview.util.MeasureUtil;

/**
 * 一阶贝塞尔 线段
 * */
public class beisaier1  extends View{

    private Path mPath;
    private Context mContext;
    private Paint mPaint;


    public beisaier1(Context context) {
        super(context);
    }

    public beisaier1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath.moveTo(MeasureUtil.getScreenSize((Activity) mContext)[0]/2,MeasureUtil.getScreenSize((Activity) mContext)[1]/2);
        mPath.lineTo(MeasureUtil.getScreenSize((Activity) mContext)[0]/4,MeasureUtil.getScreenSize((Activity) mContext)[1]/6);
        canvas.drawPath(mPath,mPaint);
        mPath.reset();
        invalidate();
    }
}
