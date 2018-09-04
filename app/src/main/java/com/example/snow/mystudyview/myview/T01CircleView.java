package com.example.snow.mystudyview.myview;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.snow.mystudyview.util.MeasureUtil;

/**
 * 绘制一个圆，按一定时间从圆点循环放大
 * */
public class T01CircleView extends View implements Runnable{

    private Paint mPaint;
    private Context mContext;
    private int radiu;// 圆环半径
    private float cx;
    private float cy;
    private float a,b;
    private float touchX,touchY;

    public T01CircleView(Context context) {
        this(context,null);
    }

    public T01CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
        xy();
    }

    public T01CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void initPaint(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//实例画笔并打开抗锯齿
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//设置画笔样式，描边
        mPaint.setColor(Color.RED);//设置画笔颜色，红色
        mPaint.setStrokeWidth(10);//设置描边粗细 10，单位px
    }

    public void xy(){
        cx = MeasureUtil.getScreenSize((Activity) mContext)[0]/2;
        cy = MeasureUtil.getScreenSize((Activity) mContext)[1]/10;
        Log.i("xy : T01CircleView","cx = " + cx + "  cy = " + cy);
        a = MeasureUtil.getScreenSize((Activity) mContext)[0];
        b = MeasureUtil.getScreenSize((Activity) mContext)[1];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(cx,cy,radiu,mPaint);
    }

    public synchronized void setRadio(int radio){
        this.radiu = radio;
        invalidate();
    }

    @Override
    public void run() {
        while (true) {
            try { //如果半径小于200则自加否则大于200后重置半径值以实现往复
                if(cx < 720 && cy < 1230){
                    cx = cx + 1;
                    cy = 5*cy/cx+cy;
                    postInvalidate();
                }else {
                    cx = MeasureUtil.getScreenSize((Activity) mContext)[0]/2;
                    cy = MeasureUtil.getScreenSize((Activity) mContext)[1]/10;
                }
                // 每执行一次暂停40毫秒
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        postInvalidate();
        switch (MotionEventCompat.getActionMasked(event)){
            case MotionEvent.ACTION_DOWN://手触摸屏幕后，点中球则球直接返回去
                Log.i("xy : onTouchEvent","touchX = " + touchX + "  touchY = " + touchY);
                if(touchX < cx + radiu && touchX > cx - radiu && touchY < cy + radiu && touchY > cy - touchY){
                    Log.i("xy : onTouchEvent","点钟了");
                }
                break;
        }
        return true;
    }
}
