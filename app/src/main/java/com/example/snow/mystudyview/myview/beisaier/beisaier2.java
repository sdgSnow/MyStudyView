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
 * 二阶贝塞尔曲线
 * */
public class beisaier2 extends View{
    private float startX, startY, endX, endY, touchX, touchY;
    private Path mPath;
    private Paint mPaint;

    public beisaier2(Context context) {
        super(context);
    }

    public beisaier2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        startX = getMeasuredWidth()/5;
        startY = getMeasuredHeight()/3;
        endX = getMeasuredWidth();
        endY = getMeasuredHeight()/3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);

        mPath.moveTo(startX,startY);//起点
        mPath.quadTo(touchX,touchY,endX,endY);//控制点和终点
        canvas.drawPath(mPath,mPaint);
        mPath.reset();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        postInvalidate();
        return true;
    }
}
