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
import android.view.MotionEvent;
import android.view.View;

import com.example.snow.mystudyview.util.MeasureUtil;

public class MoveCircleView extends View {

    private Paint mPaint;
    private float mFraction;
    private float cx,cy;//圆心坐标
    private float cx2,cy2;//圆心2坐标
    private int radio;//圆半径
    private int radio2;//圆2半径
    private Context mContext;
    private float width,height;//屏幕宽高
    private float mDownX;
    private float mDownY;

    private boolean isCanMove;
    private boolean isMove;

    public MoveCircleView(Context context) {
        this(context,null);
    }

    public MoveCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        width = MeasureUtil.getScreenSize((Activity) mContext)[0];
        height = MeasureUtil.getScreenSize((Activity) mContext)[1];
        cx = MeasureUtil.getScreenSize((Activity) mContext)[0]/2;
        cy = MeasureUtil.getScreenSize((Activity) mContext)[1]/2;

        cx2 = MeasureUtil.getScreenSize((Activity) mContext)[0]/2;
        cy2 = MeasureUtil.getScreenSize((Activity) mContext)[1]/4;
    }

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mFraction = animation.getAnimatedFraction();
            postInvalidate();
        }
    };


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(cx,cy,radio,mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawCircle(cx2,cy2,30,mPaint);
    }

    public void setRadio(int radio){
        this.radio = radio;
        invalidate();
    }
    public int getRadio(){
        return radio;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (MotionEventCompat.getActionMasked(event)){
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                isCanMove = Math.pow(mDownX - cx, 2) +  Math.pow(mDownY - cy, 2) <=  Math.pow(radio, 2);
                isMove = Math.pow(cx-cx2,2)+Math.pow(cy-cy2,2) >= Math.pow(radio+30,2);

                break;
            case MotionEvent.ACTION_MOVE:

                if (!isCanMove) return false;

                float dx = event.getX() - mDownX;
                float dy = event.getY() - mDownY;

                if (cx + dx > radio && cx + dx < getWidth() - radio && cy + dy > radio && cy + dy < getHeight() - radio && isMove){
                    cx += dx;
                    cy += dy;
                }

                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        postInvalidate();
        return true;
    }

}
