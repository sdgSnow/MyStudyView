package com.example.snow.mystudyview.myview.beisaier;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 三阶贝塞尔
 * */
public class beisaier3 extends View{
    private float startX, startY, endX, endY, touch01X, touch01Y, touch02X, touch02Y;
    private Path mPath;
    private Paint mPaint;

    public beisaier3(Context context) {
        super(context);
    }

    public beisaier3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        startX = getMeasuredWidth()/5;
        startY = getMeasuredHeight()/3;
        endX = getMeasuredWidth();
        endY = getMeasuredHeight()/3;
        touch01X = getMeasuredWidth()/2;
        touch01Y = getMeasuredHeight()/6;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(startX,startY);
        mPath.cubicTo(touch01X,touch01Y,touch02X,touch02Y,endX,endY);
        canvas.drawPath(mPath,mPaint);
        mPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touch02X = event.getX();
        touch02Y = event.getY();
        postInvalidate();
        return true;
    }
}
