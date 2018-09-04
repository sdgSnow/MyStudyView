package com.example.snow.mystudyview.myview;

import android.animation.ValueAnimator;
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

public class T02CirleView extends View{

    private float mFraction;
    private ValueAnimator mAnimator;
    private Paint mPaint;
    private int radio;//定义圆半径
    private Context mContext;
    private float cx,cy;//圆心坐标
    private float touchX,touchY;
    private float width,height;//屏幕宽高

    public T02CirleView(Context context) {
        this(context,null);
    }

    public T02CirleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();

        initAnim();
    }

    private void initAnim() {
        mAnimator = ValueAnimator.ofFloat(0, 1);
        mAnimator.setDuration(1000);
        mAnimator.setRepeatCount(1);
        mAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mAnimator.addUpdateListener(mAnimatorUpdateListener);
    }

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mFraction = animation.getAnimatedFraction();
            postInvalidate();
        }
    };

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//实例画笔并打开抗锯齿
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//设置画笔样式，描边
        mPaint.setColor(Color.RED);//设置画笔颜色，红色
        mPaint.setStrokeWidth(10);//设置描边粗细 10，单位px

        cx = MeasureUtil.getScreenSize((Activity) mContext)[0]/2;
        cy = MeasureUtil.getScreenSize((Activity) mContext)[1]/10;
        width = MeasureUtil.getScreenSize((Activity) mContext)[0];
        height = MeasureUtil.getScreenSize((Activity) mContext)[1];
    }

    public void setRadio(int radio){
        this.radio = radio;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("T02CirleView","onDraw");
        canvas.drawCircle(cx,cy,radio + mFraction * 20,mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("T02CirleView","onMeasure");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchX = event.getX();
        touchY = event.getY();
        switch (MotionEventCompat.getActionMasked(event)){
            case MotionEvent.ACTION_DOWN:
                Log.i("T02CirleView","ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                if((cx + radio)<width && (cx - radio)>0 &&(cy - radio)>0 && (cy + radio)<height) {
                    cx = touchX;
                    cy = touchY;
                    postInvalidate();
                }else {

                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("T02CirleView","ACTION_UP");
                mAnimator.start();
                break;
        }
        invalidate();
        return true;
    }

}
